import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService); // Inyectar el servicio de autenticación
  const router = inject(Router); // Inyectar el enrutador

  if (authService.isAuthenticated()) {
    return true; // Permitir el acceso si el usuario está autenticado
  } else {
    router.navigate(['/login']); // Redirigir al login si no está autenticado
    return false; // Bloquear acceso
  }
};
