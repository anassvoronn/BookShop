import {Injectable} from '@angular/core';
import {User} from "../entity/user.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl: string = "/book-shop-authenticator/api/user";

    constructor(private http: HttpClient) {
    }

    findByUsername(username: string): Observable<User | null> {
        return this.http.get<User | null>(this.apiUrl + "/" + username);
    }

    existsByUsername(username: string): Observable<boolean> {
        return this.http.get<boolean>(this.apiUrl + "/" + username);
    }

    saveUser(user: User): Observable<string> {
        const requestParams = {
            responseType: 'text' as 'json',
            headers: { 'Content-Type': 'application/json' }
        };
    }

    deleteUser(userId: number): Observable<string> {
        return this.http.delete<string>(this.apiUrl + "/" + userId,
            { responseType: 'text' as 'json' }
        );
    }
}
