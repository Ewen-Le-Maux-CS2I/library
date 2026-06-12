import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LivreService } from './livre.service';

describe('LivreService', () => {
  let service: LivreService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LivreService],
    });
    service = TestBed.inject(LivreService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('devrait récupérer la liste des livres depuis le backend (GET)', () => {
    const dummyLivres = [
      { id: 1, titre: 'Le Nom de la Rose', auteur: 'Umberto Eco', isbn: '12345' },
      { id: 2, titre: 'Dune', auteur: 'Frank Herbert', isbn: '67890' },
    ];

    service.getLivres().subscribe((livres) => {
      expect(livres.length).toBe(2);
      expect(livres).toEqual(dummyLivres);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/livres');
    expect(req.request.method).toBe('GET');
    req.flush(dummyLivres); // Simule la réponse du backend
  });
});
