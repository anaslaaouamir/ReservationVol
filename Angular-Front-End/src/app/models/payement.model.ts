export interface Paiement {
  idPaiement?: number;
  montant: number;
  methodePaiement: string;
  numCarte: string;
  nomPorteur: string;
  datePaiement: string; // Stocké sous forme de string ISO
  statut?: string;
  reservationId?: number; // ID de la réservation associée
}
