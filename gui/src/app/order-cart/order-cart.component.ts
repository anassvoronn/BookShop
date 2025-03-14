import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {OrderService} from "../service/order.service";
import {Order} from '../entity/order.model';
import {OrderItem} from '../entity/orderItem.model';
import {SessionContainerService} from "../service/session-container.service";
import {Book} from "../entity/book.model";
import {BookService} from "../service/book.service";

@Component({
    selector: 'app-order-cart',
    templateUrl: './order-cart.component.html',
    styleUrls: ['./order-cart.component.css']
})
export class OrderComponent implements OnInit{
    cart: Order | null = null;
    sessionId: string = '';

    constructor(
        private orderService: OrderService,
        private snackBar: MatSnackBar,
        private sessionContainerService: SessionContainerService,
        private bookService: BookService) {
    }

    ngOnInit(): void {
        this.sessionId = this.sessionContainerService.getSession();
        this.loadOrder();
    }

    loadOrder(): void {
        this.orderService.getOrder(this.sessionId).subscribe(
            (order: Order) => {
             this.cart = order;
             this.loadBooksForOrderItems();
        },
        error => {
            this.snackBar.open('Error loading order', 'Close', {
              duration: 3000,
            });
        });
    }

    loadBooksForOrderItems(): void {
        if (this.cart && this.cart.items) {
            this.cart.items.forEach(item => {
                this.bookService.getById(item.bookId.toString()).subscribe(
                    (book: Book) => {
                        if (book) {
                            item.book = book;
                        } else {
                            console.warn('Book with ID ' + item.bookId + ' not found.');
                        }
                    },
                    error => {
                        console.error('Error fetching book with ID '+ item.bookId + ':', error);
                        this.snackBar.open('Error loading books', 'Close', {
                            duration: 3000,
                        });
                    }
                );
            });
        }
    }
}
