import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Reservation {
  idReservation: number;
  numeroPlace: number;
  dateReservation: string;
  statut: string;
  vol: {
    villeDepart: string;
    villeDestination: string;
    heureDepart: string;
    heureArrivee: string;
    prix: number;
    statut: string;
    nameVol: string;
    companyName: string;
  };
  paiement: {
    methodePaiement: string;
    montant: number;
    datePaiement: string;
  } | null;
  client: {
    nom: string;
    telephone: string;
  };
}

@Component({
  selector: 'app-billet',
  templateUrl: './billet.component.html',
  styleUrls: ['./billet.component.css']
})
export class BilletComponent implements OnInit {
  reservation: Reservation | null = null;
  errorMessage = '';

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const idReservation = params['idReservation'];
      if (idReservation) {
        this.loadReservationDetails(idReservation);
      } else {
        this.errorMessage = 'Aucune réservation trouvée.';
      }
    });
  }

  getReservationById(id: number): Observable<any> {
    return this.http.get<any>(`http://localhost:9093/reservations/${id}`);
  }

  loadReservationDetails(id: number): void {
    this.getReservationById(id).subscribe({
      next: (reservation) => {
        this.reservation = reservation;
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors du chargement des détails de la réservation.';
        console.error(err);
      }
    });
  }
}
