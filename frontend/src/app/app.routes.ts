import { Routes } from '@angular/router';
import { LivreListComponent } from './components/livre-list.component/livre-list.component';
import { EmpruntManagerComponent } from './components/emprunt-manager.component/emprunt-manager.component';

export const routes: Routes = [
  { path: 'livres', component: LivreListComponent },
  { path: 'emprunts', component: EmpruntManagerComponent },
  { path: '', redirectTo: '/livres', pathMatch: 'full' },
];
