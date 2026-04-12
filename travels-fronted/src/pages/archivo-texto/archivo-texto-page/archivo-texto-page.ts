import { HttpClient } from '@angular/common/http';
import { Component, signal } from '@angular/core';
import { ConstantesRest } from '../../../services/login/restConstantes';
import { Observable } from 'rxjs';
import { ErrorBackend } from '../../../modelos/ErrorBackend';

@Component({
  selector: 'app-archivo-texto-page',
  imports: [],
  templateUrl: './archivo-texto-page.html',
  styleUrl: './archivo-texto-page.css',
})
export class ArchivoTextoPage {

  constantesRest = new ConstantesRest();
  archivoSeleccionado: File | null = null;

  logs = signal<string [] | null>(null);

  constructor(private httpCliente: HttpClient) {

  }

  alSeleccionarArchivo(event: any) {
    const archivo = event.target.files[0];

    if (archivo) {
      if (archivo.type === 'text/plain' || archivo.name.endsWith('.txt')) {
        this.archivoSeleccionado = archivo;
        this.logs.set(null);
      } else {
        alert("Error: Solo se permiten archivos de texto (.txt)");
        this.archivoSeleccionado = null;
        event.target.value = '';
      }
    }
  }

  subir() {
    if (!this.archivoSeleccionado) {
      alert("Por favor, selecciona un archivo primero.");
      return;
    }


    const formData = new FormData();
    formData.append('file', this.archivoSeleccionado);

    this.procesarArchivoTexto(formData).subscribe({
      next: (res: string[]) => {
        this.logs.set(null);
        this.logs.set(res);
      },
      error: (err: any) => {
        const errorData: ErrorBackend = err.error;
                alert(errorData.detalles);
        console.error("Error en el servidor", err);
        alert("Hubo un error al subir el archivo");
      }
    })
  }



  public procesarArchivoTexto(formData: FormData): Observable<string[]> {
    return this.httpCliente.post<string[]>(this.constantesRest.getApiURL() + 'archivo-texto', formData);
  }


}
