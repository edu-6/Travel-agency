export interface DetalleReservacionPaquete {
    idReservacion: number;
    nombrePaquete: string;
    nombreTitular: string;
    fechaCreacion: string | Date;
    totalAPagar: number;
    totalPagado: number;
    estado: string;
}