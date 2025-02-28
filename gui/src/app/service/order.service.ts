import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from 'rxjs/operators';
import {Book} from "../entity/book.model";
import {Order} from '../entity/order.model';

@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private apiUrl: string = "/book-shop-order/api/order";

    constructor(private http: HttpClient) {}

    getAllOrders(): Observable<Order[]> {
        return this.http.get<Order[]>(this.apiUrl);
    }

    addToCart(bookId: number, sessionId: string): Observable<string> {
        const body = { bookId };
        return this.http.put<string>(this.apiUrl, body,
        {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'sessionId': sessionId
            })
        }).pipe(
            map(response => response)
        );
    }
}
