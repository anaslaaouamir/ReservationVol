import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VolsComponent } from './vols/vols.component';
import { ClientsComponent } from './clients/clients.component';
import { BookingsComponent } from './bookings/bookings.component';
import {AdminRoutingModule} from "./admin-routing.module";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ToastModule} from "primeng/toast";
import {TableModule} from "primeng/table";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";
import {ToolbarModule} from "primeng/toolbar";
import {ConfirmationService, MessageService} from "primeng/api";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CalendarModule} from "primeng/calendar";



@NgModule({
  declarations: [
    VolsComponent,
    ClientsComponent,
    BookingsComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    ToastModule,
    ToolbarModule,
    ConfirmDialogModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    CalendarModule
  ],
  providers: [MessageService,ConfirmationService]
})
export class AdminModule { }
