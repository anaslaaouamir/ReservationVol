import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Client {
  nom: string;
  email: string;
  motPasse: string;
  telephone: string;
}

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
  };
  paiement:{
    methodePaiement:string | null;
  }
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  client: Client = { nom: '', email: '', motPasse: '', telephone: '' };
  clientId: number = 3;
  loading = true;
  errorMessage = '';
  reservations: Reservation[] = [];
  showReservations = false;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.clientId = this.getStoredClientId();
    if (this.clientId) {
      this.loadClient();
    } else {
      this.errorMessage = "Client non trouvé. Veuillez vous reconnecter.";
    }
  }

  getStoredClientId(): number {
    const clientData = localStorage.getItem('currentClient');
    return clientData ? JSON.parse(clientData).idClient : 0;
  }



  getClientById(id: number): Observable<any> {
    return this.http.get<any>(`http://localhost:9091/clients/${id}`);
  }

  loadClient(): void {
    this.loading = true;
    this.errorMessage = '';

    this.getClientById(this.clientId).subscribe({
      next: (client) => {
        this.client = client;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors du chargement des informations du client.';
        console.error(err);
        this.loading = false;
      },
    });
  }

  updateClient(): void {
    this.http.put(`http://localhost:9091/clients/${this.clientId}`, this.client).subscribe({
      next: () => {
        window.location.reload();
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors de la mise à jour des informations.';
        console.error(err);
      }
    });
  }

  clientReservations(clientId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:9093/client_reservations/${clientId}`);
  }

  loadReservations(): void {
    this.clientReservations(this.clientId).subscribe({
      next: (reservations) => {
        this.reservations = reservations;
        this.showReservations = true;
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors du chargement des réservations.';
        console.error(err);
      }
    });
  }
}
