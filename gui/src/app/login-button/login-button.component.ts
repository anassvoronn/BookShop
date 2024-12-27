import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SessionContainerService} from "../service/session-container.service";

@Component({
  selector: 'app-login-button',
  templateUrl: './login-button.component.html',
  styleUrls: ['./login-button.component.css']
})
export class LoginButtonComponent implements OnInit {
    sessionId: string = '';

    constructor(
        private router: Router,
        private sessionService: SessionContainerService) {
    }

     ngOnInit(): void {
         this.checkSession();
     }

    checkSession() {
        this.sessionId = this.sessionService.getSession();
    }

    onLogin() {
        this.router.navigate(['/authentication-form']);
    }

    onLogout() {
        this.sessionService.setSession('');
        this.checkSession();
        this.router.navigate(['/book-list']);
    }
}
