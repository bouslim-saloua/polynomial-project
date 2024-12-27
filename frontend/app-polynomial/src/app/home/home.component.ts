import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CalculService } from '../services/calcul.service';
import { AuthService } from '../services/auth.service';
import { HistoriqueService } from '../services/historique.service';

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
  result: any = null;
  errorMessage1: string | null = null;
  errorMessage: string | null = null;
  isLoggedIn: boolean = false; 
  factorisationId: number | null = null;
  currentDate: string = '';
  historiqueList: any[] = [];
  isLoading = false;
  constructor(private calculService: CalculService,private authService: AuthService,private historiqueService: HistoriqueService) {}
 
 

 ngOnInit() {
  this.currentDate = new Date().toLocaleDateString();
  this.isLoggedIn = this.authService.isLoggedIn;

  if (this.isLoggedIn) {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    const utilisateurId = user.id;

    if (utilisateurId) {
      this.historiqueService.historique$.subscribe((data) => {
        this.historiqueList = data;
        console.log('Historique des calculs:', data);
      });
    }
  }
}
getFontSize(): string {
  const contentLength = this.result ? this.result.toString().length : this.operator1.length;
  if (contentLength > 100) {
    return '10px'; 
  } else if (contentLength > 10) {
    return '1.5rem'; 
  } else {
    return '2rem';
  }
}

 loadHistorique(): void {
   this.isLoading = true;
   const user = JSON.parse(localStorage.getItem('user') || '{}');
   const utilisateurId = user.id;
 
   if (utilisateurId) {
     this.historiqueService.getHistoriqueByUtilisateur(utilisateurId).subscribe({
       next: (data) => {
         this.historiqueList = data;
         console.log('Historique des calculs:', data);
 
         this.historiqueList.forEach((historique) => {
           const calculId = historique.calculId;
           this.calculService.findById(calculId).subscribe({
             next: (calculData) => {
               historique.calcul = calculData;
               console.log('Détails du calcul:', calculData);
             },
             error: (error) => {
               console.error('Erreur lors de la récupération du calcul:', error);
             },
           });
         });
 
         this.isLoading = false; // Arrête l'indicateur de chargement
       },
       error: (error) => {
         console.error('Erreur lors de la récupération de l\'historique:', error);
         this.isLoading = false;
       },
     });
   }
 }
 

  logout() {
    this.authService.logout();  
    this.isLoggedIn = false; 
  }
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
     this.errorMessage1 =null;
    this.errorMessage =null;
    this.result=null;
    
    if(this.operator1 === '0' || this.operator1 === '') return;
    if(this.operator1.length == 1 )
    {
      this.operator1 = '0';
      return;
    }
    this.operator1 = this.operator1.slice(0, this.operator1.length - 1); 
  }
  
  nav() {
    console.log('Navigating to login...');
    this.router.navigate(['/login']);
  }
  nav2() {
    console.log('Navigating to login...');
    this.router.navigate(['']);
  }
  factoriser() {
    this.errorMessage1 =null;
    this.errorMessage =null;
    this.result = null;
   
    if (!this.operator1 || this.operator1.trim() === '') {
      this.errorMessage1 = 'Veuillez remplir le champ avant de soumettre.';
      this.result = null; 
      return;
    } 
    const isValidExpression = /^[\d\+\-\*\/\^x\s]+$/.test(this.operator1);  
    if (!isValidExpression) {
      this.errorMessage = 'Le format est incorrect.';
      this.result = null; 
      return;
    }
    const formattedExpression = this.operator1.replace(/\^/g, '**');
    this.errorMessage = null;  
    this.calculService.factoriser(formattedExpression).subscribe(
      (response) => {
        if (response && response.factorisation) {
          this.result = response.factorisation; 
          console.log('Factorisation réussie:', response.factorisation);
          const idToFetch = response.id; 
          if (idToFetch) {
            this.calculService.findById(idToFetch).subscribe(
              (findResponse) => {
                this.factorisationId = findResponse.id; 
                console.log('Données trouvées par ID:', findResponse); 
                const user = JSON.parse(localStorage.getItem('user') || '{}');
                const utilisateurId = user.id;
                if (!utilisateurId || !this.factorisationId) {
                  console.error('Utilisateur ou calcul manquant');
                  return;
                }
                this.historiqueService.saveHistorique(utilisateurId, this.factorisationId)
                  .subscribe({
                    next: (response) => {
                      console.log('Historique sauvegardé avec succès:', response);
                      this.loadHistorique();
                    },
                    error: (error) => {
                      console.error('Erreur lors de la sauvegarde de l\'historique:', error);
                    }
                  });
              },
              (findError) => {
                console.error('Erreur lors de la récupération par ID:', findError);
              }
            );
          } else {
            console.error('Aucun ID trouvé dans la réponse de factorisation.');
          }
          
        } else {
          this.errorMessage = 'Réponse inattendue du serveur.';
          console.error('Réponse inattendue:', response);
        }
      },
      (error) => {
        this.errorMessage = 'Le format est incorrect.';
        console.error('Erreur lors de la factorisation:', error);
      }
    );
  }
 calculerRacines() {
    this.errorMessage1 = null;
    this.errorMessage = null;
    this.result = null;
    if (!this.operator1 || this.operator1.trim() === '') {
      this.errorMessage1 = 'Veuillez remplir le champ avant de soumettre.';
      return;
    }
    const isValidExpression = /^[\d\+\-\*\/\^x\s]+$/.test(this.operator1); 
    if (!isValidExpression) {
      this.errorMessage = 'Le format est incorrect.';
      return;
    }
    const formattedExpression = this.operator1.replace(/\^/g, '**'); 
    this.calculService.calculerRacines(formattedExpression).subscribe(
      (response) => {
        if (response && response.racines) {
          this.result = response.racines.map((racine: string) => `x = ${racine}`).join(', '); 
          console.log('Racines trouvées:', response.racines);
          const idToFetch = response.id; 
          if (idToFetch) {
            
            this.calculService.findById(idToFetch).subscribe(
              (findResponse) => {
                this.factorisationId = findResponse.id; 
                console.log('Données trouvées par ID:', findResponse);
                const user = JSON.parse(localStorage.getItem('user') || '{}');
                const utilisateurId = user.id;
  
                if (!utilisateurId || !this.factorisationId) {
                  console.error('Utilisateur ou calcul manquant');
                  return;
                }
                this.historiqueService.saveHistorique(utilisateurId, this.factorisationId)
                  .subscribe({
                    next: (response) => {
                      console.log('Historique sauvegardé avec succès:', response);
                      this.loadHistorique();
                    },
                    error: (error) => {
                      console.error('Erreur lors de la sauvegarde de l\'historique:', error);
                    }
                  });
              },
              (findError) => {
                console.error('Erreur lors de la récupération par ID:', findError);
              }
            );
          } else {
            console.error('Aucun ID trouvé dans la réponse.');
          }
        } else {
          this.errorMessage = 'Réponse inattendue du serveur.';
          console.error('Réponse inattendue:', response);
        }
      },
      (error) => {
        this.errorMessage = 'Erreur lors du calcul des racines.';
        console.error('Erreur lors du calcul des racines:', error);
      }
    );
  }
}

