import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { OuvrageService } from './ouvrage.service';
import { Ouvrage } from '../models/ouvrage.model';

describe('OuvrageService', () => {
  let service: OuvrageService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OuvrageService],
    });
    service = TestBed.inject(OuvrageService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('devrait recuperer tous les ouvrages (Livres et Revues) via un GET', () => {
    const mockOuvrages: Ouvrage[] = [
      { id: 1, titre: 'Les Misérables', auteur: 'Victor Hugo', type: 'LIVRE', isbn: '9782070409242' },
      { id: 2, titre: 'Science & Vie N°1200', auteur: 'Rédaction', type: 'REVUE', numero: 1200 },
    ];

    service.getOuvrages().subscribe((ouvrages) => {
      expect(ouvrages.length).toBe(2);
      expect(ouvrages).toEqual(mockOuvrages);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/ouvrages');
    expect(req.request.method).toBe('GET');
    req.flush(mockOuvrages);
  });
});
