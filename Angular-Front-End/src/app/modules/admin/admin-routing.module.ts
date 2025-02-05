import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from "../../core/auth.guard";
import {VolsComponent} from "./vols/vols.component";
import {ClientsComponent} from "./clients/clients.component";
import {BookingsComponent} from "./bookings/bookings.component";
import {SupportAdminComponent} from "./support-admin/support-admin.component"
import {SupportAdminChatComponent} from "./support-admin-chat/support-admin-chat.component";



const routes: Routes = [
  { path: 'vols', component: VolsComponent },
  { path: 'clients', component: ClientsComponent},
  { path: 'bookings', component: BookingsComponent},
  { path: 'admin-tickets', component: SupportAdminComponent },
  { path: 'admin-tickets/:ticketId/messages', component: SupportAdminChatComponent }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
