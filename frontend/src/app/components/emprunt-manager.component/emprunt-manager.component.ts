import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmpruntService } from '../../services/emprunt.service';
import { Emprunt } from '../../models/emprunt.model';

@Component({
  selector: 'app-emprunt-manager',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './emprunt-manager.component.html',
})
export class EmpruntManagerComponent implements OnInit {
  empruntForm: FormGroup;
  emprunts: Emprunt[] = [];

  constructor(
    private fb: FormBuilder,
    private empruntService: EmpruntService,
  ) {
    this.empruntForm = this.fb.group({
      idAdherent: ['', Validators.required],
      idExemplaire: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.chargerEmprunts();
  }

  chargerEmprunts() {
    this.empruntService.getEmpruntsActifs().subscribe((data) => (this.emprunts = data));
  }

  soumettreEmprunt() {
    if (this.empruntForm.valid) {
      const { idAdherent, idExemplaire } = this.empruntForm.value;
      this.empruntService.enregistrerEmprunt(idAdherent, idExemplaire).subscribe(() => {
        this.chargerEmprunts(); // Rafraîchit la liste
        this.empruntForm.reset();
      });
    }
  }
}
