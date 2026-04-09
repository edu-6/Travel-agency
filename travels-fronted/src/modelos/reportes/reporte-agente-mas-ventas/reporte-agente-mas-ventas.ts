import { ReservacionDetalle } from "./reservacion-detalle";

export interface ReporteAgenteMasVentas {
  nombreAgente: string;
  idAgente: number;
  reservaciones: ReservacionDetalle[];
}