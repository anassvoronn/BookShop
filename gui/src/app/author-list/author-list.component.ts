import {Component, OnInit} from '@angular/core';
import {Author} from "../entity/author.model";
import {AuthorService} from "../service/author.service";

@Component({
    selector: 'app-author-list',
    templateUrl: './author-list.component.html',
    styleUrls: ['./author-list.component.css']
})
export class AuthorListComponent implements OnInit {
    authors: Author[] = [];
    displayedColumns: string[] = ['name', 'gender', 'birthDate', 'deathDate', 'country', 'actions'];

    constructor(private authorService: AuthorService) {
    }

    ngOnInit(): void {
        this.authorService.getAllAuthors().subscribe(authors => {
            this.authors = authors;
        });
    }

    updateAuthor(id: number): void {
    }

    deleteAuthor(id: number): void {
        this.authorService.deleteAuthor(id);
    }
}
