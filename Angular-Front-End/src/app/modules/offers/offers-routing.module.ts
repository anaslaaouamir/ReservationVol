import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OfferListComponent } from './offer-list/offer-list.component';
import { OfferDetailComponent } from './offer-detail/offer-detail.component';
import { OfferSearchComponent } from './offer-search/offer-search.component';

const routes: Routes = [
  { path: '', component: OfferSearchComponent },
  { path: 'detail/:id', component: OfferDetailComponent },
  { path: 'search', component: OfferSearchComponent },
  { path: 'list-vol', component: OfferListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OffersRoutingModule {}
