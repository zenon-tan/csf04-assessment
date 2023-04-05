import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CommentService } from 'src/app/services/comment.service';
import { Comment } from '../models/Comment';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit, OnDestroy {

  param$!: Subscription
  qparam$!: Subscription
  title!: string
  form!: FormGroup
  comment!: Comment
  query!: string

  constructor(private router : Router, 
    private activatedRoute : ActivatedRoute,
    private cSrc : CommentService,
    private fb: FormBuilder) {}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (param) => {
        this.title = param['title']
        // console.info(this.title)
      }
    )

    this.qparam$ = this.activatedRoute.queryParams.subscribe(
      (param) => {
        this.query = param['query']
        // console.info(this.query)
      }
    )

    this.form = this.fb.group({
      name: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      rating: this.fb.control<number>(3, [Validators.required, Validators.max(5)]),
      comment: this.fb.control<string>("", [Validators.required])
    })
  }

  submitComment() {

    this.comment = {

      name: this.form.value['name'],
      title: this.title,
      rating: this.form.value['rating'],
      comment: this.form.value['comment']
    }

    console.info(this.comment)

    this.cSrc.postComment(this.comment).then(
      (data) => console.info(data['response'])
    )
    this.router.navigate(["/search"], {queryParams: {query: this.query}})

  }

  goback() {
    this.form.reset()
    this.router.navigate(["/search"], {queryParams: {query: this.query}})
  }
  ngOnDestroy(): void {
    this.param$.unsubscribe()
    this.qparam$.unsubscribe()
  }

  



}
