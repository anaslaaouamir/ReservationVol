import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SupportService } from '../../../services/support.service';
import {HttpClient} from "@angular/common/http";

interface Ticket {
  idTicket: number;
  sujet: string;
  description: string;
  statut: string;
  dateCreation: string | null;
  idClient: number;
  clientName?: string; // Optional field for client name
}

@Component({
  selector: 'app-support-admin',
  templateUrl: './support-admin.component.html',
  styleUrls: ['./support-admin.component.css']
})
export class SupportAdminComponent {

  tickets: Ticket[] = [];
  clientId = 1; // Manually set the client ID
  loading = true;
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private supportService: SupportService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  loadTickets(): void {
    this.loading = true;
    this.errorMessage = '';

    this.supportService.getAllTickets().subscribe({
      next: (tickets) => {
        this.tickets = tickets;
        this.tickets.forEach(ticket => {
          this.supportService.getClient(ticket.idClient).subscribe({
            next: (client) => {
              ticket.clientName = client.nom;
            },
            error: (err) => {
              console.error(`Failed to load client details for ticket ${ticket.idTicket}`, err);
            }
          });
        });
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
    this.router.navigate([`/admin-space/admin-tickets/${ticket.idTicket}/messages`]);
  }

  doneTicket(ticket: Ticket): void {

    // @ts-ignore
    this.http.put(`http://localhost:9094/stickets/${ticket.idTicket}`).subscribe({
      next: () => {
        console.log('Ticket updated successfully');
        window.location.reload();
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors de modification du ticket.';
        console.error(err);
      }
    });
  }

}
