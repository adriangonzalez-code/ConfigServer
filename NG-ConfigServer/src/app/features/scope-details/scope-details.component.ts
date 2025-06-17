import { ActivatedRoute } from '@angular/router';
import { Component } from "@angular/core";
import { Scope } from "../scopes/models/scope.model";
import { ScopesService } from "../../core/services/ScopeService";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import { PropertyService } from "../../core/services/PropertyService";
import {FormsModule} from "@angular/forms";
import {Property} from "./models/property.model";

@Component({
  selector: 'app-scope-details',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgClass,
    NgIf
  ],
  templateUrl: './scope-details.component.html'
})
export class ScopeDetailsComponent {

  activeTab: 'plain' | 'secret' | 'users' | 'access' = 'plain';
  editMode: boolean = false;
  scopeId: number = 0;
  properties: any[] = [];
  showSecrets: Record<number, boolean> = {};
  showAccessKey: boolean = false;
  accessKey: string = ''; // asigna desde el backend o como corresponda
  scopeDetails: Scope = {
    id: 0,
    scopeName: '',
    description: '',
    accessKey: '',
    createdBy: '',
    createdAt: '',
    users: []
  };

  constructor(private route: ActivatedRoute, private scopesService: ScopesService, private propertyService: PropertyService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.scopeId = +id;
        this.loadScopeDetails(this.scopeId);
      }
    });
  }

  loadScopeDetails(id: number) {
    this.scopesService.getScopeById(id).subscribe({
      next: (scope: Scope) => {
        this.scopeDetails = scope;

        this.loadProperties(id);
      },
      error: (err) => {
        console.error('Error loading scope details:', err);
      }
    });
  }

  loadProperties(scopeId: number) {
    this.propertyService.getProperties(scopeId).subscribe({
      next: (properties) => {
        this.properties = properties;
      },
      error: (err) => {
        console.error('Error loading properties:', err);
      }
    });
  }

  getFilteredProperties(tab: 'plain' | 'secret') {
    return this.properties.filter(p => p.secret === (tab === 'secret'));
  }

  addProperty(tab: 'plain' | 'secret') {
    this.properties.push({
      id: 0,
      key: '',
      value: '',
      secret: tab === 'secret'
    });
  }

  toggleEditMode() {
    this.editMode = !this.editMode;
  }

  removeProperty(propToRemove: Property) {
    this.properties = this.properties.filter(p => p !== propToRemove);
  }

  addUser() {
    this.scopeDetails.users.push('');
  }

  removeUser(index: number) {
    this.scopeDetails.users.splice(index, 1);
  }

  toggleSecretVisibility(propId: number): void {
    this.showSecrets[propId] = !this.showSecrets[propId];
  }

  saveUsers() {
    // Aquí haces el llamado al servicio
    /*this.scopeService.updateUsers(this.scopeId, this.users).subscribe(() => {
      Swal.fire('Saved!', 'User list updated.', 'success');
    });*/
  }
}
