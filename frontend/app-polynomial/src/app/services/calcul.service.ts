import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class CalculService {
  private apiUrl = 'http://localhost:8888/calcule'; // URL de votre backend

  constructor(private http: HttpClient,private authService: AuthService) {}

  factoriser(polynome: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/factorisation`, { polynome });
  }

  calculerRacines(polynome: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/racines`, { polynome });
  }

  findById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }
}
