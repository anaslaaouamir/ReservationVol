import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {HttpClientModule} from "@angular/common/http";
import {NavbarComponent} from "./layout/navbar/navbar.component";
import { OffersModule } from './modules/offers/offers.module';
import { BookingsModule } from './modules/bookings/bookings.module';
import { ClientsModule } from './modules/clients/clients.module';
import { SupportModule } from './modules/support/support.module';
import {AdminModule} from "./modules/admin/admin.module";



function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'Vol',
        clientId: 'client'
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    KeycloakAngularModule,
    OffersModule,
    BookingsModule,
    ClientsModule,
    SupportModule,
    AdminModule
  ],
  providers: [{provide : APP_INITIALIZER, useFactory : initializeKeycloak, multi :true, deps : [KeycloakService]}],
  bootstrap: [AppComponent]
})
export class AppModule { }
