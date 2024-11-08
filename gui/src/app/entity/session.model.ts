export class Session {
    private readonly _id: number;
    private readonly _userId: number;
    private readonly _sessionId: string;

    constructor(id: number, userId: number, sessionId: string) {
        this._id = id;
        this._userId = userId;
        this._sessionId = sessionId;
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

    get sessionId(): string {
        return this._sessionId;
    }
}
