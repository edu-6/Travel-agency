import { PasajeroReservacion } from "./pasajero-reservacion";

export interface ReporteVenta {
    idReservacion: number;
    totalAPagar: number;
    nombreAgente: string;
    nombreTitular: string;
    idTitular: string;
    pasajeros: PasajeroReservacion[]; // Tu ArrayList de Java se convierte en Array []
}