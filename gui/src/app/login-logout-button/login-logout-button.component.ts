import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SessionContainerService} from "../service/session-container.service";

@Component({
  selector: 'app-login-logout-button',
  templateUrl: './login-logout-button.component.html',
  styleUrls: ['./login-logout-button.component.css']
})
export class LoginAndLogoutButtonComponent implements OnInit {
    sessionId: string = '';

    constructor(
        private router: Router,
        private sessionContainerService: SessionContainerService) {
    }

     ngOnInit() {
         this.sessionContainerService.sessionStatus$.subscribe((sessionId) => {
             this.sessionId = sessionId;
         });
     }

    onLogin() {
        this.router.navigate(['/authentication-form']);
    }

    onLogout() {
        this.sessionContainerService.setSession('');
    }
}
