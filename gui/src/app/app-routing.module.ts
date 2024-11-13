import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthorListComponent} from './author-list/author-list.component';
import {BookListComponent} from "./book-list/book-list.component";
import {AuthorFormComponent} from "./author-form/author-form.component";
import {BookFormComponent} from "./book-form/book-form.component";
import {AuthorDetailComponent} from "./author-detail/author-detail.component";
import {BookDetailComponent} from "./book-detail/book-detail.component";
import {AuthenticationComponent} from "./authentication-form/authentication-form.component";
import {RegistrationComponent} from "./registration-form/registration-form.component";

const routes: Routes = [
    {path: '', redirectTo: '/author-list', pathMatch: 'full'},
    {path: 'author-list', component: AuthorListComponent},
    {path: 'book-list', component: BookListComponent},
    {path: 'author-form', component: AuthorFormComponent},
    {path: 'book-form', component: BookFormComponent},
    {path: 'author-form/:id', component: AuthorFormComponent},
    {path: 'book-form/:id', component: BookFormComponent},
    {path: 'author-details/:id', component: AuthorDetailComponent},
    {path: 'book-details/:id', component: BookDetailComponent},
    {path: 'authentication-form', component: AuthenticationComponent},
    {path: 'registration-form', component: RegistrationComponent},
    // Add other routes if needed
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
