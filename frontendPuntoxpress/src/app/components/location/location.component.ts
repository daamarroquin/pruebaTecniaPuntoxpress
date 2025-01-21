import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocationService } from '@app/services/location.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.scss'],
})
export class LocationComponent implements OnInit {
  locations: any[] = [];
  locationForm: FormGroup;
  isFormVisible = false; // Controla la visibilidad del formulario

  constructor(
    private fb: FormBuilder,
    private locationService: LocationService,
    private router: Router
  ) {
    this.locationForm = this.fb.group({
      number: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Solo números
      locationName: ['', Validators.required],
      capacity: ['', [Validators.required, Validators.min(1)]], // Número mínimo de 1
    });
  }

  ngOnInit(): void {
    this.listarUbicaciones();
  }


  toggleForm(): void {
    this.isFormVisible = !this.isFormVisible;

    if (!this.isFormVisible) {
      this.locationForm.reset();
    }
  }

  listarUbicaciones(): void {
    this.locationService.listar().subscribe({
      next: (response) => {
        this.locations = response.data;
      },
      error: (error) => {
        console.error('Error al listar ubicaciones:', error);
      },
    });
  }

  editar(id: string): void {
    // Navegar a la página de edición pasando el ID como parámetro
    this.router.navigate([`/location/edit`, id]);
  }

  confirmDelete(id: string): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'No podrás revertir esta acción.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.locationService.eliminar(id).subscribe({
          next: () => {
            Swal.fire(
              'Eliminado!',
              'La ubicación ha sido eliminada.',
              'success'
            );
            this.listarUbicaciones(); // Refrescar lista
          },
          error: (error) => {
            console.error('Error al eliminar la ubicación:', error);
            Swal.fire(
              'Error!',
              'Ocurrió un error al eliminar la ubicación.',
              'error'
            );
          },
        });
      }
    });
  }

  crearUbicacion(): void {
    if (this.locationForm.valid) {
      const nuevaUbicacion = this.locationForm.value;

      this.locationService.crear(nuevaUbicacion).subscribe({
        next: () => {
          Swal.fire('¡Éxito!', 'Ubicación creada exitosamente.', 'success');
          this.locationForm.reset(); // Limpia el formulario después de enviar
          this.listarUbicaciones(); // Refrescar lista
        },
        error: (error) => {
          console.error('Error al crear la ubicación:', error);
          Swal.fire(
            'Error',
            'Ocurrió un error al crear la ubicación.',
            'error'
          );
        },
      });
    }
  }
}
