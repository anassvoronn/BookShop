import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {OrderService} from "../service/order.service";
import {Order} from '../entity/order.model';
import {OrderItem} from '../entity/orderItem.model';
import {SessionContainerService} from "../service/session-container.service";

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
        private sessionContainerService: SessionContainerService) {
    }

    ngOnInit(): void {
        this.sessionId = this.sessionContainerService.getSession();
        this.loadOrder();
    }

    loadOrder(): void {
        this.orderService.getOrder(this.sessionId).subscribe(
            (order: Order) => {
             this.cart = order;
        },
        error => {
            this.snackBar.open('Error loading order', 'Close', {
              duration: 3000,
            });
        });
    }
}
