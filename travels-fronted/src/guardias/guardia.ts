import { CanActivateFn, Router } from "@angular/router";
import { AutenticacionServicio } from "../services/login/autenficacion-service";
import { inject } from "@angular/core";


export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AutenticacionServicio);
  const router = inject(Router);

  if (!authService.isAuthenticated()) {
    return router.createUrlTree(['/']);
  }


  const rolesPermitidos = route.data['roles'] as Array<string>;
  const rolUsuario = authService.getRol();

  if (rolesPermitidos && !rolesPermitidos.includes(rolUsuario || '')) {
    return router.createUrlTree(['/']); 
  }

  return true;
};