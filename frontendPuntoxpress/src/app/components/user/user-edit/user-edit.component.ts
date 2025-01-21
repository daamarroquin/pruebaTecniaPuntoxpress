import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '@app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {
  userForm: FormGroup;
  userId: string;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize the form
    this.userForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      role: ['', Validators.required],
      password: ['', [Validators.minLength(8)]], // Password is optional but requires at least 8 characters
    });
    this.userId = '';
  }

  ngOnInit(): void {
    // Get the ID from the route
    this.userId = this.route.snapshot.paramMap.get('id') || '';
    if (this.userId) {
      this.loadUserData(this.userId);
    }
  }

  loadUserData(id: string): void {
    // Fetch user data
    this.userService.buscarPorId(id).subscribe({
      next: (response) => {
        const user = response.data;
        this.userForm.patchValue({
          name: user.name,
          email: user.email,
          phoneNumber: user.phoneNumber,
          role: user.role,
        });
      },
      error: (error) => {
        console.error('Error fetching user:', error);
        Swal.fire('Error', 'No se pudo cargar el usuario.', 'error');
      },
    });
  }

  actualizarUsuario(): void {
    if (this.userForm.valid) {
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
          this.userService.actualizar(this.userId, this.userForm.value).subscribe({
            next: () => {
              Swal.fire('¡Actualizado!', 'El usuario ha sido actualizado.', 'success');
              this.router.navigate(['/user']); // Redirect after update
            },
            error: (error) => {
              console.error('Error updating user:', error);
              Swal.fire('Error', 'No se pudo actualizar el usuario.', 'error');
            },
          });
        }
      });
    } else {
      Swal.fire('Formulario inválido', 'Corrija los errores antes de continuar.', 'error');
    }
  }
}
