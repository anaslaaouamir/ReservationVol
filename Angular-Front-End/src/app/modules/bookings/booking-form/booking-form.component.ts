
import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {OfferService} from "../../../services/offer.service";
import {BookingService} from "../../../services/booking.service";

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent {
  vol: any;
  client: any;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private offerService: OfferService,
    private bookingService: BookingService
  ) {
    this.client = JSON.parse(localStorage.getItem('currentClient') || '{}');
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const idVol = params['idVol'];
      if (idVol) {
        this.offerService.getVolById(idVol).subscribe(vol => {
          this.vol = vol;
        });
      }
    });
  }

  annuler() {
    const [year, month, day] = this.vol.heureDepart.split('-').map(Number);
    this.router.navigate(['/offers/list-vol'], {
      queryParams: {
        villeDepart: this.vol.villeDepart,
        villeDestination: this.vol.villeDestination,
        day,
        month,
        year
      }
    });
  }

  passerAuPaiement() {
    const reservation = {
      dateReservation: new Date(),
      idClient: this.client.idClient,
      idVol: this.vol.idVol
    };

    this.bookingService.addReservation(reservation).subscribe(response => {
      if (response && response.idReservation) {
        this.router.navigate(['/bookings/payment'], {
          queryParams: { idReservation: response.idReservation }
        });
      }
    });
  }
}
