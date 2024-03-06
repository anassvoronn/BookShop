import { Component } from '@angular/core';
import {AuthorService} from "../service/author.service";
import {Author} from "../entity/author.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-author-detail',
  templateUrl: './author-detail.component.html',
  styleUrls: ['./author-detail.component.css']
})
export class AuthorDetailComponent {

    authorDetail!: FormGroup;
    authorId!: string | null;

    constructor(private formBuilder: FormBuilder,
                private snackBar: MatSnackBar,
                private authorService: AuthorService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            if (params.has('id')) {
                this.authorId = params.get('id');
                this.authorService.getById(this.authorId).subscribe(author => {
                    this.authorDetail.setValue({
                        name: author.name,
                        gender: author.gender,
                        birthDate: author.birthDate,
                        deathDate: author.deathDate,
                        country: author.country,
                        age: author.age
                    });
                })
            }
        });

        this.authorDetail = this.formBuilder.group({
                    name: ['', Validators.required],
                    gender: ['', Validators.required],
                    birthDate: [''],
                    deathDate: [''],
                    country: ['', Validators.required],
                    age: ['']
        });
    }
}
