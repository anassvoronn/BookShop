import {Component, OnInit} from '@angular/core';
import {Book} from "../entity/book.model";
import {Genre} from "../entity/genre.model";
import {BookService} from "../service/book.service";
import {GenreService} from "../service/genre.service";
import {Author} from "../entity/author.model";
import {AuthorService} from "../service/author.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SessionContainerService} from "../service/session-container.service";
import {OrderService} from "../service/order.service";

@Component({
    selector: 'app-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
    books: Book[] = [];
    displayedColumns: string[] = ['title', 'publishingYear', 'genre', 'actions'];
    title: string = '';
    selectedGenre: Genre | null = null;
    selectedAuthorId: number | null = null;
    genres: Genre[] = [];
    publishingYear: string = '';
    totalBooks: number = 0;
    authors: Author[] = [];
    sessionId: string = '';

    constructor(
        private bookService: BookService,
        private snackBar: MatSnackBar,
        private genreService: GenreService,
        private authorService: AuthorService,
        private sessionContainerService: SessionContainerService,
        private orderService: OrderService) {
    }

    ngOnInit(): void {
        this.loadAllBooks();
        this.loadGenres();
        this.loadAuthors();
        this.sessionId = this.sessionContainerService.getSession();
        this.sessionContainerService.sessionStatus$.subscribe((sessionId) => {
            this.sessionId = sessionId;
        })
    }

    loadAllBooks(): void {
        this.bookService.getAllBooks().subscribe((books: Book[]) => {
            this.books = books;
            this.totalBooks = books.length;
        });
    }

    loadGenres(): void {
        this.genreService.getAllGenres().subscribe((genres: Genre[]) => {
            this.genres = [Genre.emptyGenre(), ...genres];
        });
    }

    loadAuthors(): void {
        this.authorService.getAllAuthors().subscribe((authors: Author[]) => {
            this.authors = [Author.emptyAuthor(), ...authors];
        });
    }

    updateBook(id: number): void {
    }

    buyBook(book: Book): void {
        if (!this.sessionId) {
            this.snackBar.open('You must be logged in to purchase the book.', 'Close', {
                duration: 5000,
            });
            return;
        }
        this.orderService.addToCart(book.id, this.sessionId).subscribe({
            next: (response: void) => {
                this.snackBar.open('Book added to cart!', 'Close', {
                    duration: 2000,
                });
            },
            error: (error) => {
                this.snackBar.open('Error occurred while adding book to cart.', 'Close', {
                    duration: 2000,
                });
                console.error('Error adding book to cart:', error);
            }
        });
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

    searchBooks(): void {
        if (this.title.trim() === '' && !this.selectedGenre && !this.publishingYear && !this.selectedAuthorId) {
            this.loadAllBooks();
        } else {
            this.bookService.searchBooks(this.title, this.selectedGenre, this.publishingYear, this.selectedAuthorId).subscribe(
                (foundBooks: Book[]) => {
                    this.books = foundBooks;
                    this.snackBar.open('Books found', 'Close', {
                        duration: 15000,
                    });
                },
                error => {
                    this.snackBar.open('Error searching books: ' + error.message, 'Close', {
                        duration: 15000,
                    });
                }
            );
        }
    }

    resetSearch(): void {
        this.title = '';
        this.selectedGenre = null;
        this.loadAllBooks();
        this.publishingYear = '';
        this.selectedAuthorId = null;
    }
}
