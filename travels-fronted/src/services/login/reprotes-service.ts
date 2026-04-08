import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { UsuarioLoginRequest } from "../../modelos/login/LoginRequest";
import { UsuarioLoginResponse } from "../../modelos/login/LoginResponse";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { ProveedorRequest } from "../../modelos/proveedores/ProveedorRequest";
import { ProveedorResponse } from "../../modelos/proveedores/ProveedorResponse";
import { ReporteGanancias } from "../../modelos/reportes/reporte-ganancia";

@Injectable({
  providedIn: 'root'
})


export class ReportesSerivice {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {

  }

  public obtenerReporteGanancias(): Observable<ReporteGanancias>{
    return this.httpCliente.get<ReporteGanancias>(this.constantesRest.getApiURL()+ 'api/reportes');
  }


}