import { Component, Input } from '@angular/core';
import { ReporteVenta } from '../../../modelos/reportes/reporte-ventas/reporte-venta';

@Component({
  selector: 'app-reporte-ventas-component',
  imports: [],
  templateUrl: './reporte-ventas-component.html',
  styleUrl: './reporte-ventas-component.css',
})
export class ReporteVentasComponent {

  @Input({required: true})
  reporteVentas !: ReporteVenta [];
}
