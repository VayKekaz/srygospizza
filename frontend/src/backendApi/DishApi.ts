import DishModel from "../model/DishModel";
import clonedeep from "lodash/cloneDeep";
import {SRYGOSPIZZA_BACKEND_URL} from "../config";
import ListenerHolder from "../util/ListenerHolder";
import LoadingStatus from "../util/LoadingStatus";


class DishApi {
    private _status: LoadingStatus = LoadingStatus.loading;
    public get status() {
        return this._status;
    }

    private dishes: Array<DishModel> = [];

    private currentPageNumber: number = 0;
    private currentPageSize: number = 12;

    private setPageProperties(pageNumber: number = 0, pageSize: number = 12) {
        this.currentPageNumber = pageNumber;
        this.currentPageSize = pageSize;
    }

    public getDishes = (): Array<DishModel> =>
        clonedeep(this.dishes);

    public reload = async (): Promise<Array<DishModel>> =>
        this.load(this.currentPageNumber, this.currentPageSize);

    public load = async (
        pageNumber: number = this.currentPageNumber,
        pageSize: number = this.currentPageSize,
    ): Promise<Array<DishModel>> => {
        this.setPageProperties(pageNumber, pageSize)
        this._status = LoadingStatus.loading;
        this.listeners.notify();
        try {
            const url = `${SRYGOSPIZZA_BACKEND_URL}/dishes?pageNumber=${pageNumber}&pageSize=${pageSize}`
            const response = await fetch(url);
            const responseJson = await (response).json();
            this.dishes = responseJson.content as Array<DishModel>;
            this._status = LoadingStatus.loaded;
            this.listeners.notify();
            return this.dishes;
        } catch (error) {
            this._status = LoadingStatus.error;
            this.listeners.notify();
            throw error;
        }
    }

    public readonly listeners =
        new ListenerHolder<{
            status: LoadingStatus,
            dishes: Array<DishModel>
        }>(() => {
            return {
                status: this.status,
                dishes: this.getDishes()
            };
        });
}

export default new DishApi();
