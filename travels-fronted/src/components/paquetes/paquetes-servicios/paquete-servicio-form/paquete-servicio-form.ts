import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProveedorResponse } from '../../../../modelos/proveedores/ProveedorResponse';
import { ProveedoresService } from '../../../../services/login/proveedores-service';
import { PaqueteServicio } from '../../../../modelos/proveedor-servicio/paquete-servicio';
import { PaqueteServicioCard } from "../paquete-servicio-card/paquete-servicio-card";
import { PaquetesService } from '../../../../services/login/paquetes-service';

@Component({
  selector: 'app-paquete-servicio-form',
  imports: [ReactiveFormsModule, PaqueteServicioCard],
  templateUrl: './paquete-servicio-form.html',
  styleUrl: './paquete-servicio-form.css',
})
export class PaqueteServicioForm implements OnInit {
  mensajeEdicion: string = "editando";
  mensajeCreacion: string = "creacion";
  formulario !: FormGroup;

  enEdicion = signal<boolean>(false);
  mensajeError: string = "";

  hayError = signal<boolean>(false);
  formularioActivo = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);

  proveedores = signal<ProveedorResponse[]>([]);

  existenes = signal<PaqueteServicio[]>([]);
  nuevos = signal<PaqueteServicio[]>([]);

  seleccionado !: PaqueteServicio;

  idActual: number = -1;

  @Input()
  existenesParametro !: PaqueteServicio[];


  constructor(private proveedoresService: ProveedoresService,
     private formBuilder: FormBuilder, private paquetesService : PaquetesService) {

  }
  

  ngOnInit(): void {
    this.cargarProveedores();
    this.instanciarFormulario();

    if(this.existenesParametro){
      this.existenes.set(this.existenesParametro);
    }
  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group(
      {
        id_paquete: [],
        id: [this.idActual],
        descripcion: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(300)]],
        id_proveedor: [Validators.required],
        precio: [10, [Validators.required, Validators.min(1)]],
        nombreProveedor: [""]
      }
    );
  }


  private limpiarFormulario() {
    this.formulario.reset({
      id: this.idActual,
      precio: 1,
      id_proveedor: 0,
      descripcion: ''
    });

    this.formulario.patchValue({ id_proveedor: this.proveedores()[0].id });
  }

  abrirFormulario() {
    this.formularioActivo.set(true);
    this.intentoEnviarlo.set(false);
    this.limpiarFormulario();
  }

  cerrarFormulario() {
    this.formularioActivo.set(false);
  }

  abrirFormularioModoEdicion(seleccionado: PaqueteServicio) {
    this.enEdicion.set(true);
    this.formularioActivo.set(true);
    this.formulario.patchValue(seleccionado);
  }


  enviar() {
    this.intentoEnviarlo.set(true);

    if (!this.formulario.valid) return;
    this.cerrarFormulario();

    if (this.enEdicion()) {
      this.editar();
    } else {
      this.guardarNuevo();
    }

  }


  private editar() {
    let nuevo = this.formulario.value as PaqueteServicio;
    this.agregarNombreProveedor(nuevo);

    let existeEnDB = nuevo.id > 0;
    let indice;
    if (existeEnDB) {
      this.existenes.update(lista =>lista.map(p => p.id === nuevo.id ? nuevo : p));
    } else {
      this.nuevos.update(lista =>lista.map(p => p.id === nuevo.id ? nuevo : p));
    }
    
    this.enEdicion.set(false);
  }



  eliminarSeleccionado(seleccionado: PaqueteServicio) {
    let id = seleccionado.id;
    let existeEnDB = id > 0;
    let indice;
    if (existeEnDB) {
      this.existenes.update(lista => lista.filter(p => p.id !== seleccionado.id));

      this.eliminarServicioEnPaquete(seleccionado.id.toString());
    } else {
      this.nuevos.update(lista => lista.filter(p => p.id !== seleccionado.id));
    }
  }

  private guardarNuevo() {
    let nuevo = this.formulario.value as PaqueteServicio;
    this.agregarNombreProveedor(nuevo);

    this.nuevos.update(lista => [...lista, nuevo]);
    this.idActual--; // restar id
    this.limpiarFormulario();

  }


  private agregarNombreProveedor(nuevo: PaqueteServicio){
    const proveedorSeleccionado = this.proveedores().find(p => p.id == nuevo.id_proveedor);
    if (proveedorSeleccionado) {
      nuevo.nombreProveedor = proveedorSeleccionado.nombre;
    }
  }




  private eliminarServicioEnPaquete(id: string){
    this.paquetesService.eliminarServicioEnPaquete(id).subscribe({
      next:() =>{
      },
      error :(httpError: any)=>{
        console.log(httpError.error);
      }
    });
  }



  private cargarProveedores() {
    this.proveedoresService.obtenerTodos().subscribe({
      next: (todos: ProveedorResponse[]) => {
        this.proveedores.set(todos);
        this.formulario.patchValue({ id_proveedor: todos[0].id });
      },
      error: (httpError: any) => {

      }

    });
  }

}
