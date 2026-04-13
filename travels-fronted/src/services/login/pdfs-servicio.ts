import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { ProveedorRequest } from "../../modelos/proveedores/ProveedorRequest";
import { ProveedorResponse } from "../../modelos/proveedores/ProveedorResponse";
import { DestinoRequest } from "../../modelos/destinos/destino-request";
import { DestinoResponse } from "../../modelos/destinos/destino-reponse";
import { PaqueteGeneral } from "../../modelos/paquetes/paquete-full";
import { PaqueteResponse } from "../../modelos/paquetes/paquete-response";

@Injectable({
  providedIn: 'root'
})


export class PdfsServicio {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {
    
  }
  
  public descargarComprobante(idReservacion: number): Observable<Blob> {
    return this.httpCliente.get(`${this.constantesRest.getApiURL()}api/comprobante/${idReservacion}`, { responseType: 'blob' }
    );
  }





}