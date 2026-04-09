import { Component, Input } from '@angular/core';
import { ReportePaqueteMasVendido } from '../../../modelos/reportes/paquete-mas-vendido-reporte/paquete-mas-vendido';

@Component({
  selector: 'app-reporte-paquete-mas-vendido-component',
  imports: [],
  templateUrl: './reporte-paquete-mas-vendido-component.html',
  styleUrl: './reporte-paquete-mas-vendido-component.css',
})
export class ReportePaqueteMasVendidoComponent {

  @Input({required: true})
  reporte !: ReportePaqueteMasVendido;


  @Input({required: true})
  masVendido !: boolean;
}
