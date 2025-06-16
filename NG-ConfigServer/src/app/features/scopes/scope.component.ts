import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import Swal from 'sweetalert2';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-scopes',
  standalone: true,
  imports: [
    FormsModule
  ],
  template: 'scope.component.html'
})
export class ScopesComponent implements OnInit {
  scopes: any[] = [];
  searchTerm = '';
  showModal = false;

  newScope = {
    name: '',
    description: ''
  };

  constructor(private http: HttpClient) {
    this.loadScopes();
  }

  ngOnInit(): void {
    this.loadScopes();
  }

  loadScopes() {
    debugger;
    const params = this.searchTerm
      ? new HttpParams().set('search', this.searchTerm)
      : undefined;

    this.http.get<any[]>('http://localhost:8080/api/scopes', { params }).subscribe(scopes => {
      this.scopes = scopes;
    });
  }

  searchScopes() {
    this.loadScopes();
  }

  openModal() {
    this.newScope = { name: '', description: '' };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  createScope() {
    this.http.post('/api/scopes', this.newScope).subscribe(() => {
      Swal.fire({
        icon: 'success',
        title: 'Scope created!',
        showConfirmButton: false,
        timer: 1500
      });
      this.closeModal();
      this.loadScopes();
    });
  }
}
