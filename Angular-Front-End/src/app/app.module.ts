import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations"; // Supprimez cette ligne

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NavbarComponent} from "./layout/navbar/navbar.component";
import {AuthInterceptor} from "./core/auth.interceptor";



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
    BrowserAnimationsModule
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },{provide : APP_INITIALIZER, useFactory : initializeKeycloak, multi :true, deps : [KeycloakService]}],
  bootstrap: [AppComponent]
})
export class AppModule { }
