import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationService } from '@app/services/location.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-location-edit',
  templateUrl: './location-edit.component.html',
  styleUrls: ['./location-edit.component.scss']
})
export class LocationEditComponent implements OnInit {
  locationForm: FormGroup;
  locationId: string;

  constructor(
    private fb: FormBuilder,
    private locationService: LocationService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Inicializa el formulario reactivo
    this.locationForm = this.fb.group({
      number: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      locationName: ['', Validators.required],
      capacity: ['', [Validators.required, Validators.min(1)]],
    });
    this.locationId = '';
  }

  ngOnInit(): void {
    // Obtiene el ID desde la URL
    this.locationId = this.route.snapshot.paramMap.get('id') || '';
    if (this.locationId) {
      this.cargarDatos(this.locationId);
    }
  }

  cargarDatos(id: string): void {
    // Carga los datos de la ubicación
    this.locationService.buscarPorId(id).subscribe({
      next: (response) => {
        const location = response.data; // Accede al objeto dentro del campo `data`
        this.locationForm.patchValue({
          number: location.number,
          locationName: location.locationName,
          capacity: location.capacity,
        });
      },
      error: (error) => {
        console.error('Error al cargar la ubicación:', error);
        Swal.fire('Error', 'No se pudo cargar la ubicación.', 'error');
      },
    });
  }

  actualizarUbicacion(): void {
    if (this.locationForm.valid) {
      Swal.fire({
        title: '¿Estás seguro?',
        text: 'Los cambios se guardarán permanentemente.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, actualizar!',
      }).then((result) => {
        if (result.isConfirmed) {
          this.locationService.actualizar(this.locationId, this.locationForm.value).subscribe({
            next: () => {
              Swal.fire('¡Actualizado!', 'La ubicación ha sido actualizada.', 'success');
              this.router.navigate(['/location']); // Redirigir después de actualizar
            },
            error: (error) => {
              console.error('Error al actualizar la ubicación:', error);
              Swal.fire('Error', 'No se pudo actualizar la ubicación.', 'error');
            },
          });
        }
      });
    } else {
      Swal.fire('Formulario inválido', 'Por favor, corrige los errores antes de continuar.', 'error');
    }
  }
}
