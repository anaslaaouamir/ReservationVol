import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private baseUrl = 'http://localhost:9091'; // URL de base pour le backend

  constructor(private http: HttpClient) {}

  // Récupérer tous les clients
  getClients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/clients`);
  }

  // Récupérer un client par ID
  getClientById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/clients/${id}`);
  }

  // Créer un nouveau client
  createClient(client: any): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/clients`, client);
  }

  // Mettre à jour un client
  updateClient(id: number, client: any): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/clients/${id}`, client);
  }

  // Supprimer un client
  deleteClient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/clients/${id}`);
  }

  // Rechercher des clients
  searchClients(recherche: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/chercherClients?recherche=${recherche}`);
  }
}
