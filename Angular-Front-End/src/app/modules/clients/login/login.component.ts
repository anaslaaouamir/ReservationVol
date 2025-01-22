import { Component } from '@angular/core';
import { AuthService } from '../../../core/auth.service';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  isLoading: boolean = false;
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  onSubmit() {
    if (!this.email || !this.password) {
      this.errorMessage = 'Please enter both email and password';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.email, this.password)
      .pipe(
        finalize(() => this.isLoading = false)
      )
      .subscribe(
        () => {
          this.authService.getUserProfile().subscribe(() => {
            this.router.navigate(['/offers/search']);
          });
        },
        (error) => {
          this.errorMessage = error || 'Login failed. Please try again.';
        }
      );
  }
  }
