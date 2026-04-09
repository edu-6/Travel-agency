import { Component, Input } from '@angular/core';
import { ReporteOcupacion } from '../../../modelos/reportes/reporte-ocupacion';

@Component({
  selector: 'app-reporte-ocupacion-destino-component',
  imports: [],
  templateUrl: './reporte-ocupacion-destino-component.html',
  styleUrl: './reporte-ocupacion-destino-component.css',
})
export class ReporteOcupacionDestinoComponent {


  @Input({ required: true})
  reporte !: ReporteOcupacion [];
}
