import { Component, Input, signal } from '@angular/core';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EnumsService } from '../../../services/login/enums-service';
import { Router, RouterLink } from '@angular/router';
import { EmpleadoRequest } from '../../../modelos/Empleados/empleado-request';
import { Rol } from '../../../modelos/enums/rol-enum';
import { EmpleadosService } from '../../../services/login/empleados-service';

@Component({
  selector: 'app-empleado-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './empleado-form.html',
  styleUrl: './empleado-form.css',
})
export class EmpleadoForm {


  formulario !: FormGroup;
  mensajeError !: string;
  urlImagen !: string;

  mensajeCreacion: string = "Nuevo empleado";
  mensajeEdicion: string = "Edicion empleado";


  enEdicion: boolean = false;
  hayLinkImagen = signal<boolean>(false);
  hayError = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);

  roles = signal<Rol[]>([]);

  constructor(private formBuilder: FormBuilder,
    private enumsService: EnumsService,
    private router: Router, private empleadosService: EmpleadosService) {
  }


  @Input()
  empleadoEdicion !: EmpleadoRequest;

  ngOnInit(): void {
    this.enEdicion = this.empleadoEdicion != null;

    this.cargarRoles();
    this.instanciarFormulario();

    if (this.enEdicion) {
      this.hayLinkImagen.set(true);
      this.formulario.reset(this.empleadoEdicion);
    }
  }


  private instanciarFormulario() {

    if (this.enEdicion) {
      this.formulario = this.formBuilder.group({
        id: [0],
        idRol: [1, Validators.required],
        nombre: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
        contraseña: ["",],
      });

    } else {
      this.formulario = this.formBuilder.group({
        id: [0],
        idRol: [1, Validators.required],
        nombre: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
        contraseña: ["", [Validators.required, Validators.minLength(6), Validators.maxLength(100)]],
      });
    }

  }


  public enviar() {

    this.reiniciarBooleanos();

    this.intentoEnviarlo.set(true);
    if (!this.formulario.valid) return;

    let nuevo: EmpleadoRequest = this.formulario.value as EmpleadoRequest;
    

    if (this.enEdicion) {
      this.editar(nuevo);
    } else {
      this.crear(nuevo);
    }

  }


  private crear(nuevo: EmpleadoRequest) {

    this.empleadosService.crear(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/empleados`]);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }

  private editar(nuevo: EmpleadoRequest) {
    this.empleadosService.editarEmpleado(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/empleados`]);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  private cargarRoles() {
    this.enumsService.getRoles().subscribe({
      next: (roles: Rol[]) => {
        this.roles.set(roles);
      },
      error: (errorHttp: any) => {
        const errorData: ErrorBackend = errorHttp.error;
        alert(errorData.detalles);
      }
    });
  }



  public actualizarUrlImagen(event: Event) {
    const elemento = event.target as HTMLInputElement;
    this.urlImagen = elemento.value;
    this.hayLinkImagen.set(true);
  }


  private reiniciarBooleanos() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(false);
  }





}
