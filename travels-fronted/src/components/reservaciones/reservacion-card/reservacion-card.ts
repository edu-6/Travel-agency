import { Component, Input } from '@angular/core';
import { ReservacionResponse } from '../../../modelos/reservaciones/reservacion-response';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-reservacion-card',
  imports: [RouterLink],
  templateUrl: './reservacion-card.html',
  styleUrl: './reservacion-card.css',
})
export class ReservacionCard {


  @Input({required: true})
  reservacion !: ReservacionResponse;



  @Input({required: true})
  mostrarBtnDetalles !: boolean;



}
