import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '@app/services/client.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss']
})
export class ClientEditComponent implements OnInit {
  clientForm: FormGroup;
  clientId: string;

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Inicializa el formulario reactivo
    this.clientForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
    });
    this.clientId = '';
  }

  ngOnInit(): void {
    // Obtiene el ID del cliente desde la URL
    this.clientId = this.route.snapshot.paramMap.get('id') || '';
    if (this.clientId) {
      this.cargarDatos(this.clientId);
    }
  }

  cargarDatos(id: string): void {
    // Carga los datos del cliente
    this.clientService.buscarPorId(id).subscribe({
      next: (response) => {
        const client = response.data; // Accede al objeto dentro del campo `data`
        this.clientForm.patchValue({
          firstName: client.firstName,
          lastName: client.lastName,
          email: client.email,
          phoneNumber: client.phoneNumber,
        });
      },
      error: (error) => {
        console.error('Error al cargar el cliente:', error);
        Swal.fire('Error', 'No se pudo cargar el cliente.', 'error');
      },
    });
  }

  actualizarCliente(): void {
    if (this.clientForm.valid) {
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
          this.clientService.actualizar(this.clientId, this.clientForm.value).subscribe({
            next: () => {
              Swal.fire('¡Actualizado!', 'El cliente ha sido actualizado.', 'success');
              this.router.navigate(['/client']); // Redirigir después de actualizar
            },
            error: (error) => {
              console.error('Error al actualizar el cliente:', error);
              Swal.fire('Error', 'No se pudo actualizar el cliente.', 'error');
            },
          });
        }
      });
    } else {
      Swal.fire('Formulario inválido', 'Por favor, corrige los errores antes de continuar.', 'error');
    }
  }
}
