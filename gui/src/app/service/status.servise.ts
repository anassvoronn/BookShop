import {Injectable} from '@angular/core';
import {Status} from "../entity/status.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class StatusService {
    private apiUrl: string = "/book-shop-authenticator/api/status";

    constructor(private http: HttpClient) {}

    getAllStatus(): Observable<Status[]> {
        return this.http.get<Status[]>(this.apiUrl);
    }
}
