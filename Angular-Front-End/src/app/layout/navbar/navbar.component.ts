import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../core/auth.service";
import {Router} from "@angular/router";
import {Client} from "../../models/client.model";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  currentClient: Client | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.getCurrentClient().subscribe(
      client => this.currentClient = client
    );
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/clients/login']);
  }
}
