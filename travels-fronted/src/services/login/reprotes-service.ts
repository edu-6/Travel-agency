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
import { ReporteAgenteMasVentas } from "../../modelos/reportes/reporte-agente-mas-ventas/reporte-agente-mas-ventas";
import { ReporteAgenteMasGanancias } from "../../modelos/reportes/reporte-agente-ganancia";
import { ReportePaqueteMasVendido } from "../../modelos/reportes/paquete-mas-vendido-reporte/paquete-mas-vendido";
import { ReporteOcupacion } from "../../modelos/reportes/reporte-ocupacion";
import { ReporteVenta } from "../../modelos/reportes/reporte-ventas/reporte-venta";
import { ReporteCancelacion } from "../../modelos/reportes/reporte-cancelacion";

@Injectable({
  providedIn: 'root'
})


export class ReportesSerivice {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {

  }

  public obtenerReporteGanancias(rep: ReporteRequest): Observable<ReporteGanancias> {
    return this.httpCliente.post<ReporteGanancias>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }

  public obtenerReporteAgenteMasVentas(rep: ReporteRequest): Observable<ReporteAgenteMasVentas> {
    return this.httpCliente.post<ReporteAgenteMasVentas>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }


  public obtenerAgenteMasGanancias(rep: ReporteRequest): Observable<ReporteAgenteMasGanancias> {
    return this.httpCliente.post<ReporteAgenteMasGanancias>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }

  public obtenerReportePaqueteMasVendido(rep: ReporteRequest): Observable<ReportePaqueteMasVendido> {
    return this.httpCliente.post<ReportePaqueteMasVendido>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }

  public obtenerReportePaqueteMenosVendido(rep: ReporteRequest): Observable<ReportePaqueteMasVendido> {
    return this.httpCliente.post<ReportePaqueteMasVendido>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }


  public obtenerReporteOcupacion(rep: ReporteRequest): Observable<ReporteOcupacion[]> {
    return this.httpCliente.post<ReporteOcupacion[]>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }


  public obtenerReporteVentas(rep: ReporteRequest): Observable<ReporteVenta[]> {
    return this.httpCliente.post<ReporteVenta[]>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }


  public obtenerReporteCancelaciones(rep: ReporteRequest): Observable<ReporteCancelacion[]> {
    return this.httpCliente.post<ReporteCancelacion[]>(this.constantesRest.getApiURL() + 'api/reportes', rep);
  }

}