import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../services/transaction.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  balance: number = 0;
  currency: string = 'XOF';
  transactions: any[] = [];
  searchQuery = '';

  // Modales
  showProfileModal = false;
  showSendModal = false;
  showDepositModal = false;
  showWithdrawModal = false;

  // Formulaires
  send = { to: '', amount: 0, note: '' };
  deposit = { amount: 0 };
  withdraw = { amount: 0 };

  constructor(
    private tx: TransactionService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadBalance();
    this.loadTransactions();
  }

  // Charger solde
  loadBalance() {
    this.tx.getBalance().subscribe({
      next: (res: any) => {
        this.balance = res.balance ?? res.amount ?? 0;
        this.currency = res.currency ?? 'XOF';
      },
      error: (err) => {
        if (err.status === 401) this.router.navigate(['/login']);
        else alert('Erreur chargement solde: ' + (err.error?.message || err.message));
      }
    });
  }

  // Charger historique transactions
  loadTransactions() {
    this.tx.getTransactions(this.searchQuery).subscribe({
      next: (res: any) => { this.transactions = res || []; },
      error: (err) => {
        if (err.status === 401) this.router.navigate(['/login']);
        else alert('Erreur chargement transactions: ' + (err.error?.message || err.message));
      }
    });
  }

  onSearch() { this.loadTransactions(); }

  // Profil
  openProfile() { this.showProfileModal = true; }
  closeProfile() { this.showProfileModal = false; }

  // Envoi argent
  openSend() { this.showSendModal = true; }
  closeSend() { this.showSendModal = false; }

  submitSend() {
    if (!this.send.to || this.send.amount <= 0) {
      alert('Remplissez correctement le destinataire et le montant');
      return;
    }
    const payload = {
      toEmail: this.send.to,
      amount: this.send.amount,
      description: this.send.note
    };
    this.tx.sendMoney(payload).subscribe({
      next: () => {
        alert('Envoi effectué avec succès');
        this.closeSend();
        this.loadBalance();
        this.loadTransactions();
      },
      error: (err) => {
        if (err.status === 401) alert('Vous devez être connecté');
        else if (err.status === 403) alert('Action non autorisée');
        else alert('Erreur envoi : ' + (err.error?.message || err.message));
      }
    });
  }

  // Dépôt
  openDeposit() { this.showDepositModal = true; }
  closeDeposit() { this.showDepositModal = false; }
  submitDeposit() {
    if (this.deposit.amount <= 0) { alert('Montant invalide'); return; }
    this.tx.deposit(this.deposit).subscribe({
      next: () => {
        alert('Dépôt effectué');
        this.closeDeposit();
        this.loadBalance();
        this.loadTransactions();
      },
      error: (err) => alert('Erreur dépôt: ' + (err.error?.message || err.message))
    });
  }

  // Retrait
  openWithdraw() { this.showWithdrawModal = true; }
  closeWithdraw() { this.showWithdrawModal = false; }
  submitWithdraw() {
    if (this.withdraw.amount <= 0) { alert('Montant invalide'); return; }
    this.tx.withdraw(this.withdraw).subscribe({
      next: () => {
        alert('Retrait effectué');
        this.closeWithdraw();
        this.loadBalance();
        this.loadTransactions();
      },
      error: (err) => alert('Erreur retrait: ' + (err.error?.message || err.message))
    });
  }

  // Déconnexion
logout() { this.auth.logout(); this.router.navigate(['/']); }
}

