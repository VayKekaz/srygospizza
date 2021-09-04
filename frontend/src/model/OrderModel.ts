import CartItemModel from "./CartItemModel";

export default interface OrderModel {
    id?: number;
    address: string;
    dateOrdered: Date;
    dishes: Array<CartItemModel>;
};
