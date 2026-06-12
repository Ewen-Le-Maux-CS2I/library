import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ouvrage } from '../models/ouvrage.model';

@Injectable({
  providedIn: 'root',
})
export class OuvrageService {
  private apiUrl = 'http://localhost:8080/api/ouvrages';

  constructor(private http: HttpClient) {}

  getOuvrages(): Observable<Ouvrage[]> {
    return this.http.get<Ouvrage[]>(this.apiUrl);
  }
}
