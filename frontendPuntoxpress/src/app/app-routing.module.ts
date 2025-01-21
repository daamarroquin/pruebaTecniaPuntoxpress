import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guards/auth.guard';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LocationComponent } from './components/location/location.component';
import { LocationEditComponent } from './components/location/location-edit/location-edit.component';
import { ClientComponent } from './components/client/client.component';
import { ClientEditComponent } from './components/client/client-edit/client-edit.component';
import { UserEditComponent } from './components/user/user-edit/user-edit.component';
import { UserComponent } from './components/user/user.component';
import { ReservaEditComponent } from './components/dashboard/reserva-edit/reserva-edit.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'reservations/edit/:id', component: ReservaEditComponent, canActivate: [authGuard] },
  { path: 'location', component: LocationComponent, canActivate: [authGuard] },
  { path: 'location/edit/:id', component: LocationEditComponent, canActivate: [authGuard] },
  { path: 'client', component: ClientComponent, canActivate: [authGuard] },
  { path: 'client/edit/:id', component: ClientEditComponent, canActivate: [authGuard] },
  { path: 'user', component: UserComponent, canActivate: [authGuard] },
  { path: 'user/edit/:id', component: UserEditComponent, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
