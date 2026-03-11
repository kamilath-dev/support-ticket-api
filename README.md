# Support Ticket Management Application

Une application complète de gestion de tickets de support avec frontend Nuxt.js et backend Spring Boot.

## 📋 Table des matières
- [Aperçu du projet](#aperçu-du-projet)
- [Choix techniques](#choix-techniques)
- [Prérequis](#prérequis)
- [Installation et exécution](#installation-et-exécution)
  - [Backend (Spring Boot)](#backend-spring-boot)
  - [Frontend (Nuxt.js)](#frontend-nuxtjs)
- [Fonctionnalités](#fonctionnalités)
- [Structure du projet](#structure-du-projet)
- [Améliorations possibles](#améliorations-possibles)
- [Dépannage](#dépannage)

## 🎯 Aperçu du projet

Cette application permet aux utilisateurs de :
- Créer un compte et s'authentifier (JWT)
- Créer des tickets de support
- Consulter la liste de leurs tickets
- Modifier leurs tickets
- Supprimer leurs tickets
- Visualiser les détails d'un ticket

## 🛠 Choix techniques

### Backend - Spring Boot
- **Framework** : Spring Boot 3.5.11
  - Robuste et mature pour les applications d'entreprise
  - Excellente intégration avec Spring Security et JPA
- **Sécurité** : Spring Security avec JWT
  - Authentification stateless adaptée aux API REST
  - Tokens JWT pour une scalabilité horizontale
- **Base de données** : PostgreSQL
  - Fiabilité et performances
  - Support natif des transactions ACID
- **ORM** : Spring Data JPA (Hibernate)
  - Simplifie les opérations de base de données
  - Gestion automatique des relations entre entités
- **Build tool** : Maven
  - Gestion des dépendances mature et fiable

### Frontend - Nuxt.js 3
- **Framework** : Nuxt.js 3
  - Architecture modulaire et performante
  - Support SSR/SSG pour de meilleures performances
  - Configuration simplifiée
- **State Management** : Pinia
  - Store moderne et type-safe
  - Persistance des données avec `@pinia-plugin-persistedstate`
- **Styling** : Tailwind CSS
  - Développement rapide d'interface
  - Design responsive et personnalisable
- **API Client** : $fetch avec plugin axios-like
  - Gestion centralisée des requêtes API
  - Injection automatique du token JWT
- **Notifications** : Vue Toastification
  - Feedback utilisateur élégant et non-intrusif
- **Modals** : Composants personnalisés avec Vue Teleport
  - Interface fluide sans changement de page

## 📋 Prérequis

- **Java** 17 ou supérieur
- **Node.js** 18 ou supérieur
- **PostgreSQL** 14 ou supérieur
- **Maven** 3.8 ou supérieur
- **Navigateur** moderne (Chrome, Firefox, Edge)
## Améliorations possibles

- Ajout d'un système de rôles (admin / utilisateur)
- Pagination et recherche dans la liste des tickets
- Upload de pièces jointes dans les tickets

## CREATION DE LA BASE DE DONNEE

CREATE DATABASE ticket_db;
CREATE USER ticket_user WITH PASSWORD 'ticket_password';
GRANT ALL PRIVILEGES ON DATABASE ticket_db TO ticket_user;


## 🚀 Installation et exécution

# Compiler
./mvnw clean install

# Lancer l'application
./mvnw spring-boot:run -DskipTests

### Backend (Spring Boot)

1. **Cloner et se rendre dans le dossier backend**
```bash
cd support-ticket-api