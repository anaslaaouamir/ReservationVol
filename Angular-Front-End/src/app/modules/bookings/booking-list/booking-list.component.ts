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

export class BookingListComponent implements OnInit {

  reservations: Reservation[] = [];
  expandedRows: { [key: string]: boolean } = {};
  editingReservation: Reservation | null = null;

  constructor(
    private reservationService: BookingService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.loadReservations();
  }

  loadReservations() {
    this.reservationService.getReservations().subscribe(data => {
      this.reservations = data;
    });
  }

  editReservation(reservation: Reservation) {
    this.editingReservation = { ...reservation };
    // Implémenter la logique d'édition ici
  }

  confirmDelete(reservation: Reservation) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer cette réservation ?',
      accept: () => {
        this.reservationService.deleteReservation(reservation.idReservation).subscribe(() => {
          this.reservations = this.reservations.filter(r => r.idReservation !== reservation.idReservation);
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: 'Réservation supprimée avec succès'
          });
        });
      }
    });
  }

  saveReservation(reservation: Reservation) {
    this.reservationService.updateReservation(reservation).subscribe(
      updatedReservation => {
        const index = this.reservations.findIndex(r => r.idReservation === updatedReservation.idReservation);
        if (index !== -1) {
          this.reservations[index] = updatedReservation;
        }
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Réservation mise à jour avec succès'
        });
        this.editingReservation = null;
      }
    );
  }
}
