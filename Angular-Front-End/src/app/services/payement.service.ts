import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Paiement} from "../models/payement.model";



@Injectable({
  providedIn: 'root'
})
export class PaiementService {
  private apiUrl = 'http://localhost:9093';

  constructor(private http: HttpClient) {}

  getPaiements(): Observable<Paiement[]> {
    return this.http.get<Paiement[]>(`${this.apiUrl}/paiements`);
  }

  getPaiementById(id: number): Observable<Paiement> {
    return this.http.get<Paiement>(`${this.apiUrl}/paiements/${id}`);
  }

  getPaiementByReservation(id: number): Observable<Paiement> {
    return this.http.get<Paiement>(`${this.apiUrl}/reservation_paiement/${id}`);
  }

  createPaiement(idReservation: number, paiement: Paiement): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/paiements/${idReservation}`, paiement);
  }
}
