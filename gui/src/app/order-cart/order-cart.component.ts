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

    increaseQuantity(item: OrderItem, amountToAdd: number): void {
        this.orderService.updateBookQuantity(this.sessionId, item.bookId, amountToAdd).subscribe(
            () => {
                item.quantity += amountToAdd;
                console.log('Quantity was increased successfully');
                this.calculateTotalPrice();
            },
            error => {
                console.error('Error increasing quantity', error);
            }
        );
    }

    decreaseQuantity(item: OrderItem, amountToAdd: number): void {
        if (item.quantity <= 0) {
            return;
        }
        this.increaseQuantity(item, amountToAdd);
    }

    clearCart(): void {
        this.orderService.deleteOrderItems(this.sessionId).subscribe({
            next: () => {
                this.cart = new Order(0, 0, '', []);
                this.calculateTotalPrice();
                this.snackBar.open('All items have been successfully removed from the cart.', 'Close', { duration: 2000 });
            },
            error: (error) => {
                console.error('Error clearing cart', error);
                this.snackBar.open('Error clearing cart', 'Close', { duration: 2000 });
            }
        });
    }

    deleteItem(id: number) {
        this.orderService.deleteOrderItem(id, this.sessionId).subscribe(
            () => {
                console.log('Item deleted successfully');
                this.snackBar.open('Item deleted successfully', 'Close', {
                    duration: 2000,
                });
                this.loadOrder();
            },
            error => {
                console.error('Error deleting item', error);
                this.snackBar.open('Error deleting item', 'Close', {
                    duration: 2000,
                });
            }
        );
    }
}
