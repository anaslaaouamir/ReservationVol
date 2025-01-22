import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./modules/clients/login/login.component";

const routes: Routes = [
  { path: '', redirectTo: 'offers/search', pathMatch: 'full' },
  { path: 'offers', loadChildren: () => import('./modules/offers/offers.module').then(m => m.OffersModule) },
  { path: 'bookings', loadChildren: () => import('./modules/bookings/bookings.module').then(m => m.BookingsModule) },  // Lazy loading
  { path: 'clients', loadChildren: () => import('./modules/clients/clients.module').then(m => m.ClientsModule) },
  { path: 'support', loadChildren: () => import('./modules/support/support.module').then(m => m.SupportModule) },
  { path: 'admin-space', loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
