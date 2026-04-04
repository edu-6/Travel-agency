import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { ProveedorRequest } from "../../modelos/proveedores/ProveedorRequest";
import { ProveedorResponse } from "../../modelos/proveedores/ProveedorResponse";
import { DestinoRequest } from "../../modelos/destinos/destino-request";
import { DestinoResponse } from "../../modelos/destinos/destino-reponse";

@Injectable({
  providedIn: 'root'
})


export class DestinosService {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {

  }
  public crear(nuevo: DestinoRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/destinos', nuevo);
  }

  public obtenerTodos(): Observable<DestinoResponse[]> {
    return this.httpCliente.get<DestinoResponse[]>(this.constantesRest.getApiURL() + 'api/destinos');
  }


  public buscarParaEditar(nombre: string): Observable<DestinoRequest> {
    return this.httpCliente.get<DestinoRequest>(this.constantesRest.getApiURL() + 'api/destinos' + '/' + nombre);
  }


  public buscarParaMostrar(nombre: string): Observable<DestinoResponse> {
    return this.httpCliente.get<DestinoResponse>( `${this.constantesRest.getApiURL()}api/destinos/${nombre}`);
  }

  public editarProvedor( edicion: DestinoRequest): Observable<void>{
    return this.httpCliente.put<void>(this.constantesRest.getApiURL()+'api/destinos',edicion);
  }


    public eliminar(nombre: string): Observable<void>{
    return this.httpCliente.delete<void>(`${this.constantesRest.getApiURL()}api/destinos/${nombre}`);
  }



}