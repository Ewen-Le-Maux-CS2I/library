# Charge le fichier .env s'il existe pour utiliser les variables en local
ifneq ("$(wildcard .env)","")
    include .env
    export
endif

.PHONY: help up down restart status logs logs-back logs-front clean-docker

# Commande par défaut (affiche l'aide)
help:
	@echo "Commandes disponibles :"
	@echo "  make up           : Démarre toute la stack (BDD, Back, Front) en arrière-plan"
	@echo "  make down         : Arrête et supprime tous les conteneurs"
	@echo "  make restart      : Redémarre proprement l'application"
	@echo "  make status       : Affiche l'état des conteneurs et les ports"
	@echo "  make logs         : Affiche les logs en temps réel de tous les services"
	@echo "  make logs-back    : Affiche uniquement les logs du Backend Spring Boot"
	@echo "  make logs-front   : Affiche uniquement les logs du Frontend Angular"
	@echo "  make clean-docker : Nettoie les conteneurs, images orphelines et volumes"
	@echo "=========================================================================="

# Démarre toute la stack d'un coup et affiche où voir l'application
up:
	@echo "Démarrage des containers"
	docker compose up -d --build
	@echo "Démarrage terminé "
	@echo "Application accessible à : http://localhost:8080"

# Arrête l'application
down:
	@echo "Arrêt et suppression des conteneurs..."
	docker compose down
	@echo "L'application est arrêtée."

# Redémarre l'application
restart: down up

# Vérifie le statut de la stack (et la santé du healthcheck MariaDB)
status:
	@echo "État actuel des conteneurs :"
	docker compose ps

# Voir tous les logs
logs:
	docker compose logs -f

# Voir uniquement les logs du Backend (Spring Boot)
logs-back:
	docker compose logs -f backend

# Voir uniquement les logs du Frontend (Angular / Nginx)
logs-front:
	docker compose logs -f frontend

# Nettoyage en profondeur de Docker
clean-docker:
	@echo "Nettoyage de Docker"
	docker compose down -v
	docker system prune -f
	@echo "Nettoyage terminé."