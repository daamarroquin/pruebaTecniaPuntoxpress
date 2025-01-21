import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LocationComponent } from './components/location/location.component';
import { LocationEditComponent } from './components/location/location-edit/location-edit.component';
import { ClientComponent } from './components/client/client.component';
import { ClientEditComponent } from './components/client/client-edit/client-edit.component';
import { UserComponent } from './components/user/user.component';
import { UserEditComponent } from './components/user/user-edit/user-edit.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { ReservaEditComponent } from './components/dashboard/reserva-edit/reserva-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    LocationComponent,
    LocationEditComponent,
    ClientComponent,
    ClientEditComponent,
    UserComponent,
    UserEditComponent,
    ReservaEditComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [ {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  },],
  bootstrap: [AppComponent]
})
export class AppModule { }
