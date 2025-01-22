import { Component } from '@angular/core';
import {AuthService} from "../../../core/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  nom: string = '';
  email: string = '';
  motPasse: string = '';
  telephone: string = '';

  constructor(private authService: AuthService, private router: Router) {} // Injectez Router

  onSubmit() {
    this.authService
      .register(this.nom, this.email, this.motPasse, this.telephone)
      .subscribe(
        (response) => {
          console.log('Enregistrement réussi', response);
          this.router.navigate(['/clients/login']); // Redirigez vers la page de connexion
        },
        (error) => {
          console.error('Erreur lors de l\'enregistrement', error);
        }
      );
  }
}
