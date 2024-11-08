import {Component, OnInit} from '@angular/core';
import {AuthenticationResponse} from "../entity/authenticationResponse.model";
import {AuthenticatorService} from "../service/authenticator.service";
import {Status} from "../entity/status.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'app-authentication-form',
    templateUrl: './authentication-form.component.html',
    styleUrls: ['./authentication-form.component.css']
})
export class AuthenticationComponent {
    username: string = '';
    password: string = '';

    constructor(
        private authenticatorService: AuthenticatorService,
        private snackBar: MatSnackBar) {
    }

    login() {
        this.authenticatorService.login(this.username, this.password).subscribe({
            next: (response: AuthenticationResponse) => {
                if (response.status === "SUCCESS") {
                    console.log('Login successful! Session ID:', response.sessionId);
                } else if (response.status === "USER_NOT_FOUND") {
                    this.snackBar.open('User not found. Please check your details.', 'Close', {
                        duration: 15000,
                    });
                } else if (response.status === "INVALID_PASSWORD") {
                    this.snackBar.open('Invalid password. Try again.', 'Close', {
                        duration: 15000,
                    });
                }
            },
            error: (error) => {
                this.snackBar.open('Login error: ' + error.message, 'Close', {
                    duration: 15000,
                });
            }
        });
    }

    resetForm(): void {
        this.username = '';
        this.password = '';
    }
}