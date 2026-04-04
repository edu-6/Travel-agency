import { Component, Input, OnInit, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { PaqueteServicio } from '../../../modelos/proveedor-servicio/paquete-servicio';
import { ProveedorResponse } from '../../../modelos/proveedores/ProveedorResponse';
import { PaquetesService } from '../../../services/login/paquetes-service';
import { ProveedoresService } from '../../../services/login/proveedores-service';
import { ClienteResponse } from '../../../modelos/clientes/cliente-response';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ClientesService } from '../../../services/login/clientes-service';

@Component({
  selector: 'app-pasajero-reservacion-component',
  imports: [ReactiveFormsModule],
  templateUrl: './pasajero-reservacion-component.html',
  styleUrl: './pasajero-reservacion-component.css',
})
export class PasajeroReservacionComponent {


  formulario !: FormGroup;

  enEdicion = signal<boolean>(false);
  mensajeError: string = "";
  hayError = signal<boolean>(false);

  pasajeros = signal<ClienteResponse[]>([]);

  pasajerosIds: string[] = [];

  @Input()
  existenesParametro !: PaqueteServicio[];


  constructor(private clientesService: ClientesService) {

  }



  agregarPasajero(id: string) {
    this.hayError.set(false);

    if (this.usuarioRepetido(id)) {
      this.hayError.set(true);
      this.mensajeError = "El pasajero ya fué añadido!";
      return;
    }


    this.clientesService.buscarParaMostrar(id).subscribe({
      next: (cliente: ClienteResponse) => {
        this.pasajeros.update(listaActual => [...listaActual, cliente]);
        this.pasajerosIds.push(id);
      },
      error: (errorhttp: any) => {
        this.registrarError(errorhttp);
      }
    });
  }


  usuarioRepetido(id: string): boolean {
    return this.pasajerosIds.includes(id);
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }



  eliminarSeleccionado(seleccionado: PaqueteServicio) {
    /*
    let id = seleccionado.id;
    let existeEnDB = id > 0;
    let indice;
    if (existeEnDB) {
      this.existenes.update(lista => lista.filter(p => p.id !== seleccionado.id));
      
      this.eliminarServicioEnPaquete(seleccionado.id.toString());
    } else {
      this.nuevos.update(lista => lista.filter(p => p.id !== seleccionado.id));
    }*/
  }
}
