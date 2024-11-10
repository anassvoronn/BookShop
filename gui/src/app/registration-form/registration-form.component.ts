import { Component } from '@angular/core';
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
  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  register() {
    // Проверка на совпадение паролей
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Пароли не совпадают!';
      return;
    }

    // Создание нового пользователя
    const newUser: User = new User(0, this.username, this.password);

    // Проверка существования имени пользователя
    this.userService.existsByUsername(this.username).subscribe(oldUser => {
      if (oldUser) {
        this.errorMessage = 'Имя пользователя уже существует!';
      } else {
        // Сохранение нового пользователя
        this.userService.saveUser(newUser).subscribe(
          response => {
            this.successMessage = 'Пользователь успешно зарегистрирован!';
            this.router.navigate(['/authentication-form']); // Перенаправление на страницу входа
          },
          error => {
            this.errorMessage = 'Произошла ошибка при регистрации.';
            console.error(error);
          }
        );
      }
    });
  }

  resetForm() {
    // Сброс формы
    this.username = '';
    this.password = '';
    this.confirmPassword = '';
    this.errorMessage = '';
    this.successMessage = '';
  }
}
