import { Routes } from '@angular/router';
import { CreateUserScreen } from './components/create-user-screen/create-user-screen';
import { ConfiguracionUsuario } from './components/configuracion-usuario/configuracion-usuario';

export const routes: Routes = [
  {
    path: 'configuration/createUser',
    component: CreateUserScreen,
  },
  {
    path: 'configuration',
    component: ConfiguracionUsuario,
  },
];
