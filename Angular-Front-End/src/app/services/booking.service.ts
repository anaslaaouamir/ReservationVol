import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Reservation } from 'src/app/models/reservation.model';
import { HttpClient } from '@angular/common/http';
import { Vol } from '../models/vol.model';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:9093';

  constructor(private http: HttpClient) { }

  getReservation(id: number): Observable<Reservation> {
    return this.http.get<Reservation>(`${this.apiUrl}/reservations/${id}`);
  }

  getAllReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/reservations`);
  }

  addReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(`${this.apiUrl}/reservations`, reservation);
  }

  deleteReservation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/reservations/${id}`);
  }

  getClientReservations(id: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/client_reservations/${id}`);
  }

  getVolReservations(id: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/vol_reservations/${id}`);
  }

  updateReservation(id: number, reservation: Reservation): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/reservations/${id}`, reservation);
  }

  deleteVolReservations(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/vol_reservations/${id}`);
  }

  deleteClientReservations(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/client_reservations/${id}`);
  }

  getClientVolReserves(
    idClient: number,
    villeDepart?: string,
    villeDestination?: string,
    day?: number,
    month?: number,
    year?: number
  ): Observable<Vol[]> {
    let params = new URLSearchParams();
    if (villeDepart) params.append('villeDepart', villeDepart);
    if (villeDestination) params.append('villeDestination', villeDestination);
    if (day) params.append('day', day.toString());
    if (month) params.append('month', month.toString());
    if (year) params.append('year', year.toString());

    return this.http.get<Vol[]>(`${this.apiUrl}/clientVolReserves/${idClient}?${params.toString()}`);
  }
}

