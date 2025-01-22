import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OfferService } from '../../../services/offer.service';
import { Vol, SearchVolCriteria } from 'src/app/models/vol.model';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-offer-list',
  templateUrl: './offer-list.component.html',
  styleUrls: ['./offer-list.component.css']
})
export class OfferListComponent implements OnInit {
  villeDepart = '';
  villeDestination = '';
  day!: number;
  month!: number;
  year!: number;

  flightResults: any[] = []; // Les résultats des vols retournés par le backend
  loading = true;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private offerService: OfferService
  ) {}

  ngOnInit() {
    // Récupérer les paramètres de recherche depuis l'URL
    this.route.queryParams.subscribe((params) => {
      this.villeDepart = params['villeDepart'] || '';
      this.villeDestination = params['villeDestination'] || '';
      this.day = +params['day'];
      this.month = +params['month'];
      this.year = +params['year'];

      // Charger les vols en fonction des critères
      this.loadFlights();
    });
  }

  loadFlights() {
    this.loading = true;
    this.errorMessage = '';

    this.offerService
      .chercherVol(this.villeDepart, this.villeDestination, this.day, this.month, this.year)
      .subscribe({
        next: (flights) => {
          this.flightResults = flights;
          this.loading = false;
        },
        error: (err) => {
          this.errorMessage = 'Une erreur est survenue lors du chargement des vols.';
          console.error(err);
          this.loading = false;
        },
      });
  }

  selectFlight(flight: any): void {
    alert(`Vous avez sélectionné le vol ${flight.numVol}`);
  }
}
