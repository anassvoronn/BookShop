import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthorListComponent} from './author-list/author-list.component';
import {AuthorFormComponent} from "./author-form/author-form.component";
import {AuthorDetailComponent} from "./author-detail/author-detail.component";

const routes: Routes = [
    {path: '', redirectTo: '/author-list', pathMatch: 'full'},
    {path: 'author-list', component: AuthorListComponent},
    {path: 'author-form', component: AuthorFormComponent},
    {path: 'author-form/:id', component: AuthorFormComponent},
    {path: 'author-details/:id', component: AuthorDetailComponent},
    // Add other routes if needed
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
