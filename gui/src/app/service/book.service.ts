import {Injectable} from '@angular/core';
import {Book} from "../entity/book.model";
import {Genre} from "../entity/genre.model";
import {SessionContainerService} from "../service/session-container.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class BookService {
    private readonly apiUrl: string = "/book-shop/api/book";
    private readonly apiUrlViews: string = "/book-shop/api/bookViews/";
    private readonly apiUrlSearch: string = "/book-shop/api/book/search";

    constructor(private http: HttpClient,
                private sessionContainerService: SessionContainerService) {
    }

    getAllBooks(): Observable<Book[]> {
        return this.http.get<Book[]>(this.apiUrl);
    }

    getById(bookId: string | null) {
        return this.http.get<Book>(this.apiUrl + "/" + bookId);
    }

    addBook(book: Book): void {
    }

    updateBook(updatedBook: Book): Observable<string> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'sessionId': this.sessionContainerService.getSession()
        });
        const requestParams = {
            responseType: 'text' as 'json',
            headers: headers
        };
        if (updatedBook.id == 0) {
            return this.http.post<string>(this.apiUrl, updatedBook.toJsonString(),
                requestParams
            );
        } else {
            return this.http.put<string>(this.apiUrl, updatedBook.toJsonString(),
                requestParams
            );
        }
    }

    deleteBook(bookId: number): Observable<string> {
        const headers = new HttpHeaders({
            'sessionId': this.sessionContainerService.getSession()
        });
        return this.http.delete<string>(this.apiUrl + "/" + bookId,
            {headers, responseType: 'text' as 'json'}
        );
    }

    incrementViewCount(bookId: number): void {
        this.http.post<string>(this.apiUrlViews + bookId, {}).subscribe();
    }

    searchBooks(title: string, genre: Genre | null, publishingYear: string, authorId: number | null): Observable<Book[]> {
        let searchUrl = this.apiUrlSearch + '?title=' + encodeURIComponent(title);
        if (genre) {
            searchUrl += '&genre=' + encodeURIComponent(genre.value);
        }
        if (publishingYear) {
            searchUrl += '&publishingYear=' + encodeURIComponent(publishingYear);
        }
        if (authorId !== null) {
            searchUrl += '&authorId=' + authorId;
        }
        return this.http.get<Book[]>(searchUrl);
    }
}
