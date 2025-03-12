import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SessionContainerService} from "../service/session-container.service";

@Component({
  selector: 'app-cart-button',
  templateUrl: './cart-button.component.html',
  styleUrls: ['./cart-button.component.css']
})
export class CartButtonComponent implements OnInit {
    sessionId: string = '';

    constructor(
        private router: Router,
        private sessionContainerService: SessionContainerService) {
    }

    ngOnInit(): void {
        this.sessionContainerService.sessionStatus$.subscribe((sessionId) => {
            this.sessionId = sessionId;
        });
    }

    loadCart(): void {
        this.router.navigate(['/order-cart']);
    }
}
