import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from "../../core/auth.service";
import { Router } from "@angular/router";
import { Client } from "../../models/client.model";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  currentClient: Client | null = null;
  private clientSubscription!: Subscription;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.clientSubscription = this.authService.getCurrentClient().subscribe(
      client => this.currentClient = client
    );
  }

  ngOnDestroy(): void {
    if (this.clientSubscription) {
      this.clientSubscription.unsubscribe();
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/clients/login']);
  }
}
