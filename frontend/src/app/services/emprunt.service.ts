import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Emprunt } from '../models/emprunt.model';

@Injectable({
  providedIn: 'root'
})
export class EmpruntService {
  private apiUrl = 'http://localhost:8080/api/emprunts';

  constructor(private http: HttpClient) {}

  enregistrerEmprunt(idAdherent: number, idExemplaire: number): Observable<Emprunt> {
    return this.http.post<Emprunt>(this.apiUrl, { idAdherent, idExemplaire });
  }

  getEmpruntsActifs(): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(`${this.apiUrl}/actifs`);
  }
}