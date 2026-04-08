import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReporteRequest } from '../../../modelos/reportes/reporte-request';
import { ReportesSerivice } from '../../../services/login/reprotes-service';
import { ReporteGanancias } from '../../../modelos/reportes/reporte-ganancia';

@Component({
  selector: 'app-reportes-page',
  imports: [Header, ReactiveFormsModule],
  templateUrl: './reportes-page.html',
  styleUrl: './reportes-page.css',
})
export class ReportesPage  implements OnInit{


  hayError = signal<boolean>(false);

  mensajeError !: string;

  public listaReportes: string[] = [
    "Reporte de ventas en un intervalo de tiempo",
    "Reporte de cancelaciones en un intervalo de tiempo",
    "Reporte de ganancias en un intervalo de tiempo",
    "Reporte del agente con más ventas",
    "Reporte del agente con más ganancias",
    "Reporte del paquete más vendido",
    "Reporte del paquete menos vendido",
    "Reporte de ocupación por destino"
  ];


  formulario !: FormGroup;


  constructor(private formBuiler: FormBuilder, private reportesService: ReportesSerivice){

  }

  generarReporte(tipoReporte: string) {
     const reporteRequest =this.formulario.value as ReporteRequest;
     console.log(reporteRequest);

    this.reportesService.obtenerReporteGanancias(reporteRequest).subscribe({

      next:(rep: ReporteGanancias)=>{
        console.log(rep);
      },
      error:()=>{

      }

    });     


  }

  ngOnInit(): void {
    this.instanciarFormulario();
  }


  instanciarFormulario(){
    this.formulario = this.formBuiler.group({
      tipoReporte: [this.listaReportes[0], Validators.required],
      fechaInicio:[null],
      fechaFinal: [null]
    });
  }



}
