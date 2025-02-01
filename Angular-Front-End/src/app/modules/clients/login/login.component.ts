import { Component } from '@angular/core';
import { AuthService } from '../../../core/auth.service';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      motPasse: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, motPasse } = this.loginForm.value;
      this.authService.login(email, motPasse).subscribe({
        next: () => this.router.navigate(['/clients/profile']),
        error: err => console.error('Erreur de connexion:', err)
      });
    }
  }
}
