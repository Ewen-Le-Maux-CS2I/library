import { Component, OnInit, signal } from '@angular/core';
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
  livres = signal<Livre[]>([]);

  constructor(private livreService: LivreService) {}

  ngOnInit(): void {
    this.livreService.getLivres().subscribe({
      next: (data) => this.livres.set(data),
      error: (err) => console.error('Erreur lors du chargement des livres', err),
    });
  }
}
