import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoggedIn: boolean = false;
  constructor(private authService: AuthService) {}
  
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

login() {
  this.errorMessage ='';
  if (!this.email.trim() || !this.password.trim()) {
    Swal.fire({
      icon: 'error',
      title: 'Erreur',
      text: 'Veuillez remplir tous les champs.',
      confirmButtonColor: '#3085d6'
    });
    return;
  }

  const credentials = { email: this.email, password: this.password };

  this.authService.login(credentials).subscribe({
    next: (response) => {
      Swal.fire({
        icon: 'success',
        title: 'Connexion réussie!',
        text: 'Vous allez être redirigé...',
        timer: 1500,
        showConfirmButton: false
      }).then(() => {
        this.router.navigate(['']);
      });
    },
    error: (err) => {
      console.error('Erreur de connexion:', err);
      Swal.fire({
        icon: 'error',
        title: 'Échec de la connexion',
        text: 'Veuillez vérifier vos identifiants.',
        confirmButtonColor: '#3085d6'
      });
    },
  });
}
logout() {
  this.authService.logout();  
  this.router.navigate(['/login']); 
}
nav2() {
  console.log('Navigating to login...');
  this.router.navigate(['']);
}
}
