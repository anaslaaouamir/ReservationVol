import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from "../../core/auth.guard";
import {VolsComponent} from "./vols/vols.component";
import {ClientsComponent} from "./clients/clients.component";
import {BookingsComponent} from "./bookings/bookings.component";
import {roleGuard} from "../../core/role.guard";


const routes: Routes = [
  { path: 'vols', component: VolsComponent , canActivate: [roleGuard], data:{roles: ["ADMIN"]}},
  { path: 'clients', component: ClientsComponent , canActivate: [roleGuard], data:{roles: ["ADMIN"]}},
  { path: 'bookings', component: BookingsComponent, canActivate: [roleGuard], data:{roles: ["ADMIN"]} },
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
