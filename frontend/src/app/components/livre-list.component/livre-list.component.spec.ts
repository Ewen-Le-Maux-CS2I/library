import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';

import { LivreListComponent } from './livre-list.component';
import { LivreService } from '../../services/livre.service';
import { Livre } from '../../models/livre.model';

const buildFakeLivre = (overrides: Partial<Livre> = {}): Livre => ({
  id: 1,
  titre: 'Livre factice',
  auteur: 'Auteur de test',
  isbn: '9780000000000',
  ...overrides,
});

describe('LivreListComponent', () => {
  let component: LivreListComponent;
  let fixture: ComponentFixture<LivreListComponent>;

  const mockLivreService = {
    getLivresAppele: false,
    getLivres() {
      this.getLivresAppele = true;

      return of([
        buildFakeLivre({ id: 1, titre: 'Clean Code', auteur: 'Robert C. Martin', isbn: '9780132350884' }),
        buildFakeLivre({ id: 2, titre: 'Refactoring', auteur: 'Martin Fowler', isbn: '9780134757599' }),
      ]);
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LivreListComponent],
      providers: [{ provide: LivreService, useValue: mockLivreService }],
    }).compileComponents();

    fixture = TestBed.createComponent(LivreListComponent);
    component = fixture.componentInstance;
  });

  it('devrait charger une liste de livres factices au demarrage', () => {
    fixture.detectChanges();

    expect(mockLivreService.getLivresAppele).toBe(true);
    expect(component.livres().length).toBe(2);

    const titres = fixture.debugElement
      .queryAll(By.css('h3'))
      .map((element) => element.nativeElement.textContent.trim());

    expect(titres).toEqual(['Clean Code', 'Refactoring']);
  });
});
