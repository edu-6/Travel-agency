import { Component, Input, OnInit, signal, ViewChild } from '@angular/core';
import { PaqueteGeneral } from '../../../modelos/paquetes/paquete-full';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { PaqueteRequest } from '../../../modelos/paquetes/paquete-request';
import { PaquetesService } from '../../../services/login/paquetes-service';
import { PaqueteServicioForm } from '../../paquetes/paquetes-servicios/paquete-servicio-form/paquete-servicio-form';
import { PasajeroReservacionComponent } from "../pasajero-reservacion-component/pasajero-reservacion-component";
import { PaqueteResponse } from '../../../modelos/paquetes/paquete-response';
import { ReservacionRequest } from '../../../modelos/reservaciones/reservacionRequest';
import { ReservacionesService } from '../../../services/login/reservaciones-service';
import { IdReservacion } from '../../../modelos/reservaciones/idReservacion';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reservacion-form',
  imports: [ReactiveFormsModule, PasajeroReservacionComponent],
  templateUrl: './reservacion-form.html',
  styleUrl: './reservacion-form.css',
})
export class ReservacionForm implements OnInit {


  @Input({ required: true })
  idTitular !: string;



  @Input()
  paqueteFullParametro !: PaqueteGeneral;

  paqueteFull = signal<PaqueteGeneral>({} as PaqueteGeneral);

  @ViewChild(PasajeroReservacionComponent) componenteHijo!: PasajeroReservacionComponent;



  formulario !: FormGroup;
  mensajeError = signal<string | null>(null); 


  paquetes = signal<PaqueteResponse[]>([]);

  paqueteSeleccionado = signal<PaqueteResponse | null>(null);



  hayError = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);

  constructor(private formBuilder: FormBuilder,
     private paquetesService: PaquetesService,
      private reservacionesService: ReservacionesService, private router: Router) {

  }


  ngOnInit(): void {
    this.instanciarFormulario();
    this.cargarPaquetes();


  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      idTitular: [null, Validators.required],
      fechaViaje: [new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0], Validators.required],
      idPaquete: [null, Validators.required],
      idAgenteCreador: [null, Validators.required]
    });

    this.formulario.patchValue({
      idTitular: this.idTitular,
      idAgenteCreador: localStorage.getItem('id'),
    }
    );
  }




  public enviar() {
    this.reiniciarBooleanos();

    this.intentoEnviarlo.set(true);

    if (!this.formulario.valid) return;

    const nuevo = this.instanciarReservacion();
    this.guardarReservacion(nuevo);
  }

  private instanciarReservacion(): ReservacionRequest {
    const formValues = this.formulario.value;
    const fechaLimpia = formValues.fechaViaje.split('T')[0];

    const nuevo: ReservacionRequest = {
      idTitular: formValues.idTitular,
      fechaViaje: fechaLimpia,
      idPaquete: Number(formValues.idPaquete),
      idAgenteCreador: formValues.idAgenteCreador,
      pasajeros: this.componenteHijo.pasajerosIds
    };

    return nuevo;
  }


  guardarPaqueteSeleccionado(event: any) {
    const idSeleccionado = event.target.value;
    const paquete = this.paquetes().find(p => p.id == idSeleccionado);
    this.paqueteSeleccionado.set(paquete || null);
  }


  private guardarReservacion(nuevo: ReservacionRequest) {
    this.reservacionesService.crear(nuevo).subscribe({
      next:(id: IdReservacion)=>{
        console.log(" se creóoooooo");
        this.router.navigate(['/reservaciones']);
      },
      error: (httpError: any) =>{
        this.registrarError(httpError);
      }

    });

  }


  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError.set(errorData.detalles);
  }


  private cargarPaquetes() {
    this.paquetesService.buscarPaquetesActivos().subscribe({
      next: (todos: PaqueteResponse[]) => {
        this.paquetes.set(todos);

        this.formulario.patchValue({
          idPaquete: todos[0].id,
        });

      },
      error: (errorHttp: any) => {
        this.registrarError(errorHttp);
      }
    });
  }

  private reiniciarBooleanos() {
    this.mensajeError.set(null);
    this.hayError.set(false);
    this.intentoEnviarlo.set(false);
  }

}
