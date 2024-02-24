import {Injectable} from '@angular/core';
import {Country} from "../entity/country.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class CountryService {
    private apiUrl: string = "/book-shop/api/country";

    constructor(private http: HttpClient) {}

    getAllCountry(): Observable<Country[]> {
        return this.http.get<Country[]>(this.apiUrl);
    }
}
