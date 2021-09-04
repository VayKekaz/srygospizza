import {Component} from "react";
import {Link} from "react-router-dom";
import "./css/NavigationBar.scss"
import UserApi from "../backendApi/UserApi";


export default class NavigationBar extends Component<any, any> {

    render() {
        return <header className="navigationBar noselect">
            <nav className="navigationLinkBar" style={{display: "flex", alignItems: "center"}}>
                <Link to="/menu">Menu</Link>
                <Link to="/checkout">Checkout</Link>
                <Link to="/orders">Orders</Link>
            </nav>
            <NavigationBarUserButton/>
        </header>;
    }
}

class NavigationBarUserButton extends Component<any, any> {
    private listenerIndex = -1;

    componentDidMount() {
        this.listenerIndex = UserApi.listeners.add(() => this.forceUpdate());
    }

    componentWillUnmount() {
        UserApi.listeners.remove(this.listenerIndex);
    }

    render() {
        const userAuthorized = UserApi.isLoggedIn()
        return <div className="userButton">
            <span className="nickname">{
                userAuthorized
                    ? UserApi.getUsername()
                    : "Anonymous"
            }</span>
            <img
                style={{filter: userAuthorized ? "" : "invert(1)"}}
                className="userAvatar"
                src={
                    userAuthorized
                        ? UserApi.getAvatar()
                        : "https://image.flaticon.com/icons/png/512/848/848043.png"
                }
                alt="user avatar"
            />
        </div>;
    }
}