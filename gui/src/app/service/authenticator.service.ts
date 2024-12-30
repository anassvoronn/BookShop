import {Injectable} from '@angular/core';
import {AuthenticationResponse} from "../entity/authenticationResponse.model";
import {AuthenticatorRequest} from "../entity/authenticatorRequest.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Subject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthenticatorService {
    private apiUrl: string = '/book-shop-authenticator/api/authenticator/login';
    private loginStatusSubject = new Subject<boolean>();
    loginStatus$ = this.loginStatusSubject.asObservable();

    constructor(private http: HttpClient) {}

    login(username: string, password: string): Observable<AuthenticationResponse> {
        const requestParams = {
            headers: { 'Content-Type': 'application/json' }
        };
        const authenticatorRequest = new AuthenticatorRequest(username, password);
        return this.http.post<AuthenticationResponse>(this.apiUrl, authenticatorRequest.toJsonString(),
            requestParams
        );
    }

    updateLoginStatus(isLoggedIn: boolean) {
        this.loginStatusSubject.next(isLoggedIn);
    }
}
