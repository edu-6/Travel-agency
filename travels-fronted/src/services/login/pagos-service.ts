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
import { PagoRequest } from "../../modelos/pagos/pago-request";
import { PagoResponse } from "../../modelos/pagos/pago-response";
import { IdReservacion } from "../../modelos/reservaciones/idReservacion";

@Injectable({
  providedIn: 'root'
})


export class PagosService {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {

  }
  public crear(nuevo: PagoRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/pagos', nuevo);
  }

  public obtenerPagosReservacion(id: string): Observable<PagoResponse[]> {
    return this.httpCliente.get<PagoResponse []>( `${this.constantesRest.getApiURL()}api/pagos/${id}`);
  }


  


  /*
  public obtenerTodos(): Observable<DestinoResponse[]> {
    return this.httpCliente.get<DestinoResponse[]>(this.constantesRest.getApiURL() + 'api/destinos');
  }


  public buscarParaEditar(id: string): Observable<ClienteRequest> {
    return this.httpCliente.get<ClienteRequest>(this.constantesRest.getApiURL() + 'api/clientes' + '/' + id);
  }


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