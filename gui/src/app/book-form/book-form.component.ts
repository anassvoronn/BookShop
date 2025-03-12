import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {Book} from "../entity/book.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BookService} from "../service/book.service";
import {GenreService} from "../service/genre.service";
import {Genre} from "../entity/genre.model";

@Component({
    selector: 'app-book-form',
    templateUrl: './book-form.component.html',
    styleUrls: ['./book-form.component.css']
})
export class BookFormComponent implements OnInit {

    bookForm!: FormGroup;
    bookId!: string | null;

    genreOptions: { value: string, label: string }[] = [];

    fetchGenreOptions(): void {
        this.genreService.getAllGenres().subscribe(genres => {
            this.genreOptions = genres;
        });
    }

    constructor(private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private genreService: GenreService,
                private snackBar: MatSnackBar,
                private bookService: BookService) {
    }

    ngOnInit(): void {
        this.fetchGenreOptions();
        this.route.paramMap.subscribe((params) => {
            if (params.has('id')) {
                this.bookId = params.get('id');
                this.bookService.getById(this.bookId).subscribe(book => {
                    this.bookForm.setValue({
                        title: book.title,
                        publishingYear: book.publishingYear,
                        genre: book.genre
                    });
                })
            }
        });

        this.bookForm = this.formBuilder.group({
            title: ['', Validators.required],
            publishingYear: [''],
            genre: ['', Validators.required]
        });
    }

    updateBook() {
        if (this.bookForm.valid) {
            this.bookService.updateBook(
                new Book(
                    this.bookId == null ? 0 : Number(this.bookId),
                    this.bookForm.controls['title']!.value,
                    "0",
                    this.bookForm.controls['publishingYear']!.value,
                    this.bookForm.controls['genre']!.value,
                    10
                )
            ).subscribe(
               (responseText: string) => {
                   this.snackBar.open(responseText, 'Close', {
                       duration: 15000, // Set the duration for which the message will be displayed
                   });
                   this.ngOnInit();
               },
               error => {
                   this.snackBar.open('Error updating book: ' + error.message, 'Close', {
                       duration: 15000,
                   });
               }
           );
        }
    }
}
