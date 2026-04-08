import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { UsuarioLoginRequest } from "../../modelos/login/LoginRequest";
import { UsuarioLoginResponse } from "../../modelos/login/LoginResponse";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { ProveedorRequest } from "../../modelos/proveedores/ProveedorRequest";
import { ProveedorResponse } from "../../modelos/proveedores/ProveedorResponse";
import { ReporteGanancias } from "../../modelos/reportes/reporte-ganancia";
import { ReporteRequest } from "../../modelos/reportes/reporte-request";

@Injectable({
  providedIn: 'root'
})


export class ReportesSerivice {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {

  }

  public obtenerReporteGanancias(rep: ReporteRequest): Observable<ReporteGanancias>{
    return this.httpCliente.post<ReporteGanancias>(this.constantesRest.getApiURL()+ 'api/reportes', rep );
  }


}