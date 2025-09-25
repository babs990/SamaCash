# MoneyTransfer - Plateforme de transfert d'argent

## Description

MoneyTransfer est une application web qui permet aux utilisateurs de gérer leur compte, d'effectuer des virements, des dépôts et des retraits d'argent en toute sécurité.
Le projet utilise **Spring Boot** pour le backend et **Angular** pour le frontend, avec **JWT** pour l'authentification et **H2/MySQL** pour la base de données.

---

## Fonctionnalités

### Backend (Spring Boot)

* Authentification et enregistrement des utilisateurs
* Gestion des comptes (balance, currency)
* Historique des transactions
* Virements entre utilisateurs
* Dépôts et retraits
* Gestion des erreurs avec des exceptions personnalisées
* Sécurité avec JWT

### Frontend (Angular)

* Connexion / Inscription
* Consultation du solde
* Virements vers d'autres utilisateurs
* Dépôts et retraits
* Historique des transactions
* Interface simple et responsive

---

## Architecture

```
Frontend (Angular)  <---->  Backend (Spring Boot)  <---->  Base de données (H2/MySQL)
```

* **Angular** : HTTP client pour communiquer avec le backend via REST API.
* **Spring Boot** : REST API sécurisée par JWT.
* **JPA/Hibernate** : gestion des entités User, Account et Transaction.
* **Base de données** : stockage des utilisateurs, comptes et transactions.

---

## Installation

### Prérequis

* Java 17+
* Maven
* Node.js & npm
* Angular CLI
* Base de données MySQL ou H2 (selon configuration)

### Backend

1. Cloner le projet :

```bash
git clone <url_du_projet>
cd moneytransfer-backend
```

2. Configurer la base de données dans `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/moneytransfer
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

3. Lancer l'application :

```bash
mvn spring-boot:run
```

* Le serveur backend sera accessible sur `http://localhost:8080`.

### Frontend

1. Aller dans le dossier frontend :

```bash
cd moneytransfer-frontend
```

2. Installer les dépendances :

```bash
npm install
```

3. Lancer l'application Angular :

```bash
ng serve
```

* L'application sera accessible sur `http://localhost:4200`.

---

## API Endpoints

### Auth

| Méthode | Endpoint       | Description           |
| ------- | -------------- | --------------------- |
| POST    | /auth/register | Créer un utilisateur  |
| POST    | /auth/login    | Connexion utilisateur |

### Compte

| Méthode | Endpoint         | Description        |
| ------- | ---------------- | ------------------ |
| GET     | /account/balance | Récupérer le solde |

### Transactions

| Méthode | Endpoint               | Description                 |
| ------- | ---------------------- | --------------------------- |
| GET     | /transactions          | Historique des transactions |
| POST    | /transactions/send     | Virement entre utilisateurs |
| POST    | /transactions/deposit  | Dépôt d'argent              |
| POST    | /transactions/withdraw | Retrait d'argent            |

---

## Structure des entités principales

* **User / AppUser**

  * `id`, `email`, `password`, `firstName`, `lastName`, `role`

* **Account**

  * `id`, `user`, `balance`, `currency`

* **Transaction**

  * `id`, `fromAccount`, `toAccount`, `amount`, `fee`, `type`, `description`, `createdAt`

---

## Gestion des erreurs

* `ApiException` : utilisé pour retourner des messages d’erreur personnalisés comme "Solde insuffisant" ou "Utilisateur introuvable".
* Les erreurs sont renvoyées avec le code HTTP approprié (400 ou 500).

---

## Sécurité

* Authentification via JWT.
* Chaque requête API nécessitant un utilisateur authentifié doit inclure le token dans l’en-tête `Authorization: Bearer <token>`.

---

## Contributions

1. Fork le projet
2. Crée une branche : `git checkout -b feature/ma-fonctionnalité`
3. Commit tes modifications : `git commit -m 'Ajout de ma fonctionnalité'`
4. Push sur la branche : `git push origin feature/ma-fonctionnalité`
5. Ouvre une Pull Request

---

## Contact

Pour toute question ou suggestion :
**Serigne Khalifa Ababacar Sy
