export interface PaqueteResponse {
  id: number;
  nombre: string;
  descripcion: string;
  destino: string;
  duracion: number;
  capacidadMaxima: number;
  precioVenta: number;
  ganancia: number;
  id_destino: number;
  activo: boolean;
  topadoEnProximasSalidas: boolean;
}