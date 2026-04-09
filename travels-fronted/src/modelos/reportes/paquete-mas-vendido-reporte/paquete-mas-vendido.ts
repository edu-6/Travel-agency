import { DetalleReservacionPaquete } from "./detalle-reservacion-paquete";

export interface ReportePaqueteMasVendido {
    idPaquete: number;
    nombre: string;
    descripcion: string;
    mejorEpoca: string;
    reservaciones: DetalleReservacionPaquete[];
}