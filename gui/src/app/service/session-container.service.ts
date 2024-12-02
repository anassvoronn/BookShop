import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class SessionContainerService {
    private sessionId: string = '';

    constructor() {}

    setSession(sessionId: string): void {
        this.sessionId = sessionId;
    }

    getSession(): string {
       return this.sessionId;
    }

}
