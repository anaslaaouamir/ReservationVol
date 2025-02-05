import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BookingService } from "../../../services/booking.service";
import { Reservation } from 'src/app/models/reservation.model';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css'],
  providers: [MessageService, ConfirmationService]
})

export class BookingListComponent {


}
