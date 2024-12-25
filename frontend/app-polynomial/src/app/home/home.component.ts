import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  private router = inject(Router);
  title = 'Calculator';
  selector = 1;
  operator1 = '';
  operator2: number = 0;
  reset = true;
  operationSelected = false;
  operation = '';
  recentOperation = false;

  isButtonPressed = false;
  isMouseInside = false;
  onButtonDown() {
    this.isButtonPressed = true;
  }
  onButtonUp() {
    this.isButtonPressed = false;
  }
  onMouseEnter() {
    this.isMouseInside = true;
  }
  onMouseLeave() {
    this.isMouseInside = false;
    if (this.isButtonPressed) {
      this.isButtonPressed = false;
    }
  }

  switch(position: number)
  {
    this.selector = position;
    document.documentElement.setAttribute("theme", position.toString());
  }
  

  addDigit(caracter: string)
  {
    if(this.recentOperation)
    {
      this.recentOperation = false;
      this.operator1 = '0';
    }
    if(this.operator1 === '0')
    {
      if(caracter === '0') return;
      if(caracter === '.')
        this.operator1 += caracter;
      else
        this.operator1 = caracter;
      return;
    }
    if(caracter === '.' && this.operator1.includes('.')) return;
     
    this.operator1 += caracter;
    this.reset = false;
  }
  
  Del(){
    if(this.operator1 === '0' || this.operator1 === '') return;
    if(this.operator1.length == 1 )
    {
      this.operator1 = '0';
      return;
    }
    this.operator1 = this.operator1.slice(0, this.operator1.length - 1); 
  }
  
  Equal()
  {
  }
  Reset()
  {
    
  }
  nav() {
    console.log('Navigating to login...');
    this.router.navigate(['/login']);
  }

}
