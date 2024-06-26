import {Component, OnInit} from '@angular/core';
import {Book} from "../entity/book.model";
import {BookService} from "../service/book.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
    books: Book[] = [];
    displayedColumns: string[] = ['title', 'publishingYear', 'genre', 'actions'];

    constructor(
        private bookService: BookService,
        private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.bookService.getAllBooks().subscribe((books: Book[]) => {
            this.books = books;
        });
    }

    updateBook(id: number): void {
    }

    deleteBook(id: number): void {
        this.bookService.deleteBook(id).subscribe(
            (responseText: string) => {
                this.snackBar.open(responseText, 'Close', {
                    duration: 15000, // Set the duration for which the message will be displayed
                });
                this.ngOnInit();
            },
            error => {
                this.snackBar.open('Error deleting book: ' + error.message, 'Close', {
                    duration: 15000,
                });
            }
        );
    }
}
