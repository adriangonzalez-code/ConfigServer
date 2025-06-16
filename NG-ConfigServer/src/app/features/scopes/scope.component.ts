import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import {ScopesService} from "../../core/auth/ScopeService";
import {Scope} from "./models/scope.model";
import {FormsModule} from "@angular/forms";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-scopes',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    DatePipe
  ],
  templateUrl: './scope.component.html'
})
export class ScopesComponent implements OnInit {
  scopes: Scope[] = [];
  searchTerm = '';
  showModal = false;

  newScope: Partial<Scope> = {
    scopeName: '',
    description: ''
  };

  constructor(private scopesService: ScopesService) {}

  ngOnInit(): void {
    this.loadScopes();
  }

  loadScopes(): void {
    this.scopesService.getScopes(this.searchTerm).subscribe({
      next: scopes => this.scopes = scopes,
      error: err => console.error('Error loading scopes', err)
    });
  }

  searchScopes(): void {
    this.loadScopes();
  }

  openModal(): void {
    this.newScope = { scopeName: '', description: '' };
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  createScope(): void {
    if (!this.newScope.scopeName || !this.newScope.description) return;

    this.scopesService.createScope(this.newScope).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Scope created!',
          showConfirmButton: false,
          timer: 1500
        });
        this.closeModal();
        this.loadScopes();
      },
      error: err => {
        Swal.fire({
          icon: 'error',
          title: 'Error creating scope',
          text: err.message
        });
      }
    });
  }
}
