### Auteurs : LÊ Van Khôi et SALAND Maxime

## Fonctionnalités développées
* Recherche de cocktails avec filtrage pour obtenir uniquement les boissons non alcoolisées
* Liste des catégories
* Liste des ingrédients
* Ajout de cocktail en favoris
* Sélection aléatoire de cocktail

## Difficultés rencontrées
Côté backend, les principales difficultés ont porté sur la désérialisation des données reçues de l'API. Il a fallu créer une classe pour désérialiser afin de récupérer les données dans un format simple (au lieu des 16 champs d'ingrédients et de mesures). Un autre challenge a été d'utiliser correctement des generics afin de factoriser les appels à l'API (fonction enqueue dans le fichier CocktailRepository. Bien que ça n'était pas évident, cela a permis de factoriser grandement le code des appels à l'API.

Un autre problème problème a été de vouloir utiliser la composant SearchBar de material design 3 car nous ne sommes pas parvenu à la faire fonctionner (il manquait des méthodes telle que onQueryTextSubmit). Nous avons donc décidé d'utiliser une simple SearchView pour implémenter la barre de recherche.

La mise en place des Adapters et leur fonctionnement était un peu difficile au départ.
Pour le filtrage des boissons non alcoolisées : impossible de le faire pour les onglets catégories et ingrédients car l'api ne renvoie pas assez d'information quand on utilise les routes pour récupérer les cocktails via une catégorie ou un ingrédient.
Enfin, il était assez difficile d'éviter la duplication de code pour les fragments.