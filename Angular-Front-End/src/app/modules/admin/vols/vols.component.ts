import {Component, OnInit, ViewChild} from '@angular/core';
import {Vol} from "../../../models/vol.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ConfirmationService, MessageService, PrimeNGConfig} from "primeng/api";
import {OfferService} from "../../../services/offer.service";
import {Table} from "primeng/table";

@Component({
  selector: 'app-vols',
  templateUrl: './vols.component.html',
  styleUrls: ['./vols.component.css']
})
export class VolsComponent  implements OnInit {
  @ViewChild('dt') dt!: Table;
  vols: Vol[] = [];
  selectedVols: Vol[] = [];
  displayDialog: boolean = false;
  volForm: FormGroup;
  isEditMode: boolean = false;

  constructor(
    private volService: OfferService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig,
    private fb: FormBuilder
  ) {
    this.volForm = this.fb.group({
      idVol: [null],
      nameVol: ['', Validators.required],
      companyName: ['', Validators.required],
      villeDepart: ['', Validators.required],
      villeDestination: ['', Validators.required],
      heureDepart: ['', Validators.required],
      heureArrivee: ['', Validators.required],
      prix: [0, Validators.required],
      placesDisponibles: [0, Validators.required],
      statut: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.loadVols();
  }

  loadVols(): void {
    this.volService.getVols().subscribe((data: Vol[]) => {
      console.log(data); // Debug : vérifiez si `data` est bien un tableau
      this.vols = data;
    }, error => {
      console.error('Erreur lors de la récupération des vols :', error);
    });
  }

  showDialogToAdd(): void {
    this.isEditMode = false;
    this.volForm.reset();
    this.displayDialog = true;
  }

  saveVol(): void {
    if (this.volForm.valid) {
      const vol: Vol = this.volForm.value;

      // Vérification si les valeurs de date sont valides
      if (vol.heureDepart) {
        vol.heureDepart = new Date(vol.heureDepart).toISOString();
      } else {
        console.error('Heure de départ invalide');
      }

      if (vol.heureArrivee) {
        vol.heureArrivee = new Date(vol.heureArrivee).toISOString();
      } else {
        console.error('Heure d\'arrivée invalide');
      }

      console.log('Données envoyées au backend :', vol); // Debug

      if (this.isEditMode) {
        this.volService.updateVol(vol).subscribe(
          () => {
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Vol updated' });
            this.loadVols();
            this.displayDialog = false;
          },
          error => console.error('Erreur lors de la mise à jour du vol :', error)
        );
      } else {
        this.volService.addVol(vol).subscribe(
          () => {
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Vol added' });
            this.loadVols();
            this.displayDialog = false;
          },
          error => console.error('Erreur lors de l’ajout du vol :', error)
        );
      }
    }
  }





  editVol(vol: Vol): void {
    this.isEditMode = true;
    this.volForm.patchValue(vol);
    this.displayDialog = true;
  }

/*  deleteVols(vol: Vol): void {
    console.error('Heure de départ invalide');
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this vol?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.volService.deleteVol(vol.idVol).subscribe(() => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Vol deleted' });
          this.loadVols();
        });
      }
    });
  }*/



  deleteVols(vol: Vol): void {
        this.volService.deleteVol(vol.idVol).subscribe(() => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Vol deleted' });
          this.loadVols();
        });
      }





  applyFilter(event: Event, field: string): void {
    const inputElement = event.target as HTMLInputElement;
    this.dt.filter(inputElement.value, field, 'contains');
  }
}
