
CREATE DATABASE travelsDB;
use travelsDB;

CREATE TABLE rol (
     rol_id INTEGER NOT NULL PRIMARY KEY,
     rol_nombre VARCHAR(20) NOT NULL
);

CREATE TABLE empleado (
     empleado_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
     empleado_nombre VARCHAR(100) NOT NULL UNIQUE,
     empleado_contraseña VARCHAR(800) NOT NULL,
     empleado_id_rol INTEGER NOT NULL,
     empleado_activo BOOLEAN NOT NULL,
     CONSTRAINT fk_empleado_rol FOREIGN KEY (empleado_id_rol) REFERENCES rol(rol_id) ON DELETE CASCADE
);

CREATE TABLE pais (
     pais_id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
     pais_nombre VARCHAR(20) NOT NULL
);

CREATE TABLE nacionalidad (
     nacionalidad_id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
     nacionalidad_nombre VARCHAR(20) NOT NULL
);

CREATE TABLE cliente (
     cliente_id VARCHAR(20) NOT NULL PRIMARY KEY,
     cliente_nombre VARCHAR(100) NOT NULL,
     cliente_id_nacionalidad INTEGER NOT NULL,
     cliente_fecha_nacimiento DATE NOT NULL,
     cliente_telefono VARCHAR(20) NOT NULL,
     cliente_correo VARCHAR(50) NOT NULL,
     CONSTRAINT fk_cliente_nacionalidad FOREIGN KEY (cliente_id_nacionalidad) REFERENCES nacionalidad(nacionalidad_id) ON DELETE CASCADE
);


CREATE TABLE tipo_servicio(
    tipo_servicio_id INTEGER NOT NULL PRIMARY KEY,
    tipo_servicio_nombre VARCHAR(20) NOT NULL
);

CREATE TABLE proveedor(
    proveedor_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    proveedor_nombre VARCHAR(30) NOT NULL,
    proveedor_id_tipo INTEGER NOT NULL,
    proveedor_contactos VARCHAR(40),
    proveedor_id_pais INTEGER NOT NULL,
    CONSTRAINT fk_proveedor_pais FOREIGN KEY (proveedor_id_pais) REFERENCES pais(pais_id) ON DELETE CASCADE
);

CREATE TABLE destino (
     destino_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
     destino_nombre VARCHAR(40) NOT NULL,
     destino_descripcion VARCHAR(300) NOT NULL,
     destino_mejor_epoca VARCHAR(100), 
     destino_url_imagen	 VARCHAR(500),
     destino_id_pais	 INTEGER NOT NULL,
     CONSTRAINT fk_destino_pais FOREIGN KEY (destino_id_pais) REFERENCES pais(pais_id) ON DELETE CASCADE
);

CREATE TABLE paquete (
     paquete_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
     paquete_nombre VARCHAR(200) NOT NULL UNIQUE,
     paquete_descripcion VARCHAR (300),
     paquete_precio DECIMAL(15,2) NOT NULL,
     paquete_ganancia DECIMAL(15,2) NOT NULL,
     paquete_duracion INTEGER NOT NULL,
     paquete_capacidad_maxima INTEGER NOT NULL,
     paquete_id_destino INTEGER NOT NULL,
     paquete_activo BOOLEAN NOT NULL,
     CONSTRAINT fk_paquete_destino FOREIGN KEY (paquete_id_destino) REFERENCES destino (destino_id) ON DELETE CASCADE
);

CREATE TABLE servicio_paquete (
     servicio_paquete_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
     servicio_paquete_descripcion VARCHAR (300) NOT NULL,
     servicio_paquete_costo DECIMAL(15,2) NOT NULL,
     servicio_paquete_id_paquete INTEGER NOT NULL,
     servicio_paquete_id_proveedor INTEGER NOT NULL,
     CONSTRAINT fk_servicio_paquete_id_paquete FOREIGN KEY (servicio_paquete_id_paquete) REFERENCES paquete (paquete_id) ON DELETE CASCADE,
     CONSTRAINT fk_servicio_paquete_id_proveedor FOREIGN KEY (servicio_paquete_id_proveedor) REFERENCES proveedor (proveedor_id) ON DELETE CASCADE
);

CREATE TABLE estado_reservacion(
    estado_reservacion_id INTEGER NOT NULL PRIMARY KEY,
    estado_reservacion_nombre VARCHAR(20) NOT NULL
);

CREATE TABLE reservacion(
    rs_numero_reservacion INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    rs_id_titular VARCHAR(20) NOT NULL,
    rs_cantidad_pasajeros INTEGER NOT NULL,
    rs_id_agente_creador  INTEGER NOT NULL,
    rs_id_estado          INTEGER NOT NULL,
    rs_fecha_creacion     DATE NOT NULL,
    rs_fecha_viaje	  DATE NOT NULL,
    rs_total_pagado	  DECIMAL(15,2) NOT NULL,
    rs_id_paquete 	  INTEGER NOT NULL,
    rs_total_a_pagar 	  DECIMAL(15,2) NOT NULL,
    CONSTRAINT fk_reservacion_creador FOREIGN KEY  (rs_id_agente_creador) REFERENCES empleado(empleado_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservacion_estado FOREIGN KEY  (rs_id_estado) REFERENCES estado_reservacion(estado_reservacion_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservacion_titular FOREIGN KEY  (rs_id_titular) REFERENCES cliente(cliente_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservacion_paquete FOREIGN KEY  (rs_id_paquete) REFERENCES paquete(paquete_id) ON DELETE CASCADE
);


CREATE TABLE metodo_pago(
   metodo_pago_id INTEGER NOT NULL PRIMARY KEY,
   metodo_pago_nombre VARCHAR(15) NOT NULL
);


CREATE TABLE pago_reservacion(
   pago_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
   pago_cantidad DECIMAL(15,2) NOT NULL,
   pago_id_reservacion INTEGER NOT NULL,
   pago_fecha DATE NOT NULL,
   pago_id_metodo INTEGER NOT NULL,
   CONSTRAINT fk_pago_reservacion FOREIGN KEY (pago_id_reservacion) REFERENCES reservacion(rs_numero_reservacion) ON DELETE CASCADE,
   CONSTRAINT fk_metodo_pago FOREIGN KEY (pago_id_metodo) REFERENCES metodo_pago(metodo_pago_id) ON DELETE CASCADE
);

CREATE TABLE pasajero_reservacion(
	pasajero_reservacion_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	pasajero_reservacion_id_reservacion INTEGER NOT NULL,
	pasajero_reservaicon_id_cliente VARCHAR(20) NOT NULL,
	CONSTRAINT fk_boleto_reservacion FOREIGN KEY (pasajero_reservacion_id_reservacion) REFERENCES reservacion(rs_numero_reservacion) ON DELETE CASCADE,
	CONSTRAINT fk_boleto_cliente FOREIGN KEY (pasajero_reservaicon_id_cliente) REFERENCES cliente(cliente_id) ON DELETE CASCADE
);

CREATE TABLE cancelacion(
	cancelacion_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	cancelacion_fecha DATE NOT NULL,
	cancelacion_id_reservacion INTEGER NOT NULL,
	cancelacion_cantidad_rembolso DECIMAL(15,2) NOT NULL,
	CONSTRAINT fk_cancelacion_reservacion FOREIGN KEY (cancelacion_id_reservacion) REFERENCES reservacion(rs_numero_reservacion) ON DELETE CASCADE
);



INSERT INTO rol (rol_id, rol_nombre) VALUES
(1, 'Atencion al Cliente'),
(2, 'Operaciones'),
(3, 'Administrador');

INSERT INTO estado_reservacion (estado_reservacion_id, estado_reservacion_nombre) VALUES 
(1, 'Pendiente'),
(2, 'Confirmada'),
(3, 'Cancelada'),
(4, 'Completada');


INSERT INTO tipo_servicio (tipo_servicio_id, tipo_servicio_nombre) VALUES 
(1, 'Aerolinea'),
(2, 'Hotel'),
(3, 'Tour'),
(4, 'Traslado'),
(5, 'Otro');

INSERT INTO metodo_pago (metodo_pago_id, metodo_pago_nombre) VALUES 
(1, 'Efectivo'),
(2, 'Tarjeta'),
(3, 'Transferencia');


INSERT INTO pais (pais_nombre) VALUES 
('Guatemala'),
('Mexico'),
('Estados Unidos'),
('España'),
('Colombia');

INSERT INTO nacionalidad (nacionalidad_nombre) VALUES
('Guatemalteca'),
('Mexicana'),
('Estadounidense'),
('Española'),
('Colombiana');
