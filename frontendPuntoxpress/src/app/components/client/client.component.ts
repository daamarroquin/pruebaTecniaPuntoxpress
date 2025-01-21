import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '@app/services/client.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss']
})
export class ClientComponent implements OnInit {
  clients: any[] = [];
  clientForm: FormGroup;
  isFormVisible = false;

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private router: Router
  ) {
    this.clientForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
    });
  }

  ngOnInit(): void {
    this.listarClientes();
  }

  toggleForm(): void {
    this.isFormVisible = !this.isFormVisible;

    if (!this.isFormVisible) {
      this.clientForm.reset();
    }
  }

  listarClientes(): void {
    this.clientService.listar().subscribe({
      next: (response) => {
        this.clients = response.data;
      },
      error: (error) => {
        console.error('Error al listar clientes:', error);
      },
    });
  }

  editar(id: string): void {
    this.router.navigate([`/client/edit`, id]);
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
        this.clientService.eliminar(id).subscribe({
          next: () => {
            Swal.fire('Eliminado!', 'El cliente ha sido eliminado.', 'success');
            this.listarClientes();
          },
          error: (error) => {
            console.error('Error al eliminar el cliente:', error);
            Swal.fire('Error', 'No se pudo eliminar el cliente.', 'error');
          },
        });
      }
    });
  }

  crearCliente(): void {
    if (this.clientForm.valid) {
      const nuevoCliente = this.clientForm.value;

      this.clientService.crear(nuevoCliente).subscribe({
        next: () => {
          Swal.fire('¡Éxito!', 'Cliente creado exitosamente.', 'success');
          this.clientForm.reset();
          this.listarClientes();
        },
        error: (error) => {
          console.error('Error al crear el cliente:', error);
          Swal.fire('Error', 'No se pudo crear el cliente.', 'error');
        },
      });
    }
  }
}
