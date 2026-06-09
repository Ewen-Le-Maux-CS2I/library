import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmpruntService } from './emprunt.service';
import { Emprunt } from '../models/emprunt.model';

describe('EmpruntService', () => {
  let service: EmpruntService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EmpruntService]
    });
    service = TestBed.inject(EmpruntService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('devrait envoyer une requête POST pour enregistrer un emprunt', () => {
    const mockEmprunt: Emprunt = {
      idAdherent: 42,
      exemplaire: { id: 1, codeBarre: 'EX-123', disponible: true, ouvrage: { titre: 'Test', auteur: 'Auteur', type: 'LIVRE' } },
      dateEmprunt: '2026-06-09',
      dateRetourPrevue: '2026-06-23'
    };

    service.enregistrerEmprunt(42, 1).subscribe(res => {
      expect(res).toEqual(mockEmprunt);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/emprunts');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ idAdherent: 42, idExemplaire: 1 });
    req.flush(mockEmprunt);
  });
});