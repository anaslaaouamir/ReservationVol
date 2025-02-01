import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import {Client} from "../models/client.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:9091/api/auth';
  private currentClientSubject = new BehaviorSubject<Client | null>(null);

  constructor(private http: HttpClient) {
    const storedClient = localStorage.getItem('currentClient');
    if (storedClient) {
      this.currentClientSubject.next(JSON.parse(storedClient));
    }
  }

  register(client: Client): Observable<Client> {
    return this.http.post<Client>(`${this.API_URL}/register`, client);
  }

  login(email: string, motPasse: string): Observable<string> {
    return this.http.post<string>(`${this.API_URL}/login`, { email, motPasse })
      .pipe(
        tap(token => {
          localStorage.setItem('token', token);
          // Stocker les informations du client
          const clientInfo: Client = {
            email: email,
            nom: '', // À remplir avec les données du backend si disponibles
            motPasse: '',
            telephone: ''
          };
          localStorage.setItem('currentClient', JSON.stringify(clientInfo));
          this.currentClientSubject.next(clientInfo);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('currentClient');
    this.currentClientSubject.next(null);
  }

  getCurrentClient(): Observable<Client | null> {
    return this.currentClientSubject.asObservable();
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
}
