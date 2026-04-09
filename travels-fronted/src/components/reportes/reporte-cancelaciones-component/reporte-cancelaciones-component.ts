import { Component, Input } from '@angular/core';
import { ReporteCancelacion } from '../../../modelos/reportes/reporte-cancelacion';

@Component({
  selector: 'app-reporte-cancelaciones-component',
  imports: [],
  templateUrl: './reporte-cancelaciones-component.html',
  styleUrl: './reporte-cancelaciones-component.css',
})
export class ReporteCancelacionesComponent {


  @Input({required: true})
  reporteCancelaciones!: ReporteCancelacion[];
}
