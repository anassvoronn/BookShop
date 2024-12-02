export class AuthenticationResponse {
    private readonly _status: string;
    private readonly _sessionId: string;

    constructor(status: string, sessionId: string) {
        this._status = status;
        this._sessionId = sessionId;
    }

    get status(): string {
        return this._status;
    }

    get sessionId(): string {
        return this._sessionId;
    }
}
