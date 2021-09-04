import React, {Component} from "react";
import "./css/Cart.scss"
import OrderApi from "../backendApi/OrderApi";
import CartItem from "./CartItem";
import {Route, RouteComponentProps, Switch} from "react-router-dom";
import CartItemModel from "../model/CartItemModel";

export default class Cart extends Component<RouteComponentProps, any> {

    private listenerIndex = -1;

    componentDidMount() {
        this.listenerIndex = OrderApi.listeners.add(() => this.forceUpdate());
    }

    componentWillUnmount() {
        OrderApi.listeners.remove(this.listenerIndex);
    }

    render() {
        const items = OrderApi.getItems();
        const cartIsNotEmpty = OrderApi.getCartItemsCount() > 0;
        return <div className="col-xs-3">
            <div className="cart">
                <div className="cartTitle noselect">CART</div>
                <hr className="cartVerticalDelimiter"/>
                <div className="cartItemsContainer">{
                    cartIsNotEmpty
                        ? items.map((item, index) =>
                            <CartItem key={index} item={item}/>
                        )
                        : <div className="noselect" style={{
                            textAlign: "center",
                            fontSize: "1.5rem",
                            fontWeight: 200,
                        }}>Click on a dish to add it to your cart.</div>
                }</div>
                <hr className="cartVerticalDelimiter"/>
                <div className="totalDisplay">
                    Total: <span style={{fontWeight: 400}}>{this.getTotalPrice(items)}</span>
                </div>
                <Switch><Route path="/menu">{
                    cartIsNotEmpty
                        ? <button
                            className="proceedToCheckoutButton noselect"
                            onClick={this.proceedToCheckout}
                        >Proceed to Checkout</button>
                        : <button
                            className="proceedToCheckoutButton noselect"
                            disabled
                        >Proceed to Checkout</button>
                }</Route></Switch>
            </div>
        </div>;
    }

    private proceedToCheckout = () => {
        this.props.history.push("/checkout");
    }

    private getTotalPrice = (items: Array<CartItemModel>) => items.reduce(
        (l, r) => l + r.dish.price * r.quantity,
        0
    )
}
