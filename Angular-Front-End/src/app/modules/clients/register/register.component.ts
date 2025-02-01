import { Component } from '@angular/core';
import {AuthService} from "../../../core/auth.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PasswordsMatchValidator} from "../../../core/validators/passwords-match.validator";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      nom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', Validators.required],
      motPasse: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    }, { validators: PasswordsMatchValidator() });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const { nom, email, telephone, motPasse } = this.registerForm.value;
      this.authService.register({ nom, email, telephone, motPasse }).subscribe({
        next: () => this.router.navigate(['/clients/login']),
        error: err => console.error('Erreur d\'inscription:', err)
      });
    }
  }
}
