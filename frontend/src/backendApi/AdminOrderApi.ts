import OrderModel from "../model/OrderModel";
import clonedeep from "lodash/cloneDeep";
import {SRYGOSPIZZA_BACKEND_URL} from "../config";
import ListenerHolder from "../util/ListenerHolder";

class AdminOrderApi {
    private orders: Array<OrderModel> = [];
    public getOrders = () => clonedeep(this.orders);

    private stillLoading: boolean = false;
    public isLoading = (): boolean => this.stillLoading;

    private currentPageNumber: number = 0;
    private currentPageSize: number = 10;

    private setPageProperties(pageNumber: number = 0, pageSize: number = 10) {
        this.currentPageNumber = pageNumber;
        this.currentPageSize = pageSize;
    }

    public reload = async () =>
        this.load(this.currentPageNumber, this.currentPageSize);

    public load = async (pageNumber: number = 0, pageSize: number = 10) => {
        this.stillLoading = true;
        const response = await (await fetch(
            `${SRYGOSPIZZA_BACKEND_URL}/orders?pageNumber=${pageNumber}&pageSize=${pageSize}`
        )).json();
        this.orders = response.content as Array<OrderModel>;
        fixStringDates(this.orders);
        //this.orders.forEach(order => order.dateOrdered = new Date(order.dateOrdered));
        this.stillLoading = false;
        this.listeners.notify();
        return this.orders;
    }

    public readonly listeners = new ListenerHolder<Array<OrderModel>>(this.getOrders);
}

function fixStringDates(orders: Array<OrderModel>) {
    orders.forEach(fixStringDate);
}

function fixStringDate(order: OrderModel) {
    order.dateOrdered = new Date(order.dateOrdered);
}

export default new AdminOrderApi();