import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '@app/services/client.service';
import { LocationService } from '@app/services/location.service';
import { ReservationService } from '@app/services/reservation.service';
import { UserService } from '@app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  reservationForm: FormGroup;
  clients: any[] = [];
  locations: any[] = [];
  reservations: any[] = [];

  isFormVisible = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private clientService: ClientService,
    private locationService: LocationService,
    private reservationService: ReservationService
  ) {
    this.reservationForm = this.fb.group({
      name: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(100),
        ],
      ],
      date: ['', Validators.required],
      time: ['', Validators.required],
      numberOfPeople: [1, [Validators.required, Validators.min(1)]],
      status: ['', Validators.required],
      client: [{ value: '', disabled: false }], // Initially enabled
      location: [{ value: '', disabled: false }], // Initially enabled
      useGenericClient: [false],
      useGenericLocation: [false],
    });
  }

  ngOnInit(): void {
    this.loadClients();
    this.loadLocations();
    this.listarReservas();

    // Listen to the changes in `useGenericClient` and `useGenericLocation` checkboxes
    this.reservationForm
      .get('useGenericClient')
      ?.valueChanges.subscribe((useGenericClient) => {
        const clientControl = this.reservationForm.get('client');
        if (useGenericClient) {
          clientControl?.disable();
          clientControl?.reset();
        } else {
          clientControl?.enable();
        }
      });

    this.reservationForm
      .get('useGenericLocation')
      ?.valueChanges.subscribe((useGenericLocation) => {
        const locationControl = this.reservationForm.get('location');
        if (useGenericLocation) {
          locationControl?.disable();
          locationControl?.reset();
        } else {
          locationControl?.enable();
        }
      });
  }

  loadClients(): void {
    this.clientService.listar().subscribe({
      next: (response) => (this.clients = response.data),
      error: (error) => console.error('Error al cargar clientes:', error),
    });
  }

  loadLocations(): void {
    this.locationService.listar().subscribe({
      next: (response) => (this.locations = response.data),
      error: (error) => console.error('Error al cargar ubicaciones:', error),
    });
  }

  listarReservas(): void {
    this.reservationService.listar().subscribe({
      next: (response) => (this.reservations = response.data),
      error: (error) => console.error('Error al cargar ubicaciones:', error),
    });
  }

  toggleForm(): void {
    this.isFormVisible = !this.isFormVisible;
    if (!this.isFormVisible) {
      this.reservationForm.reset();
    }
  }

  guardarReserva(): void {
    const formValue = this.reservationForm.value;

    if (formValue.useGenericClient) {
      formValue.client = null;
    }
    if (formValue.useGenericLocation) {
      formValue.location = null;
    }

    let data = {
      user: {
        id: '762a9bdf-51d9-46b3-94d1-ee4cc3d9af65',
      },
      client: {
        id: formValue.client,
      },
      location: {
        id: formValue.location,
      },
      name: formValue.name,
      date: formValue.date,
      time: formValue.time,
      numberOfPeople: formValue.numberOfPeople,
      status: formValue.status,
    };

    this.reservationService.crear(data).subscribe({
            next: () => {
              Swal.fire('¡Éxito!', 'Usuario creado exitosamente.', 'success');
              this.reservationForm.reset(); // Limpia el formulario después de enviar
              this.listarReservas(); // Refrescar lista
              this.isFormVisible = false; // Ocultar el formulario
            },
            error: (error) => {
              console.error('Error al crear el usuario:', error);
              Swal.fire(
                'Error',
                'Ocurrió un error al crear el usuario.',
                'error'
              );
            },
          });
  }

  editar(id: string): void {
    this.router.navigate([`/reservations/edit`, id]);
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
    }).then((result) => {
      if (result.isConfirmed) {
        this.reservationService.eliminar(id).subscribe({
          next: () => {
            Swal.fire('Eliminado!', 'La reserva ha sido eliminada.', 'success');
            this.listarReservas(); // Refresh the list
          },
          error: (error) => {
            console.error('Error al eliminar la reserva:', error);
            Swal.fire('Error', 'No se pudo eliminar la reserva.', 'error');
          },
        });
      }
    });
  }
}
