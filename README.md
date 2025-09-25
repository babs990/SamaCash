# Samacash

**Samacash** est une application de transfert d'argent qui permet aux utilisateurs de gérer leur compte, d'effectuer des dépôts, des retraits et des transferts d'argent entre utilisateurs en toute sécurité.

---

## Table des matières

* [Technologies](#technologies)
* [Installation](#installation)
* [Configuration](#configuration)
* [Lancement](#lancement)
* [Fonctionnalités](#fonctionnalités)
* [Architecture](#architecture)
* [API Endpoints](#api-endpoints)
* [Licence](#licence)

---

## Technologies

Le projet utilise les technologies suivantes :

* **Backend :** Java, Spring Boot, Spring Security, Hibernate, JPA
* **Base de données :** MySQL (ou PostgreSQL selon le choix)
* **Frontend :** Angular (standalone components)
* **Authentification :** JWT (JSON Web Token)
* **Gestion des dépendances :** Maven pour le backend, npm pour le frontend

---

## Installation

1. **Cloner le projet :**

```bash
git clone <URL_DE_VOTRE_REPO>
cd samacash
```

2. **Backend :**

   * Naviguer dans le dossier backend
   * Installer les dépendances Maven et compiler :

```bash
mvn clean install
```

3. **Frontend :**

   * Naviguer dans le dossier frontend
   * Installer les dépendances Angular :

```bash
npm install
```

---

## Configuration

* Créer une base de données nommée `samacash` (ou selon le fichier `application.properties`)
* Configurer les paramètres de connexion à la base de données dans `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/samacash
spring.datasource.username=root
spring.datasource.password=motdepasse
spring.jpa.hibernate.ddl-auto=update
```

* Ajouter les configurations d’envoi de mail si nécessaire (pour notifications)

---

## Lancement

1. **Backend :**

```bash
mvn spring-boot:run
```

2. **Frontend :**

```bash
ng serve
```

* Le frontend sera accessible par défaut sur `http://localhost:4200`
* Le backend sera sur `http://localhost:8080`

---

## Fonctionnalités

* Création de compte utilisateur et connexion sécurisée
* Consultation du solde du compte
* Historique des transactions
* Dépôt d'argent
* Retrait d'argent
* Transfert d'argent entre utilisateurs avec frais calculés automatiquement
* Gestion des erreurs et notifications

---

## Architecture

* **Entities :** `AppUser`, `Account`, `Transaction`
* **Repository :** Interfaces JPA pour accéder aux données
* **Service :** Logique métier (transfert, dépôt, retrait)
* **Controller :** Endpoints REST pour le frontend
* **Security :** JWT pour sécuriser les endpoints

---

## API Endpoints

| Méthode | Endpoint               | Description                             |
| ------- | ---------------------- | --------------------------------------- |
| POST    | /auth/register         | Création d’un compte utilisateur        |
| POST    | /auth/login            | Connexion et récupération du token      |
| GET     | /account/balance       | Récupérer le solde du compte            |
| GET     | /transactions          | Historique des transactions             |
| POST    | /transactions/send     | Transfert d'argent vers un autre compte |
| POST    | /transactions/deposit  | Dépôt d'argent sur le compte            |
| POST    | /transactions/withdraw | Retrait d'argent du compte              |

---

## Licence

Ce projet est sous licence MIT.
Vous êtes libre de le modifier, le distribuer et l'utiliser à des fins personnelles ou professionnelles.

---

**Samacash** – Simplifiez vos transferts d'argent en toute sécurité.

Pour toute question ou suggestion :
**Serigne Khalifa Ababacar Sy
