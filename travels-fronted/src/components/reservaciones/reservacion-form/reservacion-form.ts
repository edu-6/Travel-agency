import { Component, Input, OnInit, signal, ViewChild } from '@angular/core';
import { PaqueteGeneral } from '../../../modelos/paquetes/paquete-full';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DestinoResponse } from '../../../modelos/destinos/destino-reponse';
import { Pais } from '../../../modelos/enums/pais-enum';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { PaqueteRequest } from '../../../modelos/paquetes/paquete-request';
import { DestinosService } from '../../../services/login/destinos-service';
import { EnumsService } from '../../../services/login/enums-service';
import { PaquetesService } from '../../../services/login/paquetes-service';
import { PaqueteServicioForm } from '../../paquetes/paquetes-servicios/paquete-servicio-form/paquete-servicio-form';
import { PasajeroReservacionComponent } from "../pasajero-reservacion-component/pasajero-reservacion-component";
import { PaqueteResponse } from '../../../modelos/paquetes/paquete-response';

@Component({
  selector: 'app-reservacion-form',
  imports: [ReactiveFormsModule, PasajeroReservacionComponent],
  templateUrl: './reservacion-form.html',
  styleUrl: './reservacion-form.css',
})
export class ReservacionForm implements OnInit {



  @Input()
  paqueteFullParametro !: PaqueteGeneral;

  paqueteFull = signal<PaqueteGeneral>({} as PaqueteGeneral);

  @ViewChild(PaqueteServicioForm) componenteHijo!: PaqueteServicioForm;



  formulario !: FormGroup;
  mensajeError !: string;


  paquetes = signal<PaqueteResponse[]>([]);

  paqueteSeleccionado = signal<PaqueteResponse | null>(null);



  enEdicion = signal<boolean>(false);
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
    this.cargarPaquetes();
    this.enEdicion.set(this.paqueteFullParametro != null);

    

  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      id_titular: [],
      fechaViaje: [new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0], Validators.required],
      id_paquete: [null, Validators.required]
    });
  }


  public enviar() {
    this.reiniciarBooleanos();

    this.intentoEnviarlo.set(true);

    if (!this.formulario.valid) return;

    const nuevo = this.instanciarPaqueteFull();

    this.guardarReservacion();
  }

  private instanciarPaqueteFull(): PaqueteGeneral {
    const nuevo: PaqueteGeneral = {
      paquete: this.formulario.value as PaqueteRequest,
      servicios: this.componenteHijo.existenes(),
      nuevosServicios: this.componenteHijo.nuevos()
    };
    return nuevo;
  }


  guardarPaqueteSeleccionado(event: any) {
    const idSeleccionado = event.target.value;
    const paquete = this.paquetes().find(p => p.id == idSeleccionado);
    this.paqueteSeleccionado.set(paquete || null);
  }


  private guardarReservacion() {
    /*
    this.paquetesService.crear(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/paquetes`]);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }

    });*/
  }


  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  private cargarPaquetes() {
    this.paquetesService.buscarPaquetesActivos().subscribe({
      next: (todos: PaqueteResponse[]) => {
        this.paquetes.set(todos);

        this.formulario.patchValue({
          id_paquete: todos[0].id,
        });

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
