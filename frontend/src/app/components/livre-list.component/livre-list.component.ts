import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LivreService } from '../../services/livre.service';
import { Livre } from '../../models/livre.model';

@Component({
  selector: 'app-livre-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './livre-list.component.html',
})
export class LivreListComponent implements OnInit {
  livres: Livre[] = [];

  constructor(private livreService: LivreService) {}

  ngOnInit(): void {
    this.livreService.getLivres().subscribe({
      next: (data) => (this.livres = data),
      error: (err) => console.error('Erreur lors du chargement des livres', err),
    });
  }
}
