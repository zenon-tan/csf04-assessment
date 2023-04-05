import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { MovieService } from 'src/app/services/movie.service';
import { Review } from '../models/Review';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit, OnDestroy {

  param$!: Subscription
  query!: string
  reviews!: Review[]

  @Output()
  onQuery = new Subject<string>()

  constructor(private router : Router, 
    private activatedRoute : ActivatedRoute, private mSrc : MovieService) {}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.queryParams.subscribe(
      (param) => {
        this.query = param['query']

      }
    )

    this.mSrc.getMovies(this.query.toLowerCase()).then(
      (data:any) => {
        this.reviews = data['results'] as Review[]
        console.info(this.reviews)
      }
    ).catch(
      (error : HttpErrorResponse) => {
        this.reviews = []
      }
    )
  }

  goComment(idx : number) {
    const movie = this.reviews[idx].title
    // console.info(movie)
    this.router.navigate(['/comment', movie], {queryParams: {query: this.query}})
  }

  goBack() {
    this.router.navigate(["/"])
  }
  ngOnDestroy(): void {
    this.param$.unsubscribe()
  }

  goToLink(idx : number) {
    const link = this.reviews[idx].reviewUrl
    window.open(link, '_blank')
    this.router.navigate(['/search'], {queryParams: {query: this.query}})
  }

  

}
