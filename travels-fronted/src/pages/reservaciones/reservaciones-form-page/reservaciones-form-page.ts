import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormsModule } from "@angular/forms";
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClientesService } from '../../../services/login/clientes-service';
import { ClienteResponse } from '../../../modelos/clientes/cliente-response';
import { AnyCatcher } from 'rxjs/internal/AnyCatcher';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ClienteCard } from "../../../components/clientes/cliente-card/cliente-card";
import { ReservacionForm } from "../../../components/reservaciones/reservacion-form/reservacion-form";

@Component({
  selector: 'app-reservaciones-form-page',
  imports: [Header, FormsModule, ClienteCard, ReservacionForm],
  templateUrl: './reservaciones-form-page.html',
  styleUrl: './reservaciones-form-page.css',
})
export class ReservacionesFormPage implements OnInit {

  hayError = signal<boolean>(false);
  mensajeError !: string;

  origen : string = "reservaciones-form";

  clienteEncontrado = signal<ClienteResponse | undefined>(undefined);

  identificacionRecibida !: string;

  constructor(private router: Router, private clientesService: ClientesService, private routerParams: ActivatedRoute) {

  }


  ngOnInit(): void {
    this.verificarSiRedirigieronConID();
  }


  private verificarSiRedirigieronConID(){
    this.identificacionRecibida = this.routerParams.snapshot.params['identificacion'];
    if(this.identificacionRecibida){
      this.buscarUsuario(this.identificacionRecibida);
    }
  }


  buscarUsuario(id: string) {

    if (id.length < 1) return;

    this.buscarUsuarioDB(id);
  }


  buscarUsuarioDB(id: string) {
    this.clientesService.buscarParaMostrar(id).subscribe({
      next: (cliente: ClienteResponse) => {
        
        if(cliente){
          this.clienteEncontrado.set(cliente);
        }
      },
      error: (error: any) => {
        this.registrarError(error);
        this.router.navigate(['/clientes/form-page', id]);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }

}
