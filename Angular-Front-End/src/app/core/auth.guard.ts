import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class authGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    console.log('Checking token...');
    const token = localStorage.getItem('token');
    console.log('Token:', token);

    if (token) {
      return true;
    }

    console.log('No token found, redirecting to login...');
    this.router.navigate(['clients/login']).then(success => {
      console.log('Navigation success:', success);
    }).catch(error => {
      console.error('Navigation error:', error);
    });

    return false;
  }
}
