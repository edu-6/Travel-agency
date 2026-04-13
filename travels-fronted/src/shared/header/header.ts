import { Component, OnInit, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AutenticacionServicio } from '../../services/login/autenficacion-service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header implements OnInit {


  titulo = signal<string>("");

  ngOnInit(): void {
    this.titulo.set("");
    if(this.esAdmin()){
      this.titulo.set(" Area de adinistracion");
    }

    if(this.esAtencionCliente()){
      this.titulo.set(" Atención al cliente");
    }

    if(this.esOperador()){
      this.titulo.set(" Area de operaciones");
    }
  }




  constructor(private router: Router, private autenticacionSerivice: AutenticacionServicio){
    
  }

  public logout(): void {
    this.autenticacionSerivice.logout();
  }

  esAdmin(): boolean{
     return this.autenticacionSerivice.esAdmin();
  }

  esAtencionCliente(): boolean{
    return this.autenticacionSerivice.esAtencionCliente();
  }

  esOperador(): boolean{
    return this.autenticacionSerivice.esOperador();
  }


}



