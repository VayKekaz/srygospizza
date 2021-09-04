import React, {Component} from "react";
import "./css/OrderList.scss";
import OrderModel from "../model/OrderModel";
import AdminOrderApi from "../backendApi/AdminOrderApi";
import {dateToString} from "../util";
import OrderListDishCard from "./OrderListDishCard";


export default class OrderList extends Component<any, any> {

    private listenerIndex = -1;

    componentDidMount() {
        this.listenerIndex = AdminOrderApi.listeners.add(() => this.forceUpdate());
        AdminOrderApi.reload();
    }

    componentWillUnmount() {
        AdminOrderApi.listeners.remove(this.listenerIndex);
    }

    render() {
        const orders = AdminOrderApi.getOrders();
        const ordersAreEmpty = orders.length <= 0;
        return <div className={`orderList noselect ${this.props.className}`}>
            <div className="orderListTitle noselect">ACTIVE ORDERS</div>
            <hr className="orderListContentAndTitleDelimiter"/>
            {
                ordersAreEmpty
                    ? "No orders"
                    : <div className="orderListBody" style={{overflow: "none"}}>
                        {orders.map((item: OrderModel, i: number) => <OrderListItem key={i} item={item}/>)}
                    </div>
            }
        </div>;
    }
}

class OrderListItem extends Component<{ item: OrderModel }, any> {

    render() {
        const {address, dateOrdered, dishes} = this.props.item;
        return <div className="orderListItem">
            <div className="orderListItemInfo">
                <div className="orderListItemDate">
                    Ordered:<br/>
                    <span style={{fontWeight: 500}}>{
                        dateToString(dateOrdered)
                    }</span>
                </div>
                <div className="orderListItemAddress">
                    To:<br/>
                    <span style={{fontWeight: 500}}>{
                        address
                    }</span>
                </div>
            </div>
            <div className="orderListItemDishCardContainer">{
                dishes.map((item, i) =>
                    <OrderListDishCard key={i} item={item}/>,
                )
            }</div>
        </div>;
    }
}
