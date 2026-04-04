import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DestinosService } from '../../../services/login/destinos-service';
import { DestinoResponse } from '../../../modelos/destinos/destino-reponse';
import { ProveedorResponse } from '../../../modelos/proveedores/ProveedorResponse';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { DestinoCard } from "../../../components/destinos/destino-card/destino-card";
import { ModalGenerico } from "../../../shared/modal/modal-generico/modal-generico";

@Component({
  selector: 'app-destinos-page',
  imports: [Header, ReactiveFormsModule, RouterLink, DestinoCard, ModalGenerico],
  templateUrl: './destinos-page.html',
  styleUrl: './destinos-page.css',
})
export class DestinosPage implements OnInit {

  barraBusqueda !: FormGroup;
  destinos = signal<DestinoResponse[]>([]);

  destinoEncontrado !: DestinoResponse;

  destinoSeleccionado !: DestinoResponse;
  mensajeEleiminacion !: string;



  mensajeError !: string;

  hayError: boolean = false;
  buscandoTodos = signal<boolean>(false);
  buscandoUno = signal<boolean>(false);

  constructor(private formBuilder: FormBuilder, private destinosService: DestinosService) {

  }

  ngOnInit(): void {
    this.instanciarBarraBusqueda();
    this.buscarTodos();
  }



  private instanciarBarraBusqueda() {
    this.barraBusqueda = this.formBuilder.group(
      {
        busqueda: ["", [Validators.required]]
      }
    );
  }



  guardarDestinoSeleccionado(destino: DestinoResponse): void {
    console.log("guarando el destino");
    this.destinoSeleccionado = destino;
    this.mensajeEleiminacion = " Se eliminará a " + destino.nombre + "  desea continuar?";
  }


  eliminarRegistro() {
    this.destinosService.eliminar(this.destinoSeleccionado.id.toString()).subscribe({
      next: () => {
        this.buscarTodos();
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }


  public buscarDestino() {
    if (!this.barraBusqueda.valid) return;

    this.buscandoTodos.set(false);
    this.destinosService.buscarParaMostrar(this.barraBusqueda.get("busqueda")?.value).subscribe({
      next: (destino: DestinoResponse) => {
        this.destinoEncontrado = destino;
        this.buscandoUno.set(true);
      },
      error: (errorHtttp: any) => {
        this.registrarError(errorHtttp);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError = true;
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }

  public buscarTodos() {
    this.destinosService.obtenerTodos().subscribe({
      next: (todos: DestinoResponse[]) => {
        this.destinos.set(todos);
        this.buscandoUno.set(false);
        this.buscandoTodos.set(true);
      },
      error: (errorHtttp: any) => {
        this.registrarError(errorHtttp);
      }

    });
  }

}


