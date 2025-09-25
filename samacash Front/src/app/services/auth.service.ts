import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private base = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    return this.http.post<any>(`${this.base}/auth/login`, { email, password }, { withCredentials: true }).pipe(
      tap(res => {
        if (res && res.token) {
          // Sauvegarde du JWT
          localStorage.setItem('token', res.token);
          // Sauvegarde de l'email de l'utilisateur
          localStorage.setItem('userEmail', email);
        }
      })
    );
  }

  register(payload: any) {
    return this.http.post<any>(`${this.base}/auth/register`, payload, { withCredentials: true });
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // ⚡ Nouvelle méthode pour récupérer l'email
  getUserEmail(): string | null {
    return localStorage.getItem('userEmail');
  }
}

