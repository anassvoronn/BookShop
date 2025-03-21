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
    totalPrice: number = 0;

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
             this.calculateTotalPrice();
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

    calculateTotalPrice(): void {
        this.totalPrice = 0;
        if (!this.cart || !this.cart.items) {
            return;
        }
        this.cart.items.forEach(item => {
            if (item.price !== undefined && item.quantity !== undefined) {
                this.totalPrice += item.price * item.quantity;
            }
        });
    }

    increaseQuantity(item: OrderItem): void {
        this.orderService.updateBookQuantity(this.sessionId, item.bookId, 1).subscribe(
            () => {
                item.quantity += 1;
                console.log('Quantity increased successfully');
                this.calculateTotalPrice();
            },
            error => {
                console.error('Error increasing quantity', error);
            }
        );
    }

    decreaseQuantity(item: OrderItem): void {
        if (item.quantity <= 0) {
            return;
        }
        this.orderService.updateBookQuantity(this.sessionId, item.bookId, -1).subscribe(
            () => {
                item.quantity -= 1;
                console.log('Quantity decreased successfully');
                this.calculateTotalPrice();
            },
            error => {
                console.error('Error decreasing quantity', error);
            }
        );
    }
}
