import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { ProveedorRequest } from "../../modelos/proveedores/ProveedorRequest";
import { ProveedorResponse } from "../../modelos/proveedores/ProveedorResponse";
import { DestinoRequest } from "../../modelos/destinos/destino-request";
import { DestinoResponse } from "../../modelos/destinos/destino-reponse";
import { ClienteRequest } from "../../modelos/clientes/cliente-request";
import { ClienteResponse } from "../../modelos/clientes/cliente-response";
import { ReservacionRequest } from "../../modelos/reservaciones/reservacionRequest";
import { ReservacionResponse } from "../../modelos/reservaciones/reservacion-response";
import { IdReservacion } from "../../modelos/reservaciones/idReservacion";

@Injectable({
  providedIn: 'root'
})


export class ActualizacionReservacionesService {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {
  }

  
  public actualizarEstadoReservaciones(): Observable<void> {
    return this.httpCliente.put<void>(this.constantesRest.getApiURL() + 'api/terminaciones', "nada");
  }


}