import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, NgOptimizedImage, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';

  showRegisterModal = false;
  register = { email: '', password: '', firstName: '', lastName: '' };

  constructor(private auth: AuthService, private router: Router) {}

  onLogin() {
    console.log('Données envoyées pour login:', { email: this.email, password: this.password });
    this.auth.login(this.email, this.password).subscribe({
      next: (res: any) => {
        console.log('Connexion réussie', res);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Erreur connexion', err);
        alert('Email ou mot de passe incorrect');
      }
    });
  }

  onRegister() {
    this.auth.register(this.register).subscribe({
      next: (res:any) => {
        alert('Compte créé avec succès');
        this.showRegisterModal = false;
      },
      error: (err) => {
        console.error(err);
        alert('Impossible de créer le compte');
      }
    });
  }
}
