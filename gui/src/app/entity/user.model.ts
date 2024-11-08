export class User {
    private readonly _id: number;
    private readonly _username: string;
    private readonly _password: string;

    constructor(id: number, username: string, password: string) {
        this._id = id;
        this._username = username;
        this._password = password;
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

    get username(): string {
        return this._username;

    }

    get password(): string {
        return this._password;
    }
}
