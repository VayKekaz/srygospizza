import React, {Component} from "react";
import "./css/OrderForm.scss"
import OrderApi from "../backendApi/OrderApi";

type OrderFormState = {
    addressInput: string;
}

export default class CheckoutForm extends Component<any, OrderFormState> {
    state: OrderFormState = {
        addressInput: "",
    }

    render() {
        const cartIsNotEmpty = OrderApi.getCartItemsCount() > 0;
        const addressInputIsCorrect = this.state.addressInput.length > 0
        return <div className="col-xs-9">
            <div className="checkoutForm">
                <div className="checkoutFormTitle noselect">CHECKOUT</div>
                <hr className="cartVerticalDelimiter"/>
                <div className="formFieldsContainer noselect">
                    <div className="inputContainer">
                        <label htmlFor="addressInput">Address</label>
                        <input onInput={this.addressInputChanged} id="addressInput" type="text"/>
                    </div>
                </div>
                <hr className="cartVerticalDelimiter"/>
                {
                    cartIsNotEmpty && addressInputIsCorrect
                        ? <button className="checkoutButton" onClick={this.checkout}>Checkout</button>
                        : <button className="checkoutButton" disabled>Checkout</button>
                }
            </div>
        </div>;
    }

    private addressInputChanged = (event: any) => {
        const address = event.target.value;
        this.setState({addressInput: address});
    }

    private checkout = async () => {
        await OrderApi.submitOrder(this.state.addressInput);
    }
}
