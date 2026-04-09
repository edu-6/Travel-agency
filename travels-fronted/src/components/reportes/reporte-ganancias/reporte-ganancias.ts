import { Component, Input } from '@angular/core';
import { ReporteGanancias } from '../../../modelos/reportes/reporte-ganancia';

@Component({
  selector: 'app-reporte-ganancias',
  imports: [],
  templateUrl: './reporte-ganancias.html',
  styleUrl: './reporte-ganancias.css',
})
export class ReporteGananciasComponent {

 @Input({required: true})
 reporte !: ReporteGanancias;

}
