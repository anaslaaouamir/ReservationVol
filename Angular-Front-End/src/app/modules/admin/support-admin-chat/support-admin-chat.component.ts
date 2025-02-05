import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {SupportService} from "../../../services/support.service";
import {HttpClient} from "@angular/common/http";

interface Message {
  idMessage: number;
  sender: string;
  content: string;
  timestamp: string | null;
}


@Component({
  selector: 'app-support-admin-chat',
  templateUrl: './support-admin-chat.component.html',
  styleUrls: ['./support-admin-chat.component.css']
})
export class SupportAdminChatComponent implements OnInit{

  messages: Message[] = [];
  ticketId!: number;
  loading = true;
  errorMessage = '';
  newMessageContent = '';

  constructor(
    private route: ActivatedRoute,
    private supportService: SupportService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ticketId = +params['ticketId'];
      this.loadMessages();
    });
  }

  loadMessages(): void {
    this.loading = true;
    this.errorMessage = '';

    this.supportService.getMessagesByTicketId(this.ticketId).subscribe({
      next: (messages) => {
        this.messages = messages;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors du chargement des messages.';
        console.error(err);
        this.loading = false;
      },
    });
  }

  sendMessage(): void {
    console.log('')
    const newMessage = {
      sender: 'Admin', // Assuming the sender is always the client in this context
      content: this.newMessageContent,
      timestamp: new Date().toISOString()
    };

    console.log('celint', newMessage);

    this.http.post(`http://localhost:9094/ticket_message/${this.ticketId}`, newMessage).subscribe({
      next: () => {
        // @ts-ignore
        this.messages.push(newMessage);
        this.newMessageContent = '';
      },
      error: (err) => {
        this.errorMessage = 'Une erreur est survenue lors de l\'envoi du message.';
        console.error(err);
      }
    });
  }

}
