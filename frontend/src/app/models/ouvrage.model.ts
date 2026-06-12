export interface Ouvrage {
  id?: number;
  titre: string;
  auteur: string;
  type: 'LIVRE' | 'REVUE'; // Permet de gérer la factory
  isbn?: string; // Spécifique au Livre
  numero?: number; // Spécifique à la Revue
}
