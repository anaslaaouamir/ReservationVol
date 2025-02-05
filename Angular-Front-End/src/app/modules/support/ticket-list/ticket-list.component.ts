import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SupportService } from '../../../services/support.service';
import { HttpClient } from "@angular/common/http";

interface Ticket {
  idTicket: number;
  sujet: string;
  description: string;
  statut: string;
  dateCreation: string | null;
}

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {
  tickets: Ticket[] = [];
  clientId: number = 0; // 🔹 Déclaration de clientId
  loading = true;
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private supportService: SupportService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.clientId = this.getStoredClientId();
    if (this.clientId) {
      this.loadTickets();
    } else {
      this.errorMessage = "Client non trouvé. Veuillez vous reconnecter.";
    }
  }

  getStoredClientId(): number {
    const clientData = localStorage.getItem('currentClient');
    return clientData ? JSON.parse(clientData).idClient : 0;
  }

  loadTickets(): void {
    this.loading = true;
    this.errorMessage = '';

    this.supportService.getTicketsByClientId(this.clientId).subscribe({
      next: (tickets) => {
        console.log('Tickets reçus:', tickets);  // Vérifiez ce que vous obtenez ici
        this.tickets = tickets;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors du chargement des tickets.';
        console.error(err);
        this.loading = false;
      },
    });
  }


  viewMessages(ticket: Ticket): void {
    this.router.navigate([`/support/client-tickets/${ticket.idTicket}/messages`]);
  }

  addTicket(): void {
    this.router.navigate([`/support/client-tickets/add-ticket`]);
  }

  supprimerTicket(ticket: Ticket): void {
    this.http.delete(`http://localhost:9094/stickets/${ticket.idTicket}`).subscribe({
      next: () => {
        console.log('Ticket deleted successfully');
        window.location.reload();
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors de la suppression du ticket.';
        console.error(err);
      }
    });
  }
}
