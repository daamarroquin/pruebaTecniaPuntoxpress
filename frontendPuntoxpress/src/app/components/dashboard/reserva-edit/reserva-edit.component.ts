import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '@app/services/client.service';
import { LocationService } from '@app/services/location.service';
import { ReservationService } from '@app/services/reservation.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-reserva-edit',
  templateUrl: './reserva-edit.component.html',
  styleUrls: ['./reserva-edit.component.scss'],
})
export class ReservaEditComponent implements OnInit {
  reservationForm: FormGroup;
  reservationId: string;
  clients: any[] = [];
  locations: any[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private locationService: LocationService,
    private reservationService: ReservationService
  ) {
    this.reservationForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      date: ['', Validators.required],
      time: ['', Validators.required],
      numberOfPeople: ['', [Validators.required, Validators.min(1)]],
      status: ['', Validators.required],
      client: [''], // Opcional: Si estás manejando clientes
      location: [''], // Opcional: Si estás manejando ubicaciones
    });

    this.reservationId = '';
  }

  ngOnInit(): void {
    this.reservationId = this.route.snapshot.paramMap.get('id') || '';
    if (this.reservationId) {
      this.cargarDatos(this.reservationId);
    }
    this.cargarClientes();
    this.cargarUbicaciones();
  }

  cargarDatos(id: string): void {
    this.reservationService.buscarPorId(id).subscribe({
      next: (response) => {
        const reservation = response.data;
        this.reservationForm.patchValue({
          name: reservation.name,
          date: reservation.date,
          time: reservation.time,
          numberOfPeople: reservation.numberOfPeople,
          status: reservation.status,
          client: reservation.client.id,
          location: reservation.location ? reservation.location.id : '',
        });
      },
      error: () => Swal.fire('Error', 'No se pudo cargar la reserva.', 'error'),
    });
  }

  cargarClientes(): void {
    // Cargar lista de clientes
    this.clientService.listar().subscribe({
      next: (response) => (this.clients = response.data),
      error: () =>
        Swal.fire('Error', 'No se pudieron cargar los clientes.', 'error'),
    });
  }

  cargarUbicaciones(): void {
    // Cargar lista de ubicaciones
    this.locationService.listar().subscribe({
      next: (response) => (this.locations = response.data),
      error: () =>
        Swal.fire('Error', 'No se pudieron cargar las ubicaciones.', 'error'),
    });
  }

  actualizarReserva(): void {
    if (this.reservationForm.valid) {
      let data = {
        user: {
          id: '762a9bdf-51d9-46b3-94d1-ee4cc3d9af65', // ID del usuario (puedes obtenerlo de otra forma si es dinámico)
        },
        client: {
          id: this.reservationForm.get('client')?.value, // Obtiene el valor del control 'client'
        },
        location: {
          id: this.reservationForm.get('location')?.value, // Obtiene el valor del control 'location'
        },
        name: this.reservationForm.get('name')?.value, // Obtiene el valor del control 'name'
        date: this.reservationForm.get('date')?.value, // Obtiene el valor del control 'date'
        time: this.reservationForm.get('time')?.value, // Obtiene el valor del control 'time'
        numberOfPeople: this.reservationForm.get('numberOfPeople')?.value, // Obtiene el valor del control 'numberOfPeople'
        status: this.reservationForm.get('status')?.value,
      };

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
          this.reservationService
            .actualizar(this.reservationId, data)
            .subscribe({
              next: () => {
                Swal.fire(
                  '¡Actualizado!',
                  'La reserva ha sido actualizada.',
                  'success'
                );
                this.router.navigate(['/dashboard']);
              },
              error: () =>
                Swal.fire(
                  'Error',
                  'No se pudo actualizar la reserva.',
                  'error'
                ),
            });
        }
      });
    } else {
      Swal.fire(
        'Formulario inválido',
        'Corrige los errores antes de continuar.',
        'error'
      );
    }
  }
}
