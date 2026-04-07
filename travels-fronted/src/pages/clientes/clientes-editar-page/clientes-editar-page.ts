import { Component, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ClienteRequest } from '../../../modelos/clientes/cliente-request';
import { ClientesService } from '../../../services/login/clientes-service';
import { ActivatedRoute } from '@angular/router';
import { ClienteForm } from "../../../components/clientes/cliente-form/cliente-form";

@Component({
  selector: 'app-clientes-editar-page',
  imports: [Header, ClienteForm],
  templateUrl: './clientes-editar-page.html',
  styleUrl: './clientes-editar-page.css',
})
export class ClientesEditarPage {

  idCliente !: string;

  clienteEncontrado !: ClienteRequest;
  fueEncontrado = signal<boolean>(false);


  constructor(private clienteService: ClientesService, private router: ActivatedRoute){
  }

  ngOnInit(): void {
    this.idCliente = this.router.snapshot.params['identificacion'];
    this.clienteService.buscarParaEditar(this.idCliente).subscribe({
      next: (cliente: ClienteRequest ) => {
        this.clienteEncontrado = cliente;
        this.fueEncontrado.set(true);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }




}
