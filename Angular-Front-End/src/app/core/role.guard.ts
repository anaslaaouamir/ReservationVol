import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['role']; // Récupère le rôle attendu
    if (this.authService.isAuthenticated() && this.authService.hasRole(expectedRole)) {
      return true;
    }
    this.router.navigate(['/unauthorized']);
    return false;
  }
}
