import DishModel from "./DishModel";

export default interface CartItemModel {
    quantity: number;
    dish: DishModel;
}