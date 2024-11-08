import { Injectable } from '@angular/core';
import { Session } from "../entity/session.model";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class SessionService {
    private apiUrl: string = "/book-shop-authenticator/api/session";

    constructor(private http: HttpClient) {}

    getById(sessionId: number): Observable<Session | null> {
        return this.http.get<Session | null>(this.apiUrl + "/" + sessionId);
    }

    saveSession(session: Session): Observable<string> {
        const requestParams = {
            responseType: 'text' as 'json',
            headers: { 'Content-Type': 'application/json' }
        };
    }

    deleteSession(sessionId: number): Observable<string> {
        return this.http.delete<string>(this.apiUrl + "/" + sessionId,
            { responseType: 'text' as 'json' }
        );
    }

   isSessionActive(sessionId: string): Observable<boolean> {
       return this.http.get<Session | null>(this.apiUrl + "/" + sessionId).pipe(
           map(session => session !== null)
       );
   }
}
