import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EmpruntManagerComponent } from './emprunt-manager.component';
import { EmpruntService } from '../../services/emprunt.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('EmpruntManagerComponent', () => {
  let component: EmpruntManagerComponent;
  let fixture: ComponentFixture<EmpruntManagerComponent>;
  let mockEmpruntService: any;

  beforeEach(async () => {
    mockEmpruntService = {
      enregistrerEmprunt: () => of({}),
      getEmpruntsActifs: () => of([]),
    };
    mockEmpruntService.getEmpruntsActifs.and.returnValue(of([]));

    await TestBed.configureTestingModule({
      imports: [EmpruntManagerComponent, ReactiveFormsModule],
      providers: [{ provide: EmpruntService, useValue: mockEmpruntService }],
    }).compileComponents();

    fixture = TestBed.createComponent(EmpruntManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('devrait soumettre le formulaire et appeler le service avec les bonnes valeurs', () => {
    mockEmpruntService.enregistrerEmprunt.and.returnValue(of({}));

    // Remplissage du formulaire de test
    component.empruntForm.setValue({ idAdherent: 101, idExemplaire: 5 });
    component.soumettreEmprunt();

    expect(mockEmpruntService.enregistrerEmprunt).toHaveBeenCalledWith(101, 5);
  });
});
