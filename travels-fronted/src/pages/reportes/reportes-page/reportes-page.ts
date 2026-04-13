import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReporteRequest } from '../../../modelos/reportes/reporte-request';
import { ReportesSerivice } from '../../../services/login/reprotes-service';
import { ReporteGanancias } from '../../../modelos/reportes/reporte-ganancia';
import { ReporteGananciasComponent } from "../../../components/reportes/reporte-ganancias/reporte-ganancias";
import { ReporteAgenteMasVentas } from '../../../modelos/reportes/reporte-agente-mas-ventas/reporte-agente-mas-ventas';
import { ReporteAgenteVentas } from "../../../components/reportes/reporte-agente-ventas/reporte-agente-ventas";
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { SignalFormControl } from '@angular/forms/signals/compat';
import { ReporteAgenteMasGanancias } from '../../../modelos/reportes/reporte-agente-ganancia';
import { ReporteAgenteGanancias } from "../../../components/reportes/reporte-agente-ganancias/reporte-agente-ganancias";
import { ReportePaqueteMasVendido } from '../../../modelos/reportes/paquete-mas-vendido-reporte/paquete-mas-vendido';
import { ReportePaqueteMasVendidoComponent } from "../../../components/reportes/reporte-paquete-mas-vendido-component/reporte-paquete-mas-vendido-component";
import { ReporteOcupacion } from '../../../modelos/reportes/reporte-ocupacion';
import { ReporteOcupacionDestinoComponent } from "../../../components/reportes/reporte-ocupacion-destino-component/reporte-ocupacion-destino-component";
import { ReporteVenta } from '../../../modelos/reportes/reporte-ventas/reporte-venta';
import { ReporteVentasComponent } from "../../../components/reportes/reporte-ventas-component/reporte-ventas-component";
import { ReporteCancelacion } from '../../../modelos/reportes/reporte-cancelacion';
import { ReporteCancelacionesComponent } from "../../../components/reportes/reporte-cancelaciones-component/reporte-cancelaciones-component";

@Component({
  selector: 'app-reportes-page',
  imports: [Header, ReactiveFormsModule, ReporteGananciasComponent, ReporteAgenteVentas, ReporteAgenteGanancias, ReportePaqueteMasVendidoComponent, ReporteOcupacionDestinoComponent, ReporteVentasComponent, ReporteCancelacionesComponent],
  templateUrl: './reportes-page.html',
  styleUrl: './reportes-page.css',
})
export class ReportesPage implements OnInit {

  formulario !: FormGroup;

  hayError = signal<boolean>(false);
  reporteEncontrado = signal<boolean>(false);
  reporteActual = signal<string | null>(null);

  reporteGanancias = signal<ReporteGanancias | null>(null);
  reporteAgenteMasVentas = signal<ReporteAgenteMasVentas | null>(null);
  reproteAgenteMasGanancias = signal<ReporteAgenteMasGanancias | null>(null);
  reportePaqueteMasVendido = signal<ReportePaqueteMasVendido | null>(null);
  reportePaqueteMenosVendido = signal<ReportePaqueteMasVendido | null>(null);
  reporteOcupacionDestino = signal<ReporteOcupacion[] | null>(null);
  reporteVentas = signal<ReporteVenta[] | null>(null);
  reporteCancelaciones = signal<ReporteCancelacion[]>([]);



  mensajeError !: string;

  public listaReportes: string[] = [
    "Reporte de ventas",
    "Reporte de cancelaciones",
    "Reporte de ganancias",
    "Reporte del agente con mas ventas",
    "Reporte del agente con mas ganancias",
    "Reporte del paquete mas vendido",
    "Reporte del paquete menos vendido",
    "Reporte de ocupación por destino",
  ];

  constructor(private formBuiler: FormBuilder, private reportesService: ReportesSerivice) {

  }

  ngOnInit(): void {
    this.instanciarFormulario();
  }

  instanciarFormulario() {
    this.formulario = this.formBuiler.group({
      tipoReporte: [this.listaReportes[0], Validators.required],
      fechaInicio: [null],
      fechaFinal: [null]
    });
  }


  resetearFormulario(tipoReporte: string) {
    this.formulario = this.formBuiler.group({
      tipoReporte: [tipoReporte, Validators.required],
      fechaInicio: [null],
      fechaFinal: [null]
    });
  }


  public reporteActivo(tipo: string): boolean {
    return tipo === this.reporteActual();
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  generarReporte(tipoReporte: string) {
    this.hayError.set(false);
    this.reporteEncontrado.set(false);
    this.reporteActual.set(tipoReporte);
    const reporteRequest = this.formulario.value as ReporteRequest;

    switch (tipoReporte) {

      case "Reporte de ventas":
        this.generarReporteVentas(reporteRequest);
        this.reporteVentas.set(null);
        break;

      case "Reporte de cancelaciones":
        this.reporteCancelaciones.set([]);
        this.generarReporteCancelaciones(reporteRequest);
        break;

      case "Reporte de ganancias":
        this.generarReporteGanancias(reporteRequest);
        this.reporteGanancias.set(null);
        break;

      case "Reporte del agente con mas ventas":
        this.reporteAgenteMasVentas.set(null);
        this.generarAgenteMasVentas(reporteRequest);
        break;
      case "Reporte del agente con mas ganancias":
        this.reproteAgenteMasGanancias.set(null);
        this.generarAgenteMasGanancias(reporteRequest);
        break;

      case "Reporte del paquete mas vendido":
        this.reportePaqueteMasVendido.set(null);
        this.generarPaqueteMasVendido(reporteRequest);
        break;

      case "Reporte del paquete menos vendido":
        this.reportePaqueteMenosVendido.set(null);
        this.generarPaquetMenosVendido(reporteRequest);
        break;

      case "Reporte de ocupación por destino":
        this.reporteOcupacionDestino.set(null);
        this.generarReporteOcupacion(reporteRequest);
        break;
    }
  }

  generarReporteGanancias(request: ReporteRequest) {
    this.reportesService.obtenerReporteGanancias(request).subscribe({
      next: (rep: ReporteGanancias) => {
        
        this.reporteGanancias.set(rep);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }


  generarAgenteMasVentas(request: ReporteRequest) {
    this.reportesService.obtenerReporteAgenteMasVentas(request).subscribe({
      next: (rep: ReporteAgenteMasVentas) => {
        this.reporteAgenteMasVentas.set(rep);
        
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  generarAgenteMasGanancias(request: ReporteRequest) {
    this.reportesService.obtenerAgenteMasGanancias(request).subscribe({
      next: (rep: ReporteAgenteMasGanancias) => {
        this.reproteAgenteMasGanancias.set(rep);
        
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  generarPaqueteMasVendido(request: ReporteRequest) {
    this.reportesService.obtenerReportePaqueteMasVendido(request).subscribe({
      next: (rep: ReportePaqueteMasVendido) => {
        if (rep) {
          this.reportePaqueteMasVendido.set(rep);
        }
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }


  generarPaquetMenosVendido(request: ReporteRequest) {
    this.reportesService.obtenerReportePaqueteMenosVendido(request).subscribe({
      next: (rep: ReportePaqueteMasVendido) => {
        if (rep) {
          this.reportePaqueteMenosVendido.set(rep);
        }
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  generarReporteOcupacion(request: ReporteRequest) {
    this.reportesService.obtenerReporteOcupacion(request).subscribe({
      next: (res: ReporteOcupacion[]) => {
        this.reporteOcupacionDestino.set(res);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  generarReporteVentas(request: ReporteRequest) {
    this.reportesService.obtenerReporteVentas(request).subscribe({
      next: (res: ReporteVenta[]) => {
        this.reporteVentas.set(res);
        
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }


  generarReporteCancelaciones(request: ReporteRequest) {
    this.reportesService.obtenerReporteCancelaciones(request).subscribe({
      next: (data: ReporteCancelacion[]) =>{
        this.reporteCancelaciones.set(data);
      },
      error: (err: any) =>{
        this.registrarError(err)
      } 
    });
  }


}



