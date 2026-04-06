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


export class ReservacionesService {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {

  }
  public crear(nuevo: ReservacionRequest): Observable<IdReservacion> {
    return this.httpCliente.post<IdReservacion>(this.constantesRest.getApiURL() + 'api/reservaciones ', nuevo);
  }

  
  public obtenerReservacionId(id: string): Observable<ReservacionResponse> {
    return this.httpCliente.get<ReservacionResponse>(this.constantesRest.getApiURL() + 'api/reservaciones'+ "/"+id);
  }
  


  public buscarReservacionesHoy(): Observable<ReservacionResponse[]> {
    return this.httpCliente.get<ReservacionResponse[]>(this.constantesRest.getApiURL() + 'api/reservaciones/hoy');
  }

  /*


  public buscarParaMostrar(id: string): Observable<ClienteResponse> {
    return this.httpCliente.get<ClienteResponse>( `${this.constantesRest.getApiURL()}api/clientes/${id}`);
  }

  public editarProvedor( edicion: DestinoRequest): Observable<void>{
    return this.httpCliente.put<void>(this.constantesRest.getApiURL()+'api/destinos',edicion);
  }


    public eliminar(nombre: string): Observable<void>{
    return this.httpCliente.delete<void>(`${this.constantesRest.getApiURL()}api/destinos/${nombre}`);
  }

*/

}