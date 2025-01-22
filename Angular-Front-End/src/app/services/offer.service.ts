import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vol, SearchVolCriteria } from 'src/app/models/vol.model';

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  private apiUrl = 'http://localhost:9092'; // URL de votre backend

  constructor(private http: HttpClient) {}

  chercherVol(villeDepart: string, villeDestination: string, day: number, month: number, year: number): Observable<any[]> {
    const url = `${this.apiUrl}/ClientChercherVol?villeDepart=${villeDepart}&villeDestination=${villeDestination}&day=${day}&month=${month}&year=${year}`;
    return this.http.get<any[]>(url);
  }

  getVolById(id: number): Observable<Vol> {
    return this.http.get<Vol>(`${this.apiUrl}/vols/${id}`);
  }
}
