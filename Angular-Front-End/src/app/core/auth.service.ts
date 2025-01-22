import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, Observable, tap, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:9091/auth'; // URL de votre backend

  constructor(private http: HttpClient) {}

  // Enregistrement d'un nouvel utilisateur
  register(nom: string, email: string, motPasse: string, telephone: string): Observable<any> {
    const body = { nom, email, motPasse, telephone };
    return this.http.post(`${this.apiUrl}/register`, body);
  }


  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(`${this.apiUrl}/login`, body, {
      headers,
      responseType: 'text' // Add this line to handle text response
    }).pipe(
      tap((response: any) => {
        console.log('Raw response:', response); // Debug log
        try {
          // Try to parse the response if it's a JSON string
          const tokenData = typeof response === 'string' ? JSON.parse(response) : response;
          if (tokenData.token) {
            localStorage.setItem('token', tokenData.token);
          } else if (typeof tokenData === 'string') {
            // If the response is a plain token string
            localStorage.setItem('token', tokenData);
          }
        } catch (e) {
          // If it's a plain token string
          localStorage.setItem('token', response);
        }
      }),
      catchError(this.handleError)
    );
  }

  getUserProfile(): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });

    return this.http.get(`${this.apiUrl}/me`, { headers }).pipe(
      tap((user) => {
        localStorage.setItem('user', JSON.stringify(user)); // Stocke le profil localement
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    console.error('Full error object:', error); // Debug log
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = error.error.message;
    } else if (error.error && typeof error.error === 'string') {
      try {
        // Try to parse error message if it's JSON
        const parsedError = JSON.parse(error.error);
        errorMessage = parsedError.message || parsedError.error || error.error;
      } catch (e) {
        // If it's not JSON, use the error string directly
        errorMessage = error.error;
      }
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
}
