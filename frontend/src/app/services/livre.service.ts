import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LivreService {
  private apiUrl = 'http://localhost:8080/api/livres';

  constructor(private http: HttpClient) {}

  getLivres(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
