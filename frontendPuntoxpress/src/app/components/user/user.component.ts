import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '@app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  users: any[] = [];
  userForm: FormGroup;
  isFormVisible = false; // Controla la visibilidad del formulario

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    // Inicializa el formulario reactivo
    this.userForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      role: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.listarUsuarios();
  }

  toggleForm(): void {
    this.isFormVisible = !this.isFormVisible;

    if (!this.isFormVisible) {
      this.userForm.reset();
    }
  }

  listarUsuarios(): void {
    this.userService.listar().subscribe({
      next: (response) => {
        console.log('Usuarios:', response);
        this.users = response.data;
      },
      error: (error) => {
        console.error('Error al listar usuarios:', error);
      },
    });
  }

  crearUsuario(): void {
    if (this.userForm.valid) {
      const nuevoUsuario = this.userForm.value;

      this.userService.crear(nuevoUsuario).subscribe({
        next: () => {
          Swal.fire('¡Éxito!', 'Usuario creado exitosamente.', 'success');
          this.userForm.reset(); // Limpia el formulario después de enviar
          this.listarUsuarios(); // Refrescar lista
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
  }

  editar(id: string): void {
    // Navegar a la página de edición pasando el ID como parámetro
    this.router.navigate([`/user/edit`, id]);
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
        this.userService.eliminar(id).subscribe({
          next: () => {
            Swal.fire('Eliminado!', 'El usuario ha sido eliminado.', 'success');
            this.listarUsuarios(); // Refrescar lista
          },
          error: (error) => {
            console.error('Error al eliminar el usuario:', error);
            Swal.fire(
              'Error!',
              'Ocurrió un error al eliminar el usuario.',
              'error'
            );
          },
        });
      }
    });
  }
}
