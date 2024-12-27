import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from './auth.service';

interface Historique {
  id?: number;
  expression: string;
  resultat: string;
  dateCalcul?: Date;
  utilisateurId?: number;
  calculId?: number;
}
@Injectable({
  providedIn: 'root'
})
export class HistoriqueService {
  private apiUrl = 'http://localhost:8888/historiques';
  private historiqueSubject = new BehaviorSubject<any[]>([]);
  historique$ = this.historiqueSubject.asObservable();
  constructor(private http: HttpClient, private authService: AuthService) {
  }
  getHistoriqueByUtilisateur(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/utilisateur/${id}`);
  }

  saveHistorique(utilisateurId: number, calculId: number): Observable<any> {
    const url = `http://localhost:8888/historiques/${utilisateurId}/${calculId}`;
    
    // Créer un objet pour l'historique, incluant la date actuelle
    const historiqueData = {
      utilisateurId,
      calculId,
      date: new Date().toISOString() // La date actuelle au format ISO
    };
  
    // Effectuer la requête HTTP POST avec l'objet contenant les informations et la date
    return this.http.post<any>(url, historiqueData);
  }
  
  

}