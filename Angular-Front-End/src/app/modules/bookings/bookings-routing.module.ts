import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookingListComponent } from './booking-list/booking-list.component';
import { BookingPayementComponent} from './booking-payement/booking-payement.component';
import { BookingFormComponent } from './booking-form/booking-form.component';
import { authGuard } from "../../core/auth.guard";


const routes: Routes = [
  { path: '', component: BookingListComponent },
  { path: 'payment', component: BookingPayementComponent },
  { path: 'new', component: BookingFormComponent, canActivate: [authGuard] },
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingsRoutingModule {}
