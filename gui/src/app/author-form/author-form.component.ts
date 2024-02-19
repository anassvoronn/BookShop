import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {Author} from "../entity/author.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AuthorService} from "../service/author.service";
import {GenderService} from "../service/gender.service";
import {Gender} from "../entity/gender.model";

@Component({
    selector: 'app-author-form',
    templateUrl: './author-form.component.html',
    styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent implements OnInit {

    authorForm!: FormGroup;
    authorId!: string | null;

    genderOptions: { value: string, label: string }[] = [];

    fetchGenderOptions(): void {
        this.genderService.getAllGenders().subscribe(genders => {
            this.genderOptions = genders;
        });
    }

    constructor(private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private authorService: AuthorService,
                private snackBar: MatSnackBar,
                private genderService: GenderService) {
    }

    ngOnInit(): void {
        this.fetchGenderOptions();
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
                    this.authorId == null ? 0 : Number(this.authorId),
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
