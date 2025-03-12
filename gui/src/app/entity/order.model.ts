import {OrderItem} from '../entity/orderItem.model';

export class Order {
    private readonly _id: number;
    private readonly _userId: number;
    private readonly _status: string;
    private readonly _items: OrderItem[];

    constructor(id: number, userId: number, status: string, items: OrderItem[]){
        this._id = id;
        this._userId = userId;
        this._status = status;
        this._items = items;
    }

    toJsonString(): string {
        let json = JSON.stringify(this);
        Object.keys(this).filter(key => key[0] === "_").forEach(key => {
            json = json.replace(key, key.substring(1));
        });

        return json;
    }

    get id(): number {
        return this._id;
    }

    get userId(): number {
        return this._userId;
    }

    get status(): string {
        return this._status;
    }

    get items(): OrderItem[] {
        return this._items;
    }
}
