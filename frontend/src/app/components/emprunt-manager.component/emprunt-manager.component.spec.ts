import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EmpruntManagerComponent } from './emprunt-manager.component';
import { EmpruntService } from '../../services/emprunt.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('EmpruntManagerComponent', () => {
  let component: EmpruntManagerComponent;
  let fixture: ComponentFixture<EmpruntManagerComponent>;

  // 1. Configuration du mock natif TypeScript (Solution 2, sans Jasmine global)
  const mockEmpruntService = {
    enregistrerEmpruntAppele: false,
    valeursEnregistrees: {} as any,

    getEmpruntsActifs() {
      return of([]); // Renvoie le tableau vide nécessaire pour le ngOnInit
    },

    enregistrerEmprunt(idAdherent: number, idExemplaire: number) {
      this.enregistrerEmpruntAppele = true;
      this.valeursEnregistrees = { idAdherent, idExemplaire };
      return of({}); // Renvoie un flux valide pour la souscription (.subscribe)
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpruntManagerComponent, ReactiveFormsModule],
      providers: [
        { provide: EmpruntService, useValue: mockEmpruntService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EmpruntManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // Déclenche le ngOnInit
  });

  it('devrait soumettre le formulaire et appeler le service avec les bonnes valeurs', () => {
    // Remplissage du formulaire réactif
    component.empruntForm.setValue({ idAdherent: 101, idExemplaire: 5 });
    
    // Appel de la méthode de soumission
    component.soumettreEmprunt();

    // Vérification basée sur les mouchards de notre mock personnalisé
    expect(mockEmpruntService.enregistrerEmpruntAppele).toBe(true);
    expect(mockEmpruntService.valeursEnregistrees).toEqual({ idAdherent: 101, idExemplaire: 5 });
  });
});