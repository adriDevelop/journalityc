import { Routes } from '@angular/router';
import { CreateUserScreen } from './components/create-user-screen/create-user-screen';

export const routes: Routes = [
  {
    path: 'configuration/createUser',
    component: CreateUserScreen,
  },
];
