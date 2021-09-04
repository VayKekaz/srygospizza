import DishModel from "../model/DishModel";
import clonedeep from "lodash/cloneDeep";
import CartItemModel from "../model/CartItemModel";
import {SRYGOSPIZZA_BACKEND_URL} from "../config";
import ListenerHolder from "../util/ListenerHolder";
import OrderModel from "../model/OrderModel";

class OrderApi {
    private cartItems: Array<CartItemModel> = [];

    public getItems = (): Array<CartItemModel> =>
        clonedeep(this.cartItems);
    public getCartItemsCount = () =>
        this.cartItems.length;

    public addDish(dish: DishModel) {
        try {
            const {item} = this.getItemByName(dish.name);
            item.quantity += 1;
        } catch (error: any) {
            this.cartItems.push({quantity: 1, dish: dish});
        }
        this.listeners.notify();
    }

    public clear() {
        this.cartItems = [];
        this.listeners.notify();
    }

    public incrementItemQuantityByName(name: string) {
        this.getItemByName(name).item.quantity++;
        this.listeners.notify();
    }

    public decrementItemQuantityByName(name: string) {
        const {index, item} = this.getItemByName(name);
        item.quantity -= 1;
        if (item.quantity < 1)
            this.cartItems.splice(index, 1);
        this.listeners.notify();
    }

    private getItemByName(name: string): { index: number, item: CartItemModel } {
        for (let index = 0; index < this.cartItems.length; index++) {
            const item = this.cartItems[index];
            if (item.dish.name === name)
                return {index, item};
        }
        throw new Error(`Cart item with name ${name} not found`);
    }

    public async submitOrder(address: string) {
        // TODO some feedback
        try {
            const response = await fetch(`${SRYGOSPIZZA_BACKEND_URL}/orders`, {
                headers: {"Content-Type": "application/json"},
                method: "POST",
                body: JSON.stringify(this.createOrder(address)),
            });
            return await response.json();
        } catch (error) {
            throw error;
        }
    }

    private createOrder(address: string): OrderModel {
        return {
            address,
            dateOrdered: new Date(),
            dishes: this.cartItems
        };
    }

    public readonly listeners = new ListenerHolder<Array<CartItemModel>>(this.getItems);
}

export default new OrderApi();
