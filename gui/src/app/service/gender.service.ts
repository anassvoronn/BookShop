import {Injectable} from '@angular/core';
import {Gender} from "../entity/gender.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class GenderService {
    private apiUrl: string = "/book-shop/api/gender";

    constructor(private http: HttpClient) {}

    getAllGenders(): Observable<Gender[]> {
        return this.http.get<Gender[]>(this.apiUrl);
    }
}
