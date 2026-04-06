import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MetodoPago } from '../../../modelos/enums/metod-pago-enum';
import { required } from '@angular/forms/signals';
import { EnumsService } from '../../../services/login/enums-service';
import { PagoRequest } from '../../../modelos/pagos/pago-request';

@Component({
  selector: 'app-pago-form',
  imports: [ReactiveFormsModule],
  templateUrl: './pago-form.html',
  styleUrl: './pago-form.css',
})
export class PagoForm implements OnInit {

  mensajeEdicion: string = "editando";
  mensajeCreacion: string = "creacion";
  formulario !: FormGroup;

  mensajeError: string = "";

  hayError = signal<boolean>(false);
  formularioActivo = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);

  metodosPago = signal<MetodoPago[]>([]);

  @Input({ required: true })
  idReservacion !: number;


  @Output()
  pagoCreado = new EventEmitter<PagoRequest>();


  @Output()
  formularioCerrado = new EventEmitter<void>();

  constructor(private enumsService: EnumsService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.cargarProveedores();
    this.instanciarFormulario();
  }

  private instanciarFormulario() {
    this.formulario = this.formBuilder.group(
      {
        cantidad: [1, [Validators.min(1)]],
        id_metodo_pago: [null],
        idReservacion: [this.idReservacion],
      }
    );
  }


  cerrarFormulario() {
    this.instanciarFormulario();
    this.formularioCerrado.emit();
  }


  enviar() {
    this.intentoEnviarlo.set(true);
    if (!this.formulario.valid) return;

    const nuevoPago = this.formulario.value as PagoRequest;
    this.pagoCreado.emit(nuevoPago);
  }

  private cargarProveedores() {
    this.enumsService.getMetodosPago().subscribe({
      next: (todos: MetodoPago[]) => {
        this.metodosPago.set(todos);

        this.formulario.patchValue({
          id_metodo_pago: this.metodosPago()[0].id
        });

      },
      error: (httpError: any) => {

      }
    });
  }

}
