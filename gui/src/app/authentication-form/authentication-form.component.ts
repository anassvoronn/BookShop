import {Component, OnInit} from '@angular/core';
import {AuthenticationResponse} from "../entity/authenticationResponse.model";
import {AuthenticatorService} from "../service/authenticator.service";
import {Status} from "../entity/status.model";

@Component({
    selector: 'app-authentication-form',
    templateUrl: './authentication-form.component.html'
})
export class AuthenticationComponent {
    username: string = '';
    password: string = '';

    constructor(
        private authenticatorService: AuthenticatorService) {
    }

    login() {
        this.authenticatorService.login(this.username, this.password).subscribe({
            next: (response: AuthenticationResponse) => {
                if (response.status === "SUCCESS") {
                    console.log('Login successful! Session ID:', response.sessionId);
                } else {
                    console.error('Login failed:', response.status);
                }
            },
            error: (error) => {
                console.error('Error during login:', error);
            }
        });
    }
}
