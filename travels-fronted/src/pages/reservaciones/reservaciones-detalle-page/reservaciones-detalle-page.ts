import { ImplicitReceiver } from '@angular/compiler';
import { Component, OnInit, signal } from '@angular/core';
import { ReservacionResponse } from '../../../modelos/reservaciones/reservacion-response';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReservacionesService } from '../../../services/login/reservaciones-service';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ReservacionCard } from "../../../components/reservaciones/reservacion-card/reservacion-card";
import { Header } from "../../../shared/header/header";
import { ModalGenerico } from "../../../shared/modal/modal-generico/modal-generico";
import { PagoForm } from "../../../components/reservaciones/pago-form/pago-form";
import { PagoRequest } from '../../../modelos/pagos/pago-request';
import { PagosService } from '../../../services/login/pagos-service';

@Component({
  selector: 'app-reservaciones-detalle-page',
  imports: [ReservacionCard, Header, ModalGenerico, PagoForm],
  templateUrl: './reservaciones-detalle-page.html',
  styleUrl: './reservaciones-detalle-page.css',
})

export class ReservacionesDetallePage implements OnInit {

  hayError = signal<boolean>(false);
  reservacion = signal<ReservacionResponse | null>(null);
  idReservacion !: string;
  mensajeError !: string;
  mensajeCancelacion: string = "Desea cancelar la reservación?";

  encontrado = signal<boolean>(false);
  formularioAbierto = signal<boolean>(false);


  constructor(private router: ActivatedRoute, private reservacionesService: ReservacionesService, private pagosService: PagosService) {

  }

  ngOnInit(): void {
    this.buscarReservacionPorId();
  }




  registrarPago(pago: PagoRequest) {
    console.log(pago);
    this.pagosService.crear(pago).subscribe({
      next: () => {
        this.buscarReservacionPorId();
        this.cerrarFormularioPago();
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }

  cerrarFormularioPago() {
    this.formularioAbierto.set(false);
  }

  abrirFormularioPago() {
    this.formularioAbierto.set(true);
  }




  buscarReservacionPorId() {
    this.idReservacion = this.router.snapshot.params['id'];

    this.reservacionesService.obtenerReservacionId(this.idReservacion).subscribe({
      next: (r: ReservacionResponse) => {
        this.reservacion.set(r);
        this.encontrado.set(true);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }



  registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  cancelarReservacion() {
    alert("se va a cancelar");
  }
}



