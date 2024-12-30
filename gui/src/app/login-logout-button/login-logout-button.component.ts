import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SessionContainerService} from "../service/session-container.service";
import {AuthenticatorService} from "../service/authenticator.service";

@Component({
  selector: 'app-login-button',
  templateUrl: './login-button.component.html',
  styleUrls: ['./login-button.component.css']
})
export class LoginAndLogoutButtonComponent implements OnInit {
    sessionId: string = '';
    isLoggedIn = false;

    constructor(
        private router: Router,
        private sessionService: SessionContainerService,
        private authenticatorService: AuthenticatorService) {
    }

     ngOnInit() {
         this.authenticatorService.loginStatus$.subscribe((status) => {
             this.isLoggedIn = status;
         });
     }

    onLogin() {
        this.authenticatorService.updateLoginStatus(true);
        this.router.navigate(['/authentication-form']);
    }

    onLogout() {
        this.authenticatorService.updateLoginStatus(false);
    }
}
