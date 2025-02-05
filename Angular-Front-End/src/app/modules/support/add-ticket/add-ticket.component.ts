import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";

@Component({
  selector: 'app-add-ticket',
  templateUrl: './add-ticket.component.html',
  styleUrls: ['./add-ticket.component.css']
})
export class AddTicketComponent {
  ticketForm: FormGroup;
  errorMessage: string = '';
  clientId: number = 0;

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    // Récupérer l'idClient à partir du localStorage
    this.clientId = this.getStoredClientId();

    this.ticketForm = this.fb.group({
      sujet: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  // Fonction pour récupérer idClient depuis le localStorage
  getStoredClientId(): number {
    const clientData = localStorage.getItem('currentClient');
    return clientData ? JSON.parse(clientData).idClient : 0; // Retourne l'idClient ou 0 si non trouvé
  }

  onSubmit(): void {
    if (this.ticketForm.valid) {
      const newTicket = {
        sujet: this.ticketForm.value.sujet,
        description: this.ticketForm.value.description,
        statut: 'opened',
        dateCreation: new Date().toISOString(),
        dateMiseAJour: new Date().toISOString(),
        idClient: this.clientId // Utiliser l'idClient récupéré
      };

      this.http.post('http://localhost:9094/stickets', newTicket).subscribe({
        next: () => {
          console.log('Ticket created successfully');
          // Optionnellement, réinitialiser le formulaire ou naviguer vers une autre page
          this.ticketForm.reset();
          this.router.navigate([`/support/client-tickets`]);
        },
        error: (err) => {
          this.errorMessage = 'Une erreur est survenue lors de la création du ticket.';
          console.error(err);
        }
      });
    }
  }
}
