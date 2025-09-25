import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private base = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): { headers: HttpHeaders } {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`
      })
    };
  }

  getBalance(): Observable<any> {
    return this.http.get<any>(`${this.base}/account/balance`, this.getAuthHeaders());
  }

  getTransactions(query: string = '') {
    const q = query ? `?q=${encodeURIComponent(query)}` : '';
    return this.http.get<any>(`${this.base}/transactions${q}`, this.getAuthHeaders());
  }

  sendMoney(payload: any) {
    return this.http.post<any>(`${this.base}/transactions/send`, payload, this.getAuthHeaders());
  }

  deposit(payload: any) {
    return this.http.post<any>(`${this.base}/transactions/deposit`, payload, this.getAuthHeaders());
  }

  withdraw(payload: any) {
    return this.http.post<any>(`${this.base}/transactions/withdraw`, payload, this.getAuthHeaders());
  }
}


