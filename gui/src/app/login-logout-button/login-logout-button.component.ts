import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticatorService} from "../service/authenticator.service";

@Component({
  selector: 'app-login-logout-button',
  templateUrl: './login-logout-button.component.html',
  styleUrls: ['./login-logout-button.component.css']
})
export class LoginAndLogoutButtonComponent implements OnInit {
    sessionId: string = '';
    isLoggedIn = false;

    constructor(
        private router: Router,
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
