import { TestBed } from '@angular/core/testing';
import { App } from './app'; // On garde votre import d'origine

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
    }).compileComponents();
  });

  // On garde UNIQUEMENT ce test qui valide que l'application démarre
  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
