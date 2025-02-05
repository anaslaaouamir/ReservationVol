import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SupportService {

  constructor(private http: HttpClient) {}

  getTicketsByClientId(clientId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:9094/stickets/${clientId}`);
  }

  getMessagesByTicketId(ticketId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:9094/ticket_message/${ticketId}`);
  }

  getAllTickets():Observable<any[]>{
    return this.http.get<any[]>(`http://localhost:9094/stickets`);
  }

  getClient(clientId: number):Observable<any>{
    return this.http.get<any[]>(`http://localhost:9091/clients/${clientId}`);
  }

}
