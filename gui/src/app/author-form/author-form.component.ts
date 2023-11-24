import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {AuthorService} from "../service/author.service";

@Component({
    selector: 'app-author-form',
    templateUrl: './author-form.component.html',
    styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent implements OnInit {

    authorForm!: FormGroup;
    authorId!: string | null;

    constructor(private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private authorService: AuthorService) {
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            if (params.has('id')) {
                this.authorId = params.get('id');
                this.authorService.getById(this.authorId).subscribe(author => {
                    this.authorForm.setValue({
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

        this.authorForm = this.formBuilder.group({
            name: ['', Validators.required],
            gender: ['', Validators.required],
            birthDate: ['', Validators.required],
            deathDate: ['', Validators.required],
            country: ['', Validators.required],
            age: ['', Validators.required]
        });
    }

    updateAuthor() {
        if (this.authorForm.valid) {
            // Handle author creation logic here
        }
    }
}
