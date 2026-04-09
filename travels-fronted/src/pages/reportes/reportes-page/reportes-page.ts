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

@Component({
  selector: 'app-reportes-page',
  imports: [Header, ReactiveFormsModule, ReporteGananciasComponent, ReporteAgenteVentas],
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
  mensajeError !: string;

  public listaReportes: string[] = [
    "Reporte de ganancias",
    "Reporte del agente con mas ventas",
    "Reporte del agente con mas ganancias",
    "Reporte del agente con más ventas",
    "Reporte del agente con más ganancias",
    "Reporte del paquete más vendido",
    "Reporte del paquete menos vendido",
    "Reporte de ocupación por destino"
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


  resetearFormulario(tipoReporte: string){
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
      case "Reporte de ganancias":
        this.generarReporteGanancias(reporteRequest);
        this.reporteGanancias.set(null);
        break;

      case "Reporte del agente con mas ventas":
        this.reporteAgenteMasVentas.set(null);
        this.generarAgenteMasVentas(reporteRequest);
        break;
    }
  }

  generarReporteGanancias(request: ReporteRequest) {
    this.reportesService.obtenerReporteGanancias(request).subscribe({
      next: (rep: ReporteGanancias) => {
        console.log(rep);
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
        console.log(rep);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }




}
