import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'separatorks'
})
export class SeparatorksPipe implements PipeTransform {

  transform(value: string): string {
    if(value.length > 18) return "ERROR";
    let numero = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    let dot = false;
    let mitad1 = '';
    let mitad2 = '';
    for (let index = 0; index < numero.length; index++) {
      if(dot)
      {
        if(numero[index] !== ',') mitad2 += numero[index];
      }
      else{
        mitad1 += numero[index];
        if(numero[index] === '.') dot = true;
      }
    }
    return mitad1 + mitad2;
  }

}
