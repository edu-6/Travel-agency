import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteResponse } from '../../../modelos/clientes/cliente-response';
import { ClientesService } from '../../../services/login/clientes-service';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ReservacionResponse } from '../../../modelos/reservaciones/reservacion-response';
import { ClienteCard } from "../../../components/clientes/cliente-card/cliente-card";
import { ReservacionCard } from "../../../components/reservaciones/reservacion-card/reservacion-card";
import { ReservacionesService } from '../../../services/login/reservaciones-service';

@Component({
  selector: 'app-clientes-detalle-page',
  imports: [Header, ClienteCard, ReservacionCard],
  templateUrl: './clientes-detalle-page.html',
  styleUrl: './clientes-detalle-page.css',
})
export class ClientesDetallePage implements OnInit {

  hayError = signal<boolean>(false);
  clienteEncontrado = signal<ClienteResponse | null>(null);
  idCliente  !: string;

  mensajeError !: string;
  origen : string = "perfil-usuario";

  reservaciones = signal<ReservacionResponse[] | null>(null);



  constructor(private routerParam: ActivatedRoute, private router:Router,
    private clientesService: ClientesService, private reservacionesService: ReservacionesService) {
    
  }


  ngOnInit(): void {
    this.idCliente = this.routerParam.snapshot.params['identificacion'];
    this.buscarCliente();
  }


  regresar(){
    this.router.navigate(['/clientes', this.idCliente]);
  }

  


  buscarCliente() {
    this.clientesService.buscarParaMostrar(this.idCliente).subscribe({
      next: (cliente: ClienteResponse) => {
        this.clienteEncontrado.set(cliente);
        console.log(cliente);
        this.cargarHistorialReservaciones(cliente.identificacion);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }



  cargarHistorialReservaciones(idCliente:string){
    this.reservacionesService.buscarHistorialDeReservaciones(idCliente).subscribe({
      next: (lista : ReservacionResponse[])=>{
          this.reservaciones.set(lista);
      },
      error:(error: any)=>{
        this.registrarError(error);
      }

    });

  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }

}
