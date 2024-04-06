import {Injectable} from '@angular/core';
import {Genre} from "../entity/genre.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class GenreService {
    private apiUrl: string = "/book-shop/api/genre";

    constructor(private http: HttpClient) {}

    getAllGenres(): Observable<Genre[]> {
        return this.http.get<Genre[]>(this.apiUrl);
    }
}
