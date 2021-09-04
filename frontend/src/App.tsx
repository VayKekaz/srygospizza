import React, {Component,} from 'react';
import './App.scss';
import DishMenu from "./components/DishMenu";
import Cart from "./components/Cart";
import {Route, Switch, Redirect} from "react-router-dom";
import CheckoutForm from "./components/CheckoutForm";
import NavigationBar from "./components/NavigationBar";
import OrderList from "./components/OrderList";


export default class App extends Component {

    render() {
        console.log(process.env.REACT_APP_SRYGOSPIZZA_BACKEND_URL);
        return <div className="app fullscreen">
            <NavigationBar/>
            <div className="appPage">
                <Switch>
                    <Route path="/orders"><OrderList className="col-xs-12"/></Route>
                    <Route>
                        <Switch>
                            <Route path="/menu"><DishMenu/></Route>
                            <Route path="/checkout"><CheckoutForm/></Route>
                            <Route path="/"><Redirect to="/menu"/></Route>
                        </Switch>
                        <Route component={Cart}/>
                    </Route>
                </Switch>
            </div>
        </div>;
    }

}
