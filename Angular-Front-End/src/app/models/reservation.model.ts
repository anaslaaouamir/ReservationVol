export interface Reservation {
  idReservation?: number;
  numeroPlace?: number;
  dateReservation: Date;
  statut?: string;
  idClient: number;
  idVol: number;
  client?: {
    id: number;
    nom: string;
    email: string;
    telephone:number;
  };
  vol?: {
    id: number;
    nameVol: string;
    lieuDepart: string;
    lieuArrivee: string;
    heureDepart: Date;
    heureArrivee: Date;
    prix: number;
    placesDisponibles: number;
    statut: string;
    companyName: string;
  };
  paiement?: {
    id: number;
    montant: number;
    methodePaiement: string;
    datePaiement: Date;
    statut: string;
  };
}
