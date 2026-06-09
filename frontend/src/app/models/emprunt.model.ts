import { Exemplaire } from './exemplaire.model';

export interface Emprunt {
  id?: number;
  dateEmprunt: string;
  dateRetourPrevue: string;
  dateRetourEffective?: string;
  idAdherent: number; // Identifiant du membre
  exemplaire: Exemplaire;
}