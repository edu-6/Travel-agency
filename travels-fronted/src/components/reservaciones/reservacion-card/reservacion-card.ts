import { Component, Input } from '@angular/core';
import { ReservacionResponse } from '../../../modelos/reservaciones/reservacion-response';
import { RouterLink } from '@angular/router';
import { PdfsServicio } from '../../../services/login/pdfs-servicio';

@Component({
  selector: 'app-reservacion-card',
  imports: [RouterLink],
  templateUrl: './reservacion-card.html',
  styleUrl: './reservacion-card.css',
})
export class ReservacionCard {


  constructor(private pdfsService: PdfsServicio) {

  }


  @Input({ required: true })
  reservacion !: ReservacionResponse;



  @Input({ required: true })
  mostrarBtnDetalles !: boolean;



  descargarComprobante() {
    this.pdfsService.descargarComprobante(this.reservacion.id).subscribe({
      next: (archivo: Blob) => {

        const blob = new Blob([archivo], { type: 'application/pdf' });

        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `comprobante-reserva-${this.reservacion.id}.pdf`;

        document.body.appendChild(a);
        a.click();

        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        console.error("No se pudo descargar el PDF", err);
      }
    });
  }
  
}
