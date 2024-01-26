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

    updateAuthor(updatedAuthor: Author): Observable<string> {
        const requestParams = {
            responseType: 'text' as 'json',
            headers: {'Content-Type': 'application/json'}
        };
        if (updatedAuthor.id == 0) {
            return this.http.post<string>(this.apiUrl, updatedAuthor.toJsonString(),
                requestParams
            );
        } else {
            return this.http.put<string>(this.apiUrl, updatedAuthor.toJsonString(),
                requestParams
            );
        }
    }

    deleteAuthor(authorId: number): Observable<string> {
        return this.http.delete<string>(this.apiUrl + "/" + authorId,
            {responseType: 'text' as 'json'}
        );
    }
}
