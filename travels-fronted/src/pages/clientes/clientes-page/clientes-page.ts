import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalGenerico } from "../../../shared/modal/modal-generico/modal-generico";
import { ActivatedRoute, Router } from '@angular/router';
import { ClientesService } from '../../../services/login/clientes-service';
import { ClienteResponse } from '../../../modelos/clientes/cliente-response';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ClienteCard } from "../../../components/clientes/cliente-card/cliente-card";

@Component({
  selector: 'app-clientes-page',
  imports: [Header, ReactiveFormsModule, ModalGenerico, ClienteCard],
  templateUrl: './clientes-page.html',
  styleUrl: './clientes-page.css',
})
export class ClientesPage implements OnInit {

  barraBusqueda !: FormGroup;
  hayError = signal<boolean>(false);
  mensajeError !: string;
  mensajeEliminacion: string = "Desea eliminar al cliente?";

  origen : string = "clientes-page";

  clienteEncontrado = signal<ClienteResponse | null>(null);
  clienteSeleccionado  !: ClienteResponse;

  constructor(private formBuiler: FormBuilder,
     private router: Router, private clientesService: ClientesService
  ,private routerParam: ActivatedRoute) {

  }


  ngOnInit(): void {
    this.instanciarFormulario();

     const idIdentificacion =this.routerParam.snapshot.params['identificacion'];
     if(idIdentificacion != null){
      this.barraBusqueda.patchValue({
        busqueda: [idIdentificacion]
      });
      this.buscarCliente();
     }
    
  }


  private instanciarFormulario() {
    this.barraBusqueda = this.formBuiler.group({
      busqueda: [null, Validators.required]
    });

  };


  buscarCliente() {
    this.hayError.set(false);
    this.clienteEncontrado.set(null);

    if (this.barraBusqueda.invalid) return;

    const id = this.barraBusqueda.get('busqueda')?.value;

    this.clientesService.buscarParaMostrar(id).subscribe({
      next: (cliente: ClienteResponse) => {
        this.clienteEncontrado.set(cliente);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }



  redirigirAClienteForm() {
    this.router.navigate(['/clientes/form-page'], { state: { origin: 'clientes-page' } });

  }


  eliminarRegistro() {
    this.clientesService.eliminar(this.clienteSeleccionado.identificacion).subscribe({

      next: ()=>{
        this.buscarCliente();
      },
      error: (error: any)=>{
        this.registrarError(error);
      }
    });
  }

  guardarSeleccinado(seleccinado: ClienteResponse){
    this.clienteSeleccionado = seleccinado;
  }
}
