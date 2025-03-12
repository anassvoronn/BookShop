import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthorListComponent} from './author-list/author-list.component';
import {AuthorDetailComponent} from './author-detail/author-detail.component';
import {AuthorFormComponent} from './author-form/author-form.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select"
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from "@angular/material/input";
import {MatNativeDateModule} from "@angular/material/core";
import {MatButtonModule} from "@angular/material/button";
import {MatTableModule} from "@angular/material/table";
import {HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {BookListComponent} from "./book-list/book-list.component";
import {BookDetailComponent} from './book-detail/book-detail.component';
import {BookFormComponent} from './book-form/book-form.component';
import {AuthenticationComponent} from "./authentication-form/authentication-form.component";
import {RegistrationComponent} from "./registration-form/registration-form.component";
import {LoginAndLogoutButtonComponent} from './login-logout-button/login-logout-button.component';
import {OrderComponent} from "./order-cart/order-cart.component";
import {CartButtonComponent} from './cart-button/cart-button.component';

@NgModule({
    declarations: [
        AppComponent,
        AuthorListComponent,
        BookListComponent,
        AuthorDetailComponent,
        BookDetailComponent,
        AuthorFormComponent,
        BookFormComponent,
        AuthenticationComponent,
        RegistrationComponent,
        LoginAndLogoutButtonComponent,
        OrderComponent,
        CartButtonComponent,
    ],
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        FormsModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        MatTableModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatButtonModule,
        MatSnackBarModule,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
