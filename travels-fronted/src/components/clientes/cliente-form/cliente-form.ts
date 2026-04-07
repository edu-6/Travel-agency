import { Component, Input, OnInit, signal } from '@angular/core';
import { PaqueteServicio } from '../../../modelos/proveedor-servicio/paquete-servicio';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Nacionalidad } from '../../../modelos/enums/nacionalidad-enum';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { EnumsService } from '../../../services/login/enums-service';
import { ClienteRequest } from '../../../modelos/clientes/cliente-request';
import { ClientesService } from '../../../services/login/clientes-service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-cliente-form',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './cliente-form.html',
  styleUrl: './cliente-form.css',
})
export class ClienteForm implements OnInit {

  formulario !: FormGroup;



  intentoEnviarlo = signal<boolean>(false);
  enEdicion = signal<boolean>(false);
  hayError = signal<boolean>(false);

  nacionalidades = signal<Nacionalidad[]>([]);

  mensajeError !: string;

  identificacionRecibida  !: string;

  vieneDeReservacionForm: boolean = false;

  origen !: string;

  @Input()
  identificacionNuevo !: string;


  @Input()

  edicion !: ClienteRequest;


  constructor(
    private formBuilder: FormBuilder,
    private enumsService: EnumsService,
    private clientesService: ClientesService,
    private router: Router,
    private routerParams: ActivatedRoute,
    private location: Location) {
  }


  ngOnInit(): void {

    if (this.edicion) {
      this.enEdicion.set(true);
    }


    this.origen = this.routerParams.snapshot.queryParamMap.get('origin') || history.state?.origin || 'default';


    this.cargarNacionalidades();
    this.instanciarFormulario();


    // recibir el parametro
    this.identificacionRecibida = this.routerParams.snapshot.params['identificacion'];
    if (this.identificacionRecibida) {
      this.vieneDeReservacionForm = true;
      this.formulario.patchValue({
        identificacion: this.identificacionRecibida
      });
    }


    if (this.edicion) {
      this.formulario.reset(this.edicion);
    }
  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group(
      {
        identificacion: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
        nombre: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
        correo: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
        telefono: [null, [Validators.required, Validators.min(1)]],
        id_nacionalidad: [1, [Validators.required]],
        fechaNacimiento: [new Date().toISOString().substring(0, 10), [Validators.required]],
      }
    );
  }


  enviar() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(true);
    if (!this.formulario.valid) return;

    let nuevo = this.formulario.value as ClienteRequest;
    console.log(nuevo);
    if (this.enEdicion()) {
      this.editar(nuevo);
    } else {
      this.guardarNuevo(nuevo);
    }

  }


  private editar(nuevo: ClienteRequest) {
    this.clientesService.editarCliente(nuevo).subscribe({
      next: () => {
        this.redirigirAPagina();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  private guardarNuevo(nuevo: ClienteRequest) {
    this.clientesService.crear(nuevo).subscribe({

      next: () => {
        this.redirigirAPagina();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }


  cancelar() {
    this.redirigirAPagina();
  }

  redirigirAPagina() {
    if(this.origen === 'reservaciones-form'){

      this.router.navigate(['/reservaciones/form-page', this.formulario.value.identificacion]);

    }else if(this.origen ==='clientes-page'){

      this.router.navigate(['/clientes']);
    }
  }



  cargarNacionalidades() {
    this.enumsService.getNacionalidades().subscribe({
      next: (array: Nacionalidad[]) => {
        this.nacionalidades.set(array);
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


}
