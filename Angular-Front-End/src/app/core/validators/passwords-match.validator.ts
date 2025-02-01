import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function PasswordsMatchValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.get('motPasse');
    const confirmPassword = control.get('confirmPassword');
    return password && confirmPassword && password.value !== confirmPassword.value
      ? { passwordsMismatch: true }
      : null;
  };
}
