export interface ReservacionRequest {
    idTitular: string;
    fechaViaje: Date,
    idPaquete: number;
    idAgenteCreador: string;
    pasajeros: string [];
}