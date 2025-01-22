export interface Reservation {
  idReservation: number;
  numeroPlace: number;
  dateReservation: Date;
  statut: string;
  idClient: number;
  idVol: number;
  client?: {
    id: number;
    nom: string;
    email: string;
  };
  vol?: {
    id: number;
    lieuDepart: string;
    lieuArrivee: string;
    dateDepart: Date;
  };
  paiement?: {
    id: number;
    montant: number;
  };
}
