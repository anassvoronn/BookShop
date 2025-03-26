import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError, map} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {Order} from '../entity/order.model';
import {OrderItem} from '../entity/orderItem.model';

@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private apiUrl: string = "/book-shop-order/api/order";

    constructor(private http: HttpClient) {}

    getAllOrders(): Observable<Order[]> {
        return this.http.get<Order[]>(this.apiUrl);
    }

    addToCart(bookId: number, sessionId: string): Observable<void> {
        const body = { bookId };
        return this.http.put<void>(this.apiUrl, body,
        {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'sessionId': sessionId
            })
        }).pipe(
            map(response => response)
        );
    }

    getOrder(sessionId: string): Observable<Order> {
        return this.http.get<Order>(this.apiUrl,
        {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'sessionId': sessionId
            })
        }).pipe(
            map(response => response),
            catchError(error => {
              console.error('Error fetching order', error);
              return throwError(error);
            })
        );
    }

    updateBookQuantity(sessionId: string, bookId: number, amountToAdd: number): Observable<void> {
        const body = { bookId, amountToAdd };
        return this.http.put<void>(this.apiUrl + '/updateQuantity', body,
        {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'sessionId': sessionId
            })
        }).pipe(
        catchError(error => {
                console.error('Error updating book quantity', error);
                return throwError(error);
            })
        );
    }

    deleteOrderItems(sessionId: string): Observable<void> {
        return this.http.delete<void>(this.apiUrl + '/delete', {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'sessionId': sessionId
            })
        }).pipe(
            catchError(error => {
                console.error('Error clearing order', error);
                return throwError(error);
            })
        );
    }

    deleteOrderItem(itemId: number, sessionId: string): Observable<void> {
        return this.http.delete<void>(this.apiUrl + "/delete/" + itemId, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'sessionId': sessionId
            })
        }).pipe(
            catchError(error => {
                console.error('Error deleting order item', error);
                return throwError(error);
            })
        );
    }
}
