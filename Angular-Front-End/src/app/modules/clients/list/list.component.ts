import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../../../services/client.service";
import {MessageService} from "primeng/api";

interface Client {
  idClient: number;
  nom: string;
  email: string;
  motPasse: string;
  telephone: string;
}
@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
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


}
