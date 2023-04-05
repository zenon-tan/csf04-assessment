import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MovieService } from 'src/app/services/movie.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  query!: string
  form!: FormGroup

  constructor(private fb: FormBuilder, private mSrc: MovieService,
    private router : Router){}

  ngOnInit(): void {
    this.form = this.fb.group({
      query: this.fb.control<string>('', [Validators.required, Validators.minLength(2)])
    })
  }

  checkIfWhiteSpace() {
    const qCtrl = this.form.controls['query'].value.trim()

    if(qCtrl.trim() == "") {
      return true
    }

    return false
  }

  processForm() {

    this.query = this.form.controls['query'].value
    this.router.navigate(["/search"], {queryParams: {query: this.query.trim()}})

  }

}
