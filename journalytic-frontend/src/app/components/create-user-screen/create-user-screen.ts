import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { form, maxLength, minLength, required, validate, FormField } from '@angular/forms/signals';

@Component({
  selector: 'app-create-user-screen',
  imports: [FormsModule, FormField],
  templateUrl: './create-user-screen.html',
  styleUrl: './create-user-screen.css',
})
export class CreateUserScreen {
  registrationUser = signal({
    username: '',
    password: '',
    passwordRepite: '',
  });

  registrationValidator = form(this.registrationUser, (schemaPath) => {
    minLength(schemaPath.password, 8, {
      message: 'La contraseña tiene que tener al menos 8 caracteres',
    });
    maxLength(schemaPath.password, 15, {
      message: 'La contraseña no puede sobrepasar los 15 caracteres',
    });
    required(schemaPath.username, {
      message: 'Por favor, el nombre de usuario no puede estar vacío',
    });
    required(schemaPath.passwordRepite, {
      message: 'Por favor, la contraseña no puede estar vacía',
    });
    required(schemaPath.passwordRepite, { message: 'Por favor, confirma la contraseña' });
    validate(schemaPath.passwordRepite, ({ value, valueOf }) => {
      const passwordRepite = value();
      const password = valueOf(schemaPath.password);

      if (passwordRepite !== password) {
        return {
          kind: 'Contraseñas no coinciden',
          message: 'Las contraseñas no coinciden',
        };
      }
      return null;
    });
  });

  crearUsuario() {
    console.log(this.registrationUser());
  }
}
