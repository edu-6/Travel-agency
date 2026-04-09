import { Component, Input } from '@angular/core';
import { ReporteAgenteMasGanancias } from '../../../modelos/reportes/reporte-agente-ganancia';

@Component({
  selector: 'app-reporte-agente-ganancias',
  imports: [],
  templateUrl: './reporte-agente-ganancias.html',
  styleUrl: './reporte-agente-ganancias.css',
})
export class ReporteAgenteGanancias {

 @Input({required: true})
  reporte !: ReporteAgenteMasGanancias;
}
