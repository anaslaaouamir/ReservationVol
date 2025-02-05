import { Component, OnInit } from '@angular/core';
import { MessageService, ConfirmationService } from 'primeng/api';
import {Reservation} from "../../../models/reservation.model";
import {BookingService} from "../../../services/booking.service";

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css'],
  providers: [MessageService, ConfirmationService]

})
export class BookingsComponent implements OnInit {
  reservations: Reservation[] = [];
  loading: boolean = true;

  // Modals visibility
  displayVolDetails: boolean = false;
  displayClientDetails: boolean = false;
  displayPaiementDetails: boolean = false;

  // Selected entities for details
  selectedVol: any = {};
  selectedClient: any = {};
  selectedPaiement: any = {};
  selectedReservation: Reservation | null = null;
  displayEditDialog: boolean = false;

  // Filters
  filters: any = {};

  // Dialog configs
  dialogStyle = { width: '50vw' };

  constructor(
    private bookingService: BookingService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.loadReservations();
  }

  loadReservations() {
    this.loading = true;
    this.bookingService.getAllReservations().subscribe({
      next: (data) => {
        this.reservations = data;
        this.loading = false;
      },
      error: (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Impossible de charger les réservations'
        });
        this.loading = false;
      }
    });
  }

  showVolDetails(reservation: Reservation) {
    this.selectedVol = reservation.vol;
    this.displayVolDetails = true;
  }

  showClientDetails(reservation: Reservation) {
    this.selectedClient = reservation.client;
    this.displayClientDetails = true;
  }

  showPaiementDetails(reservation: Reservation) {
    this.selectedPaiement = reservation.paiement;
    this.displayPaiementDetails = true;
  }
  editReservation(reservation: Reservation) {
    this.selectedReservation = { ...reservation }; // Cloner l'objet pour éviter la modification directe
    this.displayEditDialog = true;
  }

  saveReservation() {
    if (this.selectedReservation) {
      this.bookingService.updateReservation(
        this.selectedReservation.idReservation!,
        this.selectedReservation
      ).subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: `Réservation #${this.selectedReservation!.idReservation} mise à jour`
          });
          this.loadReservations(); // Recharger les données après modification
          this.displayEditDialog = false;
        },
        error: () => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: `Impossible de mettre à jour la réservation`
          });
        }
      });
    }
  }


  deleteReservation(reservation: Reservation) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer la réservation #${reservation.idReservation} ?`,
      header: 'Confirmation de suppression',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.bookingService.deleteReservation(reservation.idReservation!).subscribe({
          next: () => {
            this.reservations = this.reservations.filter(r => r.idReservation !== reservation.idReservation);
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Réservation supprimée'
            });
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Erreur',
              detail: 'Impossible de supprimer la réservation'
            });
          }
        });
      }
    });
  }

  applyFilter() {
    this.loading = true;
    this.bookingService.getAllReservations().subscribe({
      next: (data) => {
        this.reservations = data.filter(reservation => {
          return Object.keys(this.filters).every(key => {
            const typedKey = key as keyof Reservation; // Cast explicite
            return !this.filters[key] ||
              String(reservation[typedKey] ?? '').toLowerCase().includes(String(this.filters[key]).toLowerCase());
          });
        });
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur de filtrage'
        });
      }
    });
  }


  resetFilters() {
    this.filters = {};
    this.loadReservations();
  }
}
