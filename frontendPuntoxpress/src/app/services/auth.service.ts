import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post(`${this.apiUrl}/login`, body, {
      headers: { 'Content-Type': 'application/json; charset=utf-8' },
    });
  }

  getToken(): string | null {
    return localStorage.getItem('authToken'); // Obtener el token
  }

  logout(): void {
    localStorage.removeItem('authToken'); // Eliminar el token
  }

  isAuthenticated(): boolean {
    return !!this.getToken(); // Verificar si hay un token
  }
}
