import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ClientService} from "../../../services/client.service";
import { MessageService } from 'primeng/api';


interface Client {
  idClient: number;
  nom: string;
  email: string;
  motPasse: string;
  telephone: string;
}


@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})


export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  clientDialog = false;
  clientForm: FormGroup;
  editMode = false;

  constructor(
    private clientService: ClientService,
    private formBuilder: FormBuilder,
    private messageService: MessageService
  ) {
    this.clientForm = this.formBuilder.group({
      idClient: [null],
      nom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      motPasse: ['', Validators.required],
      telephone: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.loadClients();
  }

  loadClients() {
    this.clientService.getClients().subscribe(data => {
      this.clients = data;
    });
  }

  openNew() {
    this.clientForm.reset();
    this.editMode = false;
    this.clientDialog = true;
  }

  editClient(client: Client) {
    this.clientForm.patchValue(client);
    this.editMode = true;
    this.clientDialog = true;
  }

  deleteClient(client: Client) {
    this.clientService.deleteClient(client.idClient).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Succès', detail: 'Client supprimé'});
      this.loadClients();
    });
  }

  hideDialog() {
    this.clientDialog = false;
  }

  saveClient() {
    if (this.clientForm.valid) {
      if (this.editMode) {
        const id = this.clientForm.get('idClient')?.value;
        this.clientService.updateClient(id, this.clientForm.value).subscribe(() => {
          this.messageService.add({severity: 'success', summary: 'Succès', detail: 'Client mis à jour'});
          this.loadClients();
          this.hideDialog();
        });
      } else {
        this.clientService.createClient(this.clientForm.value).subscribe(() => {
          this.messageService.add({severity: 'success', summary: 'Succès', detail: 'Client créé'});
          this.loadClients();
          this.hideDialog();
        });
      }
    }
  }

  onSearch(event: Event) {
    const searchValue = (event.target as HTMLInputElement).value;
    this.clientService.searchClients(searchValue).subscribe(data => {
      this.clients = data;
    });
  }
}
