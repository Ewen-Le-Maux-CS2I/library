import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OuvrageListComponent } from './ouvrage-list.component';
import { OuvrageService } from '../../services/ouvrage.service';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('OuvrageListComponent', () => {
  let component: OuvrageListComponent;
  let fixture: ComponentFixture<OuvrageListComponent>;

  // Mock manuel conforme à la Solution 2
  const mockOuvrageService = {
    getOvragesAppele: false,
    getOuvrages() {
      this.getOvragesAppele = true;
      return of([
        {
          id: 1,
          titre: 'Design Patterns',
          auteur: 'Gang of Four',
          type: 'LIVRE',
          isbn: '978-0201633610',
        },
        {
          id: 2,
          titre: 'National Geographic N°50',
          auteur: 'National Geo',
          type: 'REVUE',
          numero: 50,
        },
      ]);
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OuvrageListComponent],
      providers: [{ provide: OuvrageService, useValue: mockOuvrageService }],
    }).compileComponents();

    fixture = TestBed.createComponent(OuvrageListComponent);
    component = fixture.componentInstance;
  });

  it('devrait charger la liste mixte (Livres et Revues) au demarrage', () => {
    fixture.detectChanges(); // Déclenche ngOnInit

    expect(mockOuvrageService.getOvragesAppele).toBe(true);
    expect(component.ouvrages.length).toBe(2);

    const spans = fixture.debugElement.queryAll(By.css('span'));
    const texteBadges = spans.map((span) => span.nativeElement.textContent.trim());

    expect(texteBadges).toContain('LIVRE');
    expect(texteBadges).toContain('REVUE');
  });
});
