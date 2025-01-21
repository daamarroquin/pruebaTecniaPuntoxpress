import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        if (response.data) {
          // Guardar el token en localStorage
          localStorage.setItem('authToken', response.data);

          // Redirigir al dashboard
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = 'Error inesperado: No se recibió un token.';
        }
      },
      error: (error) => {
        console.error('Login error:', error);
        this.errorMessage = 'Error al iniciar sesión. Verifica tus credenciales.';
      }
    });
  }
}
