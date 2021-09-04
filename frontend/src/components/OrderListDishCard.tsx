import React, {Component} from "react";
import "./css/OrderListDishCard.scss";
import CartItemModel from "../model/CartItemModel";


export default class OrderListDishCard extends Component<{ item: CartItemModel }, any> {

    render() {
        const dish = this.props.item.dish;
        return <div className="orderListDishCard">
            <DishImage src={dish.image}/>
            <div className="orderListDishCardBody">
                <span className="dishCount noselect">{this.props.item.quantity} шт.</span>
                <div className="dishName noselect">{dish.name}</div>
            </div>
        </div>;
    }
}

const DishImage = (props: { src: string }) =>
    <div className="dishCardImage" style={{
        backgroundImage: `url(${props.src})`,
    }}/>;
