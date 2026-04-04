import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { DestinosService } from '../../../services/login/destinos-service';
import { ActivatedRoute, Router } from '@angular/router';
import { DestinoResponse } from '../../../modelos/destinos/destino-reponse';
import { DestinoRequest } from '../../../modelos/destinos/destino-request';
import { DestinoForm } from "../../../components/destinos/destino-form/destino-form";

@Component({
  selector: 'app-destinos-editar-page',
  imports: [Header, DestinoForm],
  templateUrl: './destinos-editar-page.html',
  styleUrl: './destinos-editar-page.css',
})
export class DestinosEditarPage implements OnInit {

  nombreDestino !: string;
  destino !: DestinoRequest;

  destionEncontrado = signal<boolean>(false);


  constructor(private destinosService: DestinosService, private router: ActivatedRoute){
  }

  ngOnInit(): void {
    this.nombreDestino = this.router.snapshot.params['nombre'];
    this.destinosService.buscarParaEditar(this.nombreDestino).subscribe({
      next: (destino: DestinoRequest ) => {
        this.destino = destino;
        this.destionEncontrado.set(true);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
}
