import { Component, Input } from '@angular/core';
import { ClienteResponse } from '../../../modelos/clientes/cliente-response';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cliente-card',
  imports: [RouterLink],
  templateUrl: './cliente-card.html',
  styleUrl: './cliente-card.css',
})
export class ClienteCard {

  espacio : string = "    ";

  @Input()
  cliente !: ClienteResponse;


  public eliminarAccion(){

    
  }

}
