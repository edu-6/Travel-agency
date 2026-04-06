import { PasajeroResponse } from "./pasajero-response";

export interface ReservacionResponse {
    id: number;
    titular: string;
    nombrePaquete: string;
    estado: string;
    fechaCreacion: string | Date;
    fechaViaje: string | Date;
    precioTotal: number;
    totalPagado: number;
    pasajeros: PasajeroResponse[];
}