import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, map, Observable, tap} from 'rxjs';
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

  login(email: string, motPasse: string): Observable<void> {
    return this.http.post<{ token: string; user: Client }>(`${this.API_URL}/login`, { email, motPasse })
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
          localStorage.setItem('currentClient', JSON.stringify(response.user));
          this.currentClientSubject.next(response.user);
        }),
        map(() => void 0)
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

  getRoles(): string[] {
    const clientData = localStorage.getItem('currentClient');
    if (clientData) {
      const client = JSON.parse(clientData);
      return client.roles || [];
    }
    return [];
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

}
