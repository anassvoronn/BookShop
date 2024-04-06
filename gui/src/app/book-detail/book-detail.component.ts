import { Component } from '@angular/core';
import {BookService} from "../service/book.service";
import {Book} from "../entity/book.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent {

    bookDetail!: Book;
    bookId!: string | null;

    constructor(private bookService: BookService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            if (params.has('id')) {
                this.bookId = params.get('id');
                this.bookService.getById(this.bookId).subscribe(book => {
                    this.bookDetail = book;
                })
            }
        });
    }
}
