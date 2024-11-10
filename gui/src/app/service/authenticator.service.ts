import {Injectable} from '@angular/core';
import {AuthenticationResponse} from "../entity/authenticationResponse.model";
import {AuthenticatorRequest} from "../entity/authenticatorRequest.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthenticatorService {
    private apiUrl: string = '/book-shop-authenticator/api/authenticator/login';

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
}
