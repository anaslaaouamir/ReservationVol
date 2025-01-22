export interface Vol {
  idVol: number;
  nameVol: string;
  companyName: string;
  villeDepart: string;
  villeDestination: string;
  heureDepart: string; // ou Date selon votre besoin
  heureArrivee: string; // ou Date selon votre besoin
  prix: number;
  placesDisponibles: number;
  statut: string;
}

// Vous pouvez aussi ajouter des interfaces liées si nécessaire
export interface SearchVolCriteria {
  villeDepart: string;
  villeDestination: string;
  day: number;
  month: number;
  year: number;
}
