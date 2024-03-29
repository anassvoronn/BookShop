import { Component } from '@angular/core';
import {AuthorService} from "../service/author.service";
import {Author} from "../entity/author.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-author-detail',
  templateUrl: './author-detail.component.html',
  styleUrls: ['./author-detail.component.css']
})
export class AuthorDetailComponent {

    authorDetail!: Author;
    authorId!: string | null;

    constructor(private authorService: AuthorService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            if (params.has('id')) {
                this.authorId = params.get('id');
                this.authorService.getById(this.authorId).subscribe(author => {
                    this.authorDetail = author;
                })
            }
        });
    }
}
