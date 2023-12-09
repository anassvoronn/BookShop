import {Component, OnInit} from '@angular/core';
import {Author} from "../entity/author.model";
import {AuthorService} from "../service/author.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-author-list',
    templateUrl: './author-list.component.html',
    styleUrls: ['./author-list.component.css']
})
export class AuthorListComponent implements OnInit {
    authors: Author[] = [];
    displayedColumns: string[] = ['name', 'gender', 'birthDate', 'deathDate', 'country', 'age', 'actions'];

    constructor(
        private authorService: AuthorService, 
        private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.authorService.getAllAuthors().subscribe(authors => {
            this.authors = authors;
        });
    }

    updateAuthor(id: number): void {
    }

    deleteAuthor(id: number): void {
        this.authorService.deleteAuthor(id).subscribe(
            () => {
                this.snackBar.open('Author deleted successfully', 'Close', {
                    duration: 15000, // Set the duration for which the message will be displayed
                });
                this.ngOnInit();
            },
            error => {
                this.snackBar.open('Error deleting author: ' + error.message, 'Close', {
                    duration: 15000,
                });
            }
        );
    }
}
