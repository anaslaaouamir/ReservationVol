import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SearchVolCriteria } from 'src/app/models/vol.model';

@Component({
  selector: 'app-offer-search',
  templateUrl: './offer-search.component.html',
  styleUrls: ['./offer-search.component.css',]
})
export class OfferSearchComponent {
  villeDepart = '';
  villeDestination = '';
  date!: string;
  day!: number;
  month!: number;
  year!: number;

  constructor(private router: Router) {}

  chercherVols() {
      if (!this.date) {
        alert('Veuillez sélectionner une date.');
        return;
      }

      const [year, month, day] = this.date.split('-').map(Number);

      this.router.navigate(['/offers/list-vol'], {
        queryParams: {
          villeDepart: this.villeDepart,
          villeDestination: this.villeDestination,
          day,
          month,
          year,
        },
      });
    }

  }
