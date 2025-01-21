import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  private apiUrl = `${environment.apiUrl}/location`; // URL base del controlador de Location

  constructor(private http: HttpClient) {}

  /**
   * Listar todas las ubicaciones con paginación.
   * @param page Número de página (por defecto 0).
   * @param size Tamaño de la página (por defecto 100).
   * @returns Observable con la lista de ubicaciones.
   */
  listar(page: number = 0, size: number = 100): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get(`${this.apiUrl}/listar`, { params });
  }

  /**
   * Crear una nueva ubicación.
   * @param location Objeto con los datos de la nueva ubicación.
   * @returns Observable con la respuesta del servidor.
   */
  crear(location: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${this.apiUrl}/crear`, location, { headers });
  }

  /**
   * Actualizar una ubicación existente.
   * @param id ID de la ubicación a actualizar.
   * @param location Objeto con los datos actualizados.
   * @returns Observable con la respuesta del servidor.
   */
  actualizar(id: string, location: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put(`${this.apiUrl}/actualizar/${id}`, location, { headers });
  }

  /**
   * Eliminar una ubicación.
   * @param id ID de la ubicación a eliminar.
   * @returns Observable con la respuesta del servidor.
   */
  eliminar(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${id}`);
  }

  /**
   * Buscar una ubicación por ID.
   * @param id ID de la ubicación a buscar.
   * @returns Observable con la respuesta del servidor.
   */
  buscarPorId(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/buscar/${id}`);
  }
}
