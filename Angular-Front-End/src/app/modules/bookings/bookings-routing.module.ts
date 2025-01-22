import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookingListComponent } from './booking-list/booking-list.component';
import { BookingDetailComponent } from './booking-detail/booking-detail.component';
import { BookingFormComponent } from './booking-form/booking-form.component';
import { authGuard } from "../../core/auth.guard";


const routes: Routes = [
  { path: '', component: BookingListComponent },
  { path: 'detail/:id', component: BookingDetailComponent },
  { path: 'new', component: BookingFormComponent, canActivate: [authGuard] },
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingsRoutingModule {}
