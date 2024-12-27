import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.scss']
})
export class InscriptionComponent {
  errorMessage = '';
  successMessage = '';
  isLoading = false;
  userData = {
    nom: '',
    prenom: '',
    email: '',
    password: '',
    role: ['ROLE_USER']
  };
  confirmPassword = '';
  selector = 1;
  switch(position: number)
  {
    this.selector = position;
    document.documentElement.setAttribute("theme", position.toString());
  }
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    this.errorMessage = '';
    this.successMessage = '';
    this.isLoading = true;
    if (!this.userData.nom || !this.userData.prenom || !this.userData.email || !this.userData.password || !this.confirmPassword) {
      Swal.fire({
        icon: 'error',
        title: 'Erreur',
        text: 'Tous les champs sont obligatoires'
      });
      this.isLoading = false;
      return;
    }
    if (this.userData.password !== this.confirmPassword) {
      Swal.fire({
        icon: 'error',
        title: 'Erreur',
        text: 'Les mots de passe ne correspondent pas'
      });
      this.isLoading = false;
      return;
    }

    this.authService.signup(this.userData).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Succès',
          text: 'Inscription réussie! Redirection vers la page de connexion...'
        });
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
       error: (error) => {
        this.isLoading = false;
        if (error.status === 400) {
          this.errorMessage = 'Email déjà utilisé ou données invalides';
        } else if (error.error?.message) {
          this.errorMessage = error.error.message;
        } else {
          this.errorMessage = 'Une erreur est survenue lors de l\'inscription';
        }
        Swal.fire({
          icon: 'error',
          title: 'Erreur',
          text: this.errorMessage
        });
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
  
}
