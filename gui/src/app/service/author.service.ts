import {Injectable} from '@angular/core';
import {Author} from "../entity/author.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthorService {
    private apiUrl: string = "/book-shop/api/author";

    constructor(private http: HttpClient) {
    }

    getAllAuthors(): Observable<Author[]> {
        return this.http.get<Author[]>(this.apiUrl);
    }

    getById(authorId: string | null) {
        return this.http.get<Author>(this.apiUrl + "/" + authorId);
    }

    addAuthor(author: Author): void {
    }

    updateAuthor(id: number, updatedAuthor: Author): void {
    }

    deleteAuthor(authorId: number): Observable<void> {
        return this.http.delete<void>(this.apiUrl + "/" + authorId);
    }
}
