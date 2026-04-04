import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { DestinosService } from '../../../services/login/destinos-service';
import { DestinoResponse } from '../../../modelos/destinos/destino-reponse';
import { PaqueteResponse } from '../../../modelos/paquetes/paquete-response';
import { PaqueteServicio } from '../../../modelos/proveedor-servicio/paquete-servicio';
import { PaquetesService } from '../../../services/login/paquetes-service';
import { PaqueteCard } from "../../../components/paquetes/paquete-card/paquete-card";

@Component({
  selector: 'app-paquetes-page',
  imports: [Header, ReactiveFormsModule, RouterLink, PaqueteCard],
  templateUrl: './paquetes-page.html',
  styleUrl: './paquetes-page.css',
})
export class PaquetesPage implements OnInit {

  public barraBusqueda !: FormGroup;
  mensajeError !: string;


  hayError: boolean = false;
  buscandoUno = signal<boolean>(false);
  busquedaPorDestino = signal<boolean>(true);

  destinos = signal<DestinoResponse[]>([]);

  paquetes = signal<PaqueteResponse[]>([]);

  constructor(private formBuilder: FormBuilder, private destinosService: DestinosService,private paquetesService: PaquetesService)
  {

  }


  ngOnInit(): void {
    this.cargarDestinos();
    this.barraBusqueda = this.formBuilder.group(
      {
        busqueda: ["", [Validators.required]]
      }
    );
  }



  buscarPorDestino(idDestino: string) {

    if(!idDestino) return;
    
     this.paquetesService.buscarPorDestino(idDestino).subscribe({
      next:(lista: PaqueteResponse[]) =>{
        this.paquetes.set(lista);
      },
      error:(httpError: any)=>{
        this.registrarError(httpError);
      }
     });
  }


  registrarError(httpError: any) {
    this.hayError = true;
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }

  public cargarDestinos() {
    this.destinosService.obtenerTodos().subscribe({
      next: (todos: DestinoResponse[]) => {
        this.destinos.set(todos);
         this.buscarPorDestino(this.destinos()[0].id.toString());
      },
      error: (errorHtttp: any) => {
        this.registrarError(errorHtttp);
      }

    });
  }
}
