import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { UsuarioLoginRequest } from "../../modelos/login/LoginRequest";
import { UsuarioLoginResponse } from "../../modelos/login/LoginResponse";
import { Observable, tap } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})


export class AutenticacionServicio {
  private constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient, private router: Router) {
  }

  isAuthenticated = signal<boolean>(!!localStorage.getItem('auth_token'));

  login(usuarioLogin: UsuarioLoginRequest): Observable<UsuarioLoginResponse> {
    return this.httpCliente.post<UsuarioLoginResponse>(
      this.constantesRest.getApiURL() + '/api/login',
      usuarioLogin
    ).pipe(
      tap((res) => {
        localStorage.setItem('auth_token', res.token);
        localStorage.setItem('nombre', res.nombre);
        localStorage.setItem('rol', res.rol);
        localStorage.setItem('id', res.id.toString());
        this.isAuthenticated.set(true);
      })
    );
  }

  logout() {
    localStorage.clear();
    this.isAuthenticated.set(false);
    this.router.navigate(['/']);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  getRol(): string | null {
    return localStorage.getItem('rol');
  }

  esAdmin(){
    return localStorage.getItem('rol') === "Administrador";
  }

  esOperador(){
    return localStorage.getItem('rol') === "Operaciones";
  }

  esAtencionCliente(){
    return localStorage.getItem('rol') === "Atencion al Cliente";
  }


}