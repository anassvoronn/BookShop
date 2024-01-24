import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {Author} from "../entity/author.model";
import {MatSnackBar} from "@angular/material/snack-bar";
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
                private authorService: AuthorService,
                private snackBar: MatSnackBar) {
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
            birthDate: [''],
            deathDate: [''],
            country: ['', Validators.required],
            age: ['']
        });
    }

    updateAuthor() {
        if (this.authorForm.valid) {
            this.authorService.updateAuthor(
                new Author(
                    Number(this.authorId) ?? 0,
                    this.authorForm.controls['name']!.value,
                    this.authorForm.controls['gender']!.value,
                    this.authorForm.controls['birthDate']!.value,
                    this.authorForm.controls['deathDate']!.value,
                    this.authorForm.controls['country']!.value,
                    this.authorForm.controls['age']!.value
                )
            ).subscribe(
                         (responseText: string) => {
                             this.snackBar.open(responseText, 'Close', {
                                 duration: 15000, // Set the duration for which the message will be displayed
                             });
                             this.ngOnInit();
                         },
                         error => {
                             this.snackBar.open('Error updating author: ' + error.message, 'Close', {
                                 duration: 15000,
                             });
                         }
                     );
        }
    }
}
