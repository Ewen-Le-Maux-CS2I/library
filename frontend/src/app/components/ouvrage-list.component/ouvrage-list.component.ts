import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OuvrageService } from '../../services/ouvrage.service';
import { Ouvrage } from '../../models/ouvrage.model';

@Component({
  selector: 'app-ouvrage-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ouvrage-list.component.html',
})
export class OuvrageListComponent implements OnInit {
  ouvrages: Ouvrage[] = [];

  constructor(private ouvrageService: OuvrageService) {}

  ngOnInit(): void {
    this.ouvrageService.getOuvrages().subscribe({
      next: (data) => this.ouvrages = data,
      error: (err) => console.error('Erreur lors du chargement des ouvrages', err)
    });
  }
}