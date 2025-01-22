// reservation.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Reservation } from 'src/app/models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private mockData: Reservation[] = [
    {
      idReservation: 1,
      numeroPlace: 12,
      dateReservation: new Date(),
      statut: 'Confirmé',
      idClient: 1,
      idVol: 1,
      client: {
        id: 1,
        nom: 'Dupont Jean',
        email: 'jean@email.com'
      },
      vol: {
        id: 1,
        lieuDepart: 'Paris',
        lieuArrivee: 'Lyon',
        dateDepart: new Date()
      },
      paiement: {
        id: 1,
        montant: 150
      }
    },
    {
      idReservation: 1,
      numeroPlace: 12,
      dateReservation: new Date(),
      statut: 'Confirmé',
      idClient: 1,
      idVol: 1,
      client: {
        id: 1,
        nom: 'Dupont Jean',
        email: 'jean@email.com'
      },
      vol: {
        id: 1,
        lieuDepart: 'Paris',
        lieuArrivee: 'Lyon',
        dateDepart: new Date()
      },
      paiement: {
        id: 1,
        montant: 150
      }
    },
    {
      idReservation: 1,
      numeroPlace: 12,
      dateReservation: new Date(),
      statut: 'Confirmé',
      idClient: 1,
      idVol: 1,
      client: {
        id: 1,
        nom: 'Dupont Jean',
        email: 'jean@email.com'
      },
      vol: {
        id: 1,
        lieuDepart: 'Paris',
        lieuArrivee: 'Lyon',
        dateDepart: new Date()
      },
      paiement: {
        id: 1,
        montant: 150
      }
    },
    // Ajoutez plus de données mock ici
  ];

  getReservations(): Observable<Reservation[]> {
    return of(this.mockData);
  }

  updateReservation(reservation: Reservation): Observable<Reservation> {
    const index = this.mockData.findIndex(r => r.idReservation === reservation.idReservation);
    if (index !== -1) {
      this.mockData[index] = { ...reservation };
    }
    return of(reservation);
  }

  deleteReservation(id: number): Observable<void> {
    this.mockData = this.mockData.filter(r => r.idReservation !== id);
    return of(void 0);
  }
}
