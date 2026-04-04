import { Component, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-reservaciones-page',
  imports: [Header, RouterLink],
  templateUrl: './reservaciones-page.html',
  styleUrl: './reservaciones-page.css',
})
export class ReservacionesPage {


  hayError = signal<boolean>(false);
  mensajeError !: string;


}
