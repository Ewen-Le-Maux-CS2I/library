import { Ouvrage } from './ouvrage.model';

export interface Exemplaire {
  id?: number;
  codeBarre: string;
  disponible: boolean;
  ouvrage: Ouvrage;
}
