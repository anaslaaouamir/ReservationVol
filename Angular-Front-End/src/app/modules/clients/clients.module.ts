import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import {ClientsRoutingModule} from "./clients-routing.module";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { authGuard} from "../../core/auth.guard";
import { ListComponent } from './list/list.component';
import {MessageService} from "primeng/api";

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    ListComponent
  ],
  imports: [
    CommonModule,
    ClientsRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    MessageService // Ajoutez ceci ici
  ],
})
export class ClientsModule { }
