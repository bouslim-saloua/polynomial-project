import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

interface AuthResponse {
  token: string;
  user: {
    id: number;
    email: string;
    nom:string;
    prenom:string
};
}

interface LoginCredentials {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root', 
})
export class AuthService {
  private apiUrl = 'http://localhost:8888/api/auth'; 
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  isTokenExpired(token: string): boolean {
    try {
      const decoded: any = jwtDecode(token);
      const currentTime = Math.floor(Date.now() / 1000);
      return decoded.exp < currentTime; 
    } catch (error) {
      console.error('Erreur lors de la vérification du token:', error);
      return true;
    }
  }
  
  constructor(private http: HttpClient, private router: Router) {
    const token = localStorage.getItem('token');
    if (token && !this.isTokenExpired(token)) {
      this.isLoggedInSubject.next(true);
    } else {
      this.logout();
    }
  }
  
  

  
login(credentials: LoginCredentials): Observable<AuthResponse> {
  console.log('Tentative de connexion avec:', credentials); 

  return this.http.post<AuthResponse>('http://localhost:8888/api/auth/signin', credentials).pipe(
    tap({
      next: (response: AuthResponse) => {
        console.log('Réponse du serveur:', response); 
        if (response && response.token) {
          localStorage.setItem('token', response.token);

          const decodedToken: any = jwtDecode(response.token);
          console.log('Token décodé:', decodedToken);

          const userData = {
            email: decodedToken.sub, 
          };

          
          this.findByEmail(userData.email).subscribe({
            next: (userDetails) => {
              console.log('Détails utilisateur récupérés:', userDetails);
              const fullUserData = {
                id: userDetails.id,
                email: userDetails.email,
                nom: userDetails.nom,
                prenom: userDetails.prenom,
              };

              localStorage.setItem('user', JSON.stringify(fullUserData));
              this.isLoggedInSubject.next(true);
              console.log('Utilisateur complet enregistré:', fullUserData);        
            },
            error: (error) => {
              console.error('Erreur lors de la récupération des détails utilisateur:', error);
            },
          });
        }
      },
      error: (error) => {
        console.error('Erreur de connexion:', error); 
      },
    })
  );
}

  findByEmail(email: string): Observable<any> {
    return this.http.get<any>(`http://localhost:8888/api/auth/findByEmail?email=${email}`);
}

  get isLoggedIn(): boolean {
    return this.isLoggedInSubject.value;
  }
  
  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, userData);
  }
  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('userId');
    this.isLoggedInSubject.next(false);
    localStorage.removeItem('token');
    this.router.navigate(['/login']); 
  }
  signup(userData: {
    nom: string,
    prenom: string,
    email: string,
    password: string,
    role: string[]
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, userData);
  }

  getUserId(): number | null {
    return this.getUserIdFromToken();
  }

  getUserIdFromToken(): number | null {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decodedToken: any = jwtDecode(token);
        return decodedToken?.id || null;
      } catch (error) {
        console.error('Invalid token:', error);
        return null;
      }
    }
    return null;
  }

}
