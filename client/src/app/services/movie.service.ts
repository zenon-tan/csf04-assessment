import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

const API_URL = 'https://csfassessment-production-7975.up.railway.app/api/search'
const API_LOCAL = 'http://localhost:8080/api/search'

@Injectable({
  providedIn: 'root'
})
export class MovieService {


  constructor(private http: HttpClient) { }

  getMovies(query: string) {

    const params = new HttpParams()
    .append('query', query)

    console.info(query)

    return firstValueFrom(this.http.get<string>(API_URL, { params }))

  }
}
