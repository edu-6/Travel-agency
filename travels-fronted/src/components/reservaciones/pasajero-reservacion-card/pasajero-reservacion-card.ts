import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ClienteResponse } from '../../../modelos/clientes/cliente-response';

@Component({
  selector: 'app-pasajero-reservacion-card',
  imports: [],
  templateUrl: './pasajero-reservacion-card.html',
  styleUrl: './pasajero-reservacion-card.css',
})
export class PasajeroReservacionCard {



  espacio: string = "    ";

  @Input()
  pasajero !: ClienteResponse;


  @Output()
  solicitoEliminacion = new EventEmitter<string>();


  public eliminarAccion() {
    this.solicitoEliminacion.emit(this.pasajero.identificacion);
  }
}
