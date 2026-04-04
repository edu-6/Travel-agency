import { Component } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ClienteForm } from "../../../components/clientes/cliente-form/cliente-form";

@Component({
  selector: 'app-clientes-form-page',
  imports: [Header, ClienteForm],
  templateUrl: './clientes-form-page.html',
  styleUrl: './clientes-form-page.css',
})
export class ClientesFormPage {


  
}
