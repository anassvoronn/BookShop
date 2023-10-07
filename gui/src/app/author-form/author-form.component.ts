import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-author-form',
    templateUrl: './author-form.component.html',
    styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent implements OnInit {

    authorForm!: FormGroup;
    authorId!: string | null;

    constructor(private formBuilder: FormBuilder, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            if (params.has('id')) {
                this.authorId = params.get('id');
            }
        });

        this.authorForm = this.formBuilder.group({
            name: ['', Validators.required],
            gender: ['', Validators.required],
            birthDate: ['', Validators.required],
            country: ['', Validators.required],
        });
    }

    updateAuthor() {
        if (this.authorForm.valid) {
            // Handle author creation logic here
        }
    }
}
