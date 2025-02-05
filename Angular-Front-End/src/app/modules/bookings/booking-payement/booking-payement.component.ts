import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService } from '../../../services/booking.service';
import { Reservation } from '../../../models/reservation.model';
import {Paiement} from "../../../models/payement.model";
import {PaiementService} from "../../../services/payement.service";


@Component({
  selector: 'app-booking-payement',
  templateUrl: './booking-payement.component.html',
  styleUrls: ['./booking-payement.component.css']
})
export class BookingPayementComponent implements OnInit {
  reservation: Reservation | null = null;
  paiement: Paiement = {
    montant: 0,
    methodePaiement: '',
    numCarte: '',
    nomPorteur: '',
    datePaiement: new Date().toISOString()
  };

  constructor(
    private route: ActivatedRoute,
    private bookingService: BookingService,
    private paymentService: PaiementService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const idReservation = params['idReservation'];
      if (idReservation) {
        this.bookingService.getReservation(idReservation).subscribe(reservation => {
          this.reservation = reservation;
          this.paiement.montant = reservation.vol?.prix || 0; // Vérification de vol
        });
      }
    });
  }

  effectuerPaiement() {
    if (this.reservation?.idReservation !== undefined) {
      this.paymentService.createPaiement(this.reservation.idReservation, this.paiement)
        .subscribe(() => {
          this.router.navigate(['/clients/billet'], {
            queryParams: {idReservation: this.reservation?.idReservation}
          });
        });
    } else {
      alert('Réservation non valide');
    }
  }
}
