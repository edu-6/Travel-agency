import { Component, OnInit } from '@angular/core';
import { Header } from "../../shared/header/header";
import { ActualizacionReservacionesService } from '../../services/login/actualizacion-reservaciones-service';
import { ErrorBackend } from '../../modelos/ErrorBackend';

@Component({
  selector: 'app-home',
  imports: [Header],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {

  constructor(private actualizacionesService : ActualizacionReservacionesService){

  }

  ngOnInit(): void {
    this.actualizarReservaciones();
  }


  private actualizarReservaciones(){
    this.actualizacionesService.actualizarEstadoReservaciones().subscribe({
      next: ()=>{
        console.log("actualizadas correctamente");
      },
      error:(error: any)=>{
          const errorData: ErrorBackend = error.error;
          console.log(errorData);
      }
    });
  }

  
}
