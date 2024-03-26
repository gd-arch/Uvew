import { AbstractControl, ValidatorFn } from '@angular/forms';

export function tagsRequiredValidator(tags: any): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (!control || !tags || tags.length === 0) {
      return { tagsRequired: true };
    }
    return null;
  };
}

