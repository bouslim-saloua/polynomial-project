import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.scss']
})
export class InscriptionComponent {
  selector = 1;
  switch(position: number)
  {
    this.selector = position;
    document.documentElement.setAttribute("theme", position.toString());
  }
  
}
