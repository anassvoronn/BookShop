import {Injectable} from '@angular/core';
import {Author} from "../entity/author.model";
import {SessionContainerService} from "../service/session-container.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthorService {
    private apiUrl: string = "/book-shop/api/author";

    constructor(private http: HttpClient,
                private sessionContainerService: SessionContainerService) {
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
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'sessionId': this.sessionContainerService.getSession()
        });
        const requestParams = {
            responseType: 'text' as 'json',
            headers: headers
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
        const headers = new HttpHeaders({
            'sessionId': this.sessionContainerService.getSession()
        });
        return this.http.delete<string>(this.apiUrl + "/" + authorId,
            {headers, responseType: 'text' as 'json'}
        );
    }
}
