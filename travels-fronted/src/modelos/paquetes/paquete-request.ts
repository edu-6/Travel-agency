export interface PaqueteRequest {
  id: number;
  nombre: string;
  descripcion: string;
  precioVenta: number;
  duracion: number;
  capacidadMaxima: number;
  id_destino: number;
  activo: boolean;
  destino  ?: string;
  topadoEnProximasSalidas: boolean;

}