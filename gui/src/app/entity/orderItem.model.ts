import {Book} from "../entity/book.model";

export class OrderItem {
    private readonly _id: number;
    private readonly _bookId: number;
    private readonly _quantity: number;
    private readonly _price: number;
    private readonly _order: number;
    private _book: Book | null;

    constructor(id: number, bookId: number, quantity: number, price: number, order: number){
        this._id = id;
        this._bookId = bookId;
        this._quantity = quantity;
        this._price = price;
        this._order = order;
        this._book = null;
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

    get bookId(): number {
        return this._bookId;
    }

    get quantity(): number {
        return this._quantity;
    }

    get price(): number {
        return this._price;
    }

    get order(): number {
        return this._order;
    }

    get book(): Book | null {
        return this._book;
    }

    set book(value: Book) {
        this._book = value;
    }

}
