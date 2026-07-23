import { Component } from '@angular/core';
import { Country } from '../../core/models/Country';
import { COUNTRIES } from '../../core/data/global-countries';

@Component({
  selector: 'app-configuracion-usuario',
  imports: [],
  templateUrl: './configuracion-usuario.html',
  styleUrl: './configuracion-usuario.css',
})
export class ConfiguracionUsuario {
  countries: Array<Country> = COUNTRIES;
}
