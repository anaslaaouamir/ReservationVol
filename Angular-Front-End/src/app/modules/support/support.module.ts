import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketListComponent } from './ticket-list/ticket-list.component';
import {SupportRoutingModule} from "./support-routing.module";
import { AddTicketComponent } from './add-ticket/add-ticket.component';
import { TicketMessagesComponent } from './ticket-messages/ticket-messages.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    TicketListComponent,
    AddTicketComponent,
    TicketMessagesComponent
  ],
  imports: [
    CommonModule,
    SupportRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SupportModule { }
