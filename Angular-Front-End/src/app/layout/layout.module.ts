import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {KeycloakAngularModule} from "keycloak-angular";
import {HttpClientModule} from "@angular/common/http";



@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    HttpClientModule, KeycloakAngularModule
  ]
})
export class LayoutModule { }
