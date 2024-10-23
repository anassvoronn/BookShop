import {Component, OnInit} from '@angular/core';
import {Book} from "../entity/book.model";
import {Genre} from "../entity/genre.model";
import {BookService} from "../service/book.service";
import {GenreService} from "../service/genre.service";
import {Author} from "../entity/author.model";
import {AuthorService} from "../service/author.service";
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
    selectedGenre: Genre | null = null;
    selectedAuthor: Author | null = null;
    genres: Genre[] = [];
    publishingYear: string = '';
    totalBooks: number = 0;
    authors: Author[] = [];

    constructor(
        private bookService: BookService,
        private snackBar: MatSnackBar,
        private genreService: GenreService,
        private authorService: AuthorService) {
    }

    ngOnInit(): void {
        this.loadAllBooks();
        this.loadGenres();
        this.loadAuthors();
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
        if (this.title.trim() === '' && !this.selectedGenre && !this.publishingYear && !this.selectedAuthor) {
            this.loadAllBooks();
        } else {
            const authorId = this.selectedAuthor ? this.selectedAuthor.id : null;
            this.bookService.searchBooks(this.title, this.selectedGenre, this.publishingYear, authorId).subscribe(
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
        this.selectedAuthor = null;
    }
}
