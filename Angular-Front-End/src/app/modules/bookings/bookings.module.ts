import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingListComponent } from './booking-list/booking-list.component';
import { BookingDetailComponent } from './booking-detail/booking-detail.component';
import { BookingFormComponent } from './booking-form/booking-form.component';
import {BookingsRoutingModule} from "./bookings-routing.module";
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DropdownModule } from 'primeng/dropdown';
import {BookingService} from "../../services/booking.service";
import { DialogModule } from 'primeng/dialog';
import {ConfirmationService, MessageService} from "primeng/api";
import {authGuard} from "../../core/auth.guard";

@NgModule({
  declarations: [
    BookingListComponent,
    BookingDetailComponent,
    BookingFormComponent
  ],
  imports: [
    CommonModule,
    BookingsRoutingModule,
    FormsModule,
    TableModule,
    ButtonModule,
    RippleModule,
    ToastModule,
    ToolbarModule,
    InputTextModule,
    ConfirmDialogModule,
    DropdownModule,
    DialogModule
  ],
  providers: [BookingService,MessageService, ConfirmationService],
  bootstrap:[
    BookingListComponent
  ]
})
export class BookingsModule { }
