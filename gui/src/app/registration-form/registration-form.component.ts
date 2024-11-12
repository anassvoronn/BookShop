import { Component } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import { UserService } from '../service/user.service';
import { User } from '../entity/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationComponent {
    username: string = '';
    password: string = '';
    confirmPassword: string = '';

    constructor(
        private userService: UserService,
        private router: Router,
        private snackBar: MatSnackBar) {
    }

    register() {
        if (this.password !== this.confirmPassword) {
            this.snackBar.open('Passwords do not match!', 'Close', {
                duration: 15000,
            });
            return;
        }
        const newUser: User = new User(0, this.username, this.password);
        this.userService.existsByUsername(this.username).subscribe(oldUser => {
            if (oldUser) {
                this.snackBar.open('Username already exists!', 'Close', {
                    duration: 15000,
                });
            } else {
                this.userService.saveUser(newUser).subscribe(
                response => {
                    this.snackBar.open('The user has been successfully registered!', 'Close', {
                        duration: 15000,
                    });
                    this.router.navigate(['/authentication-form']);
                },
                error => {
                    this.snackBar.open('An error occurred during registration.' +  error.message, 'Close', {
                        duration: 15000,
                    });
                    console.error(error);
                });
            }
        });
    }

    resetForm() {
        this.username = '';
        this.password = '';
        this.confirmPassword = '';
    }
}
