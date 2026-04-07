import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ClienteResponse } from '../../../modelos/clientes/cliente-response';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-cliente-card',
  imports: [RouterLink],
  templateUrl: './cliente-card.html',
  styleUrl: './cliente-card.css',
})
export class ClienteCard {

  espacio: string = "    ";

  @Input()
  cliente !: ClienteResponse;


  @Input({ required: true })
  puedeEliminar !: boolean;


  @Input({ required: true })
  origen !: string;


  @Output()
  clienteSeleccionado = new EventEmitter<ClienteResponse>();

  constructor(private router: Router) {

  }


  public eliminarAccion() {
    this.clienteSeleccionado.emit(this.cliente);
    console.log("fnciona el boton");
  }




  redirigirAEditarPage() {
    this.router.navigate(['/clientes/editar-page', this.cliente.identificacion.toString()], { queryParams: { origin: this.origen } });
  }

}
