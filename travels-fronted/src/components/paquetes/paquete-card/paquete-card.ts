import { Component, Input, input, OnInit } from '@angular/core';
import { PaqueteGeneral } from '../../../modelos/paquetes/paquete-full';
import { PaqueteRequest } from '../../../modelos/paquetes/paquete-request';
import { RouterLink } from '@angular/router';
import { PaqueteResponse } from '../../../modelos/paquetes/paquete-response';

@Component({
  selector: 'app-paquete-card',
  imports: [RouterLink],
  templateUrl: './paquete-card.html',
  styleUrl: './paquete-card.css',
})
export class PaqueteCard implements OnInit {

  @Input({ required: true })
  paquete !: PaqueteResponse;
  

  ngOnInit(): void {
  }








}
