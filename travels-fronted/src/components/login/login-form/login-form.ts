import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioLoginRequest } from '../../../modelos/login/LoginRequest';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { UsuarioLoginResponse } from '../../../modelos/login/LoginResponse';
import { AutenticacionServicio } from '../../../services/login/autenficacion-service';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
@Component({
  selector: 'app-login-form',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginForm implements OnInit {
  formulario !: FormGroup;


  empleadoLoginRequest !: UsuarioLoginRequest;

  btnPresionado: boolean = false;
  loginFallido: boolean = false;

  constructor(private formBuilder: FormBuilder, private autenticacionService: AutenticacionServicio, private router: Router) {

  }

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.maxLength(100)]],
      contraseña: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  private resetearFormulario(): void {
    this.formulario.reset();
  }




  public enviarFormulario(): void {
    this.btnPresionado = true;

    if (this.formulario.valid) {

      this.empleadoLoginRequest = this.formulario.value as UsuarioLoginRequest;

      this.autenticacionService.login(this.empleadoLoginRequest).subscribe({
        next: (empleado: UsuarioLoginResponse) => {

          this.router.navigate(["/home"]);
        },
        error: (errorHttp: any) => {
          
          const errorData: ErrorBackend = errorHttp.error;
          this.loginFallido = true;
          this.resetearFormulario();
        }
      });
    }
  }

}
