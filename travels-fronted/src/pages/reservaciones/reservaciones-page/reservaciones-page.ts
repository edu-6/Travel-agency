import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { RouterLink } from '@angular/router';
import { ReservacionResponse } from '../../../modelos/reservaciones/reservacion-response';
import { ReservacionesService } from '../../../services/login/reservaciones-service';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ReservacionCard } from "../../../components/reservaciones/reservacion-card/reservacion-card";

@Component({
  selector: 'app-reservaciones-page',
  imports: [Header, RouterLink, ReservacionCard],
  templateUrl: './reservaciones-page.html',
  styleUrl: './reservaciones-page.css',
})
export class ReservacionesPage implements OnInit {


  hayError = signal<boolean>(false);
  mensajeError !: string;

  reservacionesHoy = signal<ReservacionResponse[] | null>(null);

  constructor(private reservacionesService: ReservacionesService) {

  }

  ngOnInit(): void {
    this.buscarReservacionesHoy();
  }



  buscarReservacionesHoy() {
    this.reservacionesService.buscarReservacionesHoy().subscribe({
      next: (rs: ReservacionResponse[]) => {
        this.reservacionesHoy.set(rs);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }


  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


}
