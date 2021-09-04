import React, {Component} from "react";
import DishModel from "../model/DishModel";
import OrderApi from "../backendApi/OrderApi";
import "./css/DishCard.scss";


export default class DishCard extends Component<{ dish: DishModel }, any> {

    render() {
        const dish = this.props.dish;
        return <div
            className="dishCard"
            onClick={() => OrderApi.addDish(dish)}
        >
            <DishImage src={dish.image}/>
            <div className="dishCardBody">
                <span className="dishPrice noselect">{dish.price}₽</span>
                <div className="dishName noselect">{dish.name}</div>
                <div style={{height: "4px"}}/>
                <div className="dishDescription">{dish.description}</div>
            </div>
        </div>;
    }
}

type DishImageProps = {
    src: string,
}

const DishImage = (props: DishImageProps) =>
    <div className="dishCardImage" style={{
        backgroundImage: `url(${props.src})`,
    }}/>;
