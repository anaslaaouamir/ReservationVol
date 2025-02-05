import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TicketListComponent } from './ticket-list/ticket-list.component';
import {AddTicketComponent} from "./add-ticket/add-ticket.component";
import {TicketMessagesComponent} from "./ticket-messages/ticket-messages.component";

const routes: Routes = [
  { path: '', component: TicketListComponent },
  { path: 'client-tickets', component: TicketListComponent },
  { path: 'client-tickets/:ticketId/messages', component: TicketMessagesComponent },
  { path: 'client-tickets/add-ticket', component: AddTicketComponent }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupportRoutingModule {}
