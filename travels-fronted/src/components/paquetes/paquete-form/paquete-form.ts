import { Component, input, Input, model, OnInit, signal, ViewChild } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { EnumsService } from '../../../services/login/enums-service';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { DestinoRequest } from '../../../modelos/destinos/destino-request';
import { Pais } from '../../../modelos/enums/pais-enum';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { DestinosService } from '../../../services/login/destinos-service';
import { DestinoResponse } from '../../../modelos/destinos/destino-reponse';
import { PaqueteServicioForm } from "../paquetes-servicios/paquete-servicio-form/paquete-servicio-form";
import { PaqueteRequest } from '../../../modelos/paquetes/paquete-request';
import { PaqueteGeneral } from '../../../modelos/paquetes/paquete-full';
import { PaquetesService } from '../../../services/login/paquetes-service';

@Component({
  selector: 'app-paquete-form',
  imports: [ReactiveFormsModule, PaqueteServicioForm, RouterLink],
  templateUrl: './paquete-form.html',
  styleUrl: './paquete-form.css',
})
export class PaqueteForm implements OnInit {


  @Input()
  paqueteFullParametro !: PaqueteGeneral;

  paqueteFull = signal<PaqueteGeneral>({} as PaqueteGeneral);

  @ViewChild(PaqueteServicioForm) componenteHijo!: PaqueteServicioForm;

  paises = signal<Pais[] | null>(null);

  formulario !: FormGroup;
  mensajeError !: string;
  urlImagen !: string;


  destinos = signal<DestinoResponse[]>([]);

  mensajeCreacion: string = "Nuevo paquete";
  mensajeEdicion: string = "Edicion paquete";


  enEdicion = signal<boolean>(false);
  hayLinkImagen = signal<boolean>(false);
  hayError = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);

  constructor(private formBuilder: FormBuilder,
    private enumsService: EnumsService,
    private destinosService: DestinosService,
    private paquetesService: PaquetesService,
    private router: Router) {
  }


  ngOnInit(): void {
    this.instanciarFormulario();
    this.enEdicion.set(this.paqueteFullParametro != null);

    this.cargarDestinos();

    if (this.enEdicion()) {
      this.paqueteFull.set(this.paqueteFullParametro);
      this.formulario.reset(this.paqueteFull().paquete);
      console.log(this.paqueteFull().paquete.activo);
    }
  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [0], nombre: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(200)]],
      descripcion: ["", [ Validators.minLength(1), Validators.maxLength(300)]],
      precioVenta: [1, [Validators.required, Validators.min(0.01)]],
      duracion: [1, [Validators.required, Validators.min(1)]],
      capacidadMaxima: [1, [Validators.required, Validators.min(1)]],
      id_destino: [null, Validators.required],
      activo: [true, Validators.required]
    });
  }


  public enviar() {
    this.reiniciarBooleanos();

    this.intentoEnviarlo.set(true);

    if (!this.formulario.valid) return;

    const nuevo = this.instanciarPaqueteFull();

    if (this.enEdicion()) {
      this.editar(nuevo);
    } else {
      this.crear(nuevo);
    }
  }

  private instanciarPaqueteFull(): PaqueteGeneral {
    const nuevo: PaqueteGeneral = {
      paquete: this.formulario.value as PaqueteRequest,
      servicios: this.componenteHijo.existenes(),
      nuevosServicios: this.componenteHijo.nuevos()
    };
    return nuevo;
  }


  private crear(nuevo: PaqueteGeneral) {
    this.paquetesService.crear(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/paquetes`]);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }

    });
  }

  private editar(nuevo: PaqueteGeneral) {
    this.paquetesService.editarPaquete(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/paquetes`]);
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


  private cargarDestinos() {
    this.destinosService.obtenerTodos().subscribe({
      next: (todos: DestinoResponse[]) => {
        this.destinos.set(todos);
      },
      error: (errorHttp: any) => {
        this.registrarError(errorHttp);
      }
    });
  }

  private reiniciarBooleanos() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(false);
  }


}
