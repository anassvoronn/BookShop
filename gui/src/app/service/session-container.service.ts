import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SessionContainerService {
    private sessionId: string = '';
    private sessionSubject = new Subject<string>();
    sessionStatus$ = this.sessionSubject.asObservable();

    constructor() {}

    setSession(sessionId: string): void {
        this.sessionId = sessionId;
        this.sessionSubject.next(sessionId);
    }

    getSession(): string {
       return this.sessionId;
    }
}
