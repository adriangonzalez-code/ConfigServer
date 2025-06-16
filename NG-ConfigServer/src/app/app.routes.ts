import { Routes } from '@angular/router';
import {LoginComponent} from "./features/login/login.component";
import {LayoutComponent} from "./shared/layout/layout.component";
import {canActivateGuard} from "./core/auth/AuthGuard";
//import { canActivateGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [canActivateGuard],
    children: [
      { path: 'scopes', loadComponent: () => import('./features/scopes/scope.component').then(m => m.ScopesComponent) },
      /*{ path: 'dashboard', loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent) },
      { path: 'properties', loadComponent: () => import('./properties/properties.component').then(m => m.PropertiesComponent) },*/
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '' }
];
