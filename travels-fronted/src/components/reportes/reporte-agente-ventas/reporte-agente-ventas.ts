import { Component, Input } from '@angular/core';
import { ReporteAgenteMasVentas } from '../../../modelos/reportes/reporte-agente-mas-ventas/reporte-agente-mas-ventas';

@Component({
  selector: 'app-reporte-agente-ventas',
  imports: [],
  templateUrl: './reporte-agente-ventas.html',
  styleUrl: './reporte-agente-ventas.css',
})
export class ReporteAgenteVentas {
@Input({ required: true })
  reporte!: ReporteAgenteMasVentas;

}
