import { Routes } from '@angular/router';
import { OuvrageListComponent } from './components/ouvrage-list.component/ouvrage-list.component';
import { EmpruntManagerComponent } from './components/emprunt-manager.component/emprunt-manager.component';

export const routes: Routes = [
  { path: 'ouvrages', component: OuvrageListComponent },
  { path: 'emprunts', component: EmpruntManagerComponent },
  { path: '', redirectTo: '/ouvrages', pathMatch: 'full' },
];
