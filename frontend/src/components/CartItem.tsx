import CartItemModel from "../model/CartItemModel";
import React, {Component} from "react";
import "./css/CartItem.scss"
import OrderApi from "../backendApi/OrderApi";

type CartItemProps = {
    item: CartItemModel;
}

export default class CartItem extends Component<CartItemProps, {}> {

    render() {
        const item = this.props.item;
        return <div style={{
            display: "flex",
            justifyContent: "space-between",
            margin: "12px",
        }}>
            <span className="cartItemQuantityControlContainer noselect">
                <div
                    className="cartItemQuantityControlButton incrementCartItemQuantity"
                    onClick={() => OrderApi.incrementItemQuantityByName(item.dish.name)}
                >+</div>
                <div className="cartItemQuantityControlButton cartItemQuantityDisplay">{item.quantity}</div>
                <div
                    className="cartItemQuantityControlButton decrementCartItemQuantity"
                    onClick={() => OrderApi.decrementItemQuantityByName(item.dish.name)}
                >{
                    item.quantity === 1
                        ? <span style={{color: "#dc3545"}}>×</span>
                        : "-"
                }</div>
            </span>
            <span className="cartItemName">{item.dish.name}</span>
            <span className="cartItemPrice">{item.dish.price * item.quantity}₽</span>
        </div>;
    }
}