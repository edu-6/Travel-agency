import { ChangeDetectorRef, Component, Input, OnInit, signal, Signal } from '@angular/core';
import { FormBuilder, FormGroup, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { required } from '@angular/forms/signals';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { Header } from '../../../shared/header/header';
import { Pais } from '../../../modelos/enums/pais-enum';
import { EnumsService } from '../../../services/login/enums-service';
import { NgFor } from '@angular/common';
import { TipoServicio } from '../../../modelos/enums/tipo-servicio-enum';
import { ProveedoresService } from '../../../services/login/proveedores-service';
import { ProveedorRequest } from '../../../modelos/proveedores/ProveedorRequest';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-proveedor-form',
  imports: [ReactiveFormsModule, NgFor, RouterLink],
  templateUrl: './proveedor-form.html',
  styleUrl: './proveedor-form.css',
})
export class ProveedorForm implements OnInit {

  enEdicion = signal< boolean>(false);
  formulario !: FormGroup;

  paises: Pais[] = [];
  tiposServicio: TipoServicio[] = [];

  mensajeEdicion: String = "Editar proveedor";
  mensajeCreacion: String = "Creacion proveedor";

  mensajeError !: ErrorBackend;
  creadoConExito: boolean = false;
  editadoConExito: boolean = false;
  intentoEnviarlo: boolean = false;
  hayError: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private proveedoresService: ProveedoresService, private cd: ChangeDetectorRef,
    private enumsService: EnumsService, private router: Router) {

  }

  @Input()
  proveedorEnEdicion !: ProveedorRequest;

  ngOnInit(): void {
    this.enEdicion.set(this.proveedorEnEdicion != null);

    this.instanciarFormulario();
    this.cargarTiposServicio();
    this.cargarPaises();

    if (this.enEdicion()) {
      this.formulario.patchValue(this.proveedorEnEdicion);
    }

  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group(
      {
        id: [0],
        nombre: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(30)]],
        id_tipo_servicio: [0, Validators.required],
        contactos: ["", [ Validators.minLength(1), Validators.maxLength(40)]],
        id_pais: [0, Validators.required]
      }
    );
  }


  public enviar() {
    this.reiniciarBooleanos();

    this.intentoEnviarlo = true;

    if (!this.formulario.valid) return;
    let nuevo: ProveedorRequest = this.formulario.value as ProveedorRequest;


    if (this.enEdicion()) {
      this.editar(nuevo);
    } else {
      this.crear(nuevo);
    }


    this.cd.detectChanges();
  }


  private crear(nuevo: ProveedorRequest) {
    this.proveedoresService.crear(nuevo).subscribe({
      next: () => {
        this.creadoConExito = true;
        this.cd.detectChanges();
        this.router.navigate([`/proveedores`]);
      },
      error: (errorHttp: any) => {
        this.creadoConExito = false;
        this.mensajeError = errorHttp.error;
        this.hayError = true;
        this.cd.detectChanges();
      }
    });
  }


  private editar(proveedor: ProveedorRequest) {
    this.proveedoresService.editarProvedor(proveedor).subscribe({
      next: () => {
        this.editadoConExito = true;
        this.cd.detectChanges();
        this.router.navigate([`/proveedores`]);
      },
      error: (errorHttp: any) => {
        this.editadoConExito = false;
        this.mensajeError = errorHttp.error;
        this.hayError = true;
        this.cd.detectChanges();
      }
    });
  }


  private reiniciarBooleanos() {
    this.editadoConExito = false;
    this.hayError = false;
    this.intentoEnviarlo = false;
  }




  private cargarPaises() {
    this.enumsService.getPaises().subscribe({
      next: (paises: Pais[]) => {
        this.paises = paises;
        this.formulario.get('id_pais')?.setValue(this.paises[0].id);
      },
      error: (errorHttp: any) => {
        const errorData: ErrorBackend = errorHttp.error;
        alert(errorData.detalles);
      }
    });
  }



  private cargarTiposServicio() {
    this.enumsService.getTiposServicio().subscribe({
      next: (tipos: TipoServicio[]) => {
        this.tiposServicio = tipos;
        this.formulario.get('id_tipo_servicio')?.setValue(this.tiposServicio[0].id);
      },
      error: (errorHttp: any) => {
        const errorData: ErrorBackend = errorHttp.error;
        alert(errorData.detalles);
      }
    });
  }



}
