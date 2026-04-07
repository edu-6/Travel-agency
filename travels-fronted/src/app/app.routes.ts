import { Routes } from '@angular/router';
import { LoginForm } from '../components/login/login-form/login-form';
import { Home } from '../pages/home/home';
import { ProveedoresPage } from '../pages/Proveedores/proveedores-page/proveedores-page';
import { ProveedorFormPage } from '../pages/Proveedores/proveedor-form-page/proveedor-form-page';
import { ProveedorEditarPage } from '../pages/Proveedores/proveedor-editar-page/proveedor-editar-page';
import { DestinosPage } from '../pages/destinos/destinos-page/destinos-page';
import { DestinosFormPage } from '../pages/destinos/destinos-form-page/destinos-form-page';
import { DestinosEditarPage } from '../pages/destinos/destinos-editar-page/destinos-editar-page';
import { PaquetesPage } from '../pages/paquetes/paquetes-page/paquetes-page';
import { PaquetesFormPage } from '../pages/paquetes/paquetes-form-page/paquetes-form-page';
import { PaquetesEditarPage } from '../pages/paquetes/paquetes-editar-page/paquetes-editar-page';
import { authGuard } from '../guardias/guardia';
import { EmpleadosPage } from '../pages/empleados/empleados-page/empleados-page';
import { EmpleadosFormPage } from '../pages/empleados/empleados-form-page/empleados-form-page';
import { EmpleadosEditarPage } from '../pages/empleados/empleados-editar-page/empleados-editar-page';
import { ReservacionesPage } from '../pages/reservaciones/reservaciones-page/reservaciones-page';
import { ReservacionesFormPage } from '../pages/reservaciones/reservaciones-form-page/reservaciones-form-page';
import { ClientesFormPage } from '../pages/clientes/clientes-form-page/clientes-form-page';
import { ReservacionesDetallePage } from '../pages/reservaciones/reservaciones-detalle-page/reservaciones-detalle-page';
import { ClientesPage } from '../pages/clientes/clientes-page/clientes-page';
import { ClientesEditarPage } from '../pages/clientes/clientes-editar-page/clientes-editar-page';

export const routes: Routes = [

    // ejemplo 
    //{path: "proveedores", component: ProveedoresPage, canActivate: [authGuard]},

    { path: "", component: LoginForm },
    { path: "home", component: Home, },

    { path: "proveedores", component: ProveedoresPage, canActivate: [authGuard] },
    { path: "proveedores/form-page", component: ProveedorFormPage, canActivate: [authGuard] },
    { path: "proveedores/editar-page/:nombre", component: ProveedorEditarPage, canActivate: [authGuard] },

    { path: "destinos", component: DestinosPage, canActivate: [authGuard] },
    { path: "destinos/form-page", component: DestinosFormPage, canActivate: [authGuard] },
    { path: "destinos/editar-page/:nombre", component: DestinosEditarPage, canActivate: [authGuard] },


    { path: "paquetes", component: PaquetesPage, canActivate: [authGuard] },
    { path: "paquetes/form-page", component: PaquetesFormPage, canActivate: [authGuard] },
    { path: "paquetes/editar-page/:nombre", component: PaquetesEditarPage, canActivate: [authGuard] },


    { path: "empleados", component: EmpleadosPage },
    { path: "empleados/form-page", component: EmpleadosFormPage, },
    { path: "empleados/editar-page/:nombre", component: EmpleadosEditarPage, canActivate: [authGuard] },

    { path: "reservaciones", component: ReservacionesPage },
    { path: "reservaciones/form-page", component: ReservacionesFormPage },
    { path: "reservaciones/form-page/:identificacion", component: ReservacionesFormPage },
    { path: "reservaciones/detalle-page/:id", component: ReservacionesDetallePage },



    { path: "clientes", component: ClientesPage },
    { path: "clientes/editar-page/:identificacion", component: ClientesEditarPage },
    { path: "clientes/form-page", component: ClientesFormPage },
    { path: "clientes/form-page/:identificacion", component: ClientesFormPage },



];
