export class Order {
    private readonly _id: number;
    private readonly _userId: number;

    constructor(id: number, userId: number){
        this._id = id;
        this._userId = userId;
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
}
