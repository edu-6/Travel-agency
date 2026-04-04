import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Pais } from '../../../modelos/enums/pais-enum';
import { EnumsService } from '../../../services/login/enums-service';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { NgFor } from '@angular/common';
import { DestinoRequest } from '../../../modelos/destinos/destino-request';
import { DestinosService } from '../../../services/login/destinos-service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-destino-form',
  imports: [FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './destino-form.html',
  styleUrl: './destino-form.css',
})
export class DestinoForm implements OnInit {

  paises = signal<Pais[] | null>(null);

  formulario !: FormGroup;
  mensajeError !: string;
  urlImagen !: string;

  mensajeCreacion: string = "Nuevo destino";
  mensajeEdicion: string = "Edicion destino";


  enEdicion: boolean = false;
  hayLinkImagen = signal<boolean>(false);
  hayError = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);

  constructor(private formBuilder: FormBuilder,
    private enumsService: EnumsService,
    private destinosService: DestinosService,
    private router: Router) {
  }


  @Input()
  destinoEnEdicion !: DestinoRequest;

  ngOnInit(): void {
    this.enEdicion = this.destinoEnEdicion != null;

    this.cargarPaises();
    this.instanciarFormulario();

    if (this.enEdicion) {
      this.hayLinkImagen.set(true);
      this.urlImagen = this.destinoEnEdicion.urlImagen;
      this.formulario.patchValue(this.destinoEnEdicion);
    }
  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [0],
      id_pais: [1, Validators.required],
      nombre: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
      descripcion: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(300)]],
      mejorEpoca: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
      urlImagen: ["", [Validators.required, Validators.maxLength(400)]],
    });
  }


  public enviar() {

    this.reiniciarBooleanos();

    this.intentoEnviarlo.set(true);
    if (!this.formulario.valid) return;

    let nuevo: DestinoRequest = this.formulario.value as DestinoRequest;

    if (this.enEdicion) {
      this.editar(nuevo);
    } else {
      this.crear(nuevo);
    }

  }


  private crear(nuevo: DestinoRequest) {
    this.destinosService.crear(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/destinos`]);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }

    });
  }

  private editar(nuevo: DestinoRequest) {
    this.destinosService.editarProvedor(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/destinos`]);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  private cargarPaises() {
    this.enumsService.getPaises().subscribe({
      next: (paises: Pais[]) => {
        this.paises.set(paises);
      },
      error: (errorHttp: any) => {
        const errorData: ErrorBackend = errorHttp.error;
        alert(errorData.detalles);
      }
    });
  }



  public actualizarUrlImagen(event: Event) {
    const elemento = event.target as HTMLInputElement;
    this.urlImagen = elemento.value;
    this.hayLinkImagen.set(true);
  }


  private reiniciarBooleanos() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(false);
  }



}
