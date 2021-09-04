import React, {Component, ReactNode} from "react";
import './css/DishMenu.scss'
import DishCard from "./DishCard";
import DishApi from "../backendApi/DishApi";
import {switchStatus} from "../util/LoadingStatus";


export default class DishMenu extends Component {

    private listenerIndex = -1;

    componentDidMount() {
        this.listenerIndex = DishApi.listeners.add(() => this.forceUpdate());
        // noinspection JSIgnoredPromiseFromCall
        DishApi.reload();
    }

    componentWillUnmount() {
        DishApi.listeners.remove(this.listenerIndex);
    }

    render() {
        return <div className="dishMenu col-xs-9">
            <div className="menuTitle noselect">OUR MENU</div>
            <hr className="menuTitleAndContentDelimiter"/>
            <div className="dishCardContainer">{
                switchStatus<ReactNode>(DishApi.status, {
                    onLoaded: getDishesAsCards,
                    onLoading: () => "loading...",
                    onError: () => <div>
                        Error loading menu. <button onClick={DishApi.reload}>retry</button>
                    </div>,
                })
            }</div>
        </div>;
    }
}

function getDishesAsCards(): ReactNode {
    return DishApi.getDishes().map(
        (dish, index) => <DishCard key={index} dish={dish}/>
    );
}
