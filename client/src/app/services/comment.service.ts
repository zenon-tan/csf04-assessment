import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Comment } from '../components/models/Comment';
import { firstValueFrom } from 'rxjs';

const API_URL = 'https://csfassessment-production-7975.up.railway.app/api/comment'

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http : HttpClient) { }

  postComment(comment : Comment) {
    const c = new HttpParams()
    .set("name", comment.name)
    .set("title", comment.title)
    .set("rating", comment.rating)
    .set("comment", comment.comment)

  const headers = new HttpHeaders()
  .set("Content-Type", 'application/x-www-form-urlencoded')

  return firstValueFrom(this.http.post<any>(API_URL, c, {headers : headers}))
  }
}
