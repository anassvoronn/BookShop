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
     title: string = '';

    constructor(
        private bookService: BookService,
        private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.loadAllBooks();
    }

    loadAllBooks(): void {
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
                this.loadAllBooks();
            },
            error => {
                this.snackBar.open('Error deleting book: ' + error.message, 'Close', {
                    duration: 15000,
                });
            }
        );
    }

    searchBooks() {
        if (this.title.trim() === '') {
            this.loadAllBooks();
        } else {
            this.bookService.searchBooks(this.title).subscribe(
                (foundBooks: Book[]) => {
                    this.books = foundBooks;
                    this.snackBar.open('Books found', 'Close', {
                        duration: 15000,
                    });
                },
                error => {
                    if (error.status === 204) {
                        this.books = [];
                        this.snackBar.open('No books found', 'Close', {
                            duration: 15000,
                        });
                    } else {
                        this.snackBar.open('Error searching books: ' + error.message, 'Close', {
                            duration: 15000,
                        });
                    }
                }
            );
        }
    }
}
