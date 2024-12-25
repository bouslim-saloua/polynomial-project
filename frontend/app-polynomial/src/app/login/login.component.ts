import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  
  private router = inject(Router);
  selector = 1;
  switch(position: number)
  {
    this.selector = position;
    document.documentElement.setAttribute("theme", position.toString());
  }
  inscrt(){
    this.router.navigate(['/inscription']);
}

}
