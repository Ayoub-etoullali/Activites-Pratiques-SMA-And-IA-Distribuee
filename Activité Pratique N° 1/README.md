# Jeu multi joueurs avec les SMA

## Activité pratique :
On souhaite à travers cette activité pratique de développer un simple jeu en utilisant les systèmes multi agents. Ce jeu multi joueurs permet de déterminer un nombre magique secret par des joueurs. Pour cela, définir un nombre magique (un nombre secret), c’est un nombre généré aléatoirement entre 0 et 100, utiliser la méthode statique random de la classe Math. Puis les joueurs vont tenter de trouver le nombre magique. Pour chaque tentation il faut indiquer au joueur s'il est en dessus ou au-dessous du nombre magique. <br>

Pour réaliser ce jeu, vous créez trois agents :
  #### Agent serveur : 
  Qui va générer aléatoirement le nombre magique et qui va recevoir les tentations des joueurs, une fois un joueur trouve le nombre magique, cet agent va envoyer un message à tous les joueurs pour l’informer du nombre magique trouvé et arrête le jeu, sinon va indiquer au joueur s'il est en dessus ou au-dessous du nombre magique.
  #### Agent joueur 1 : 
  Cet agent possède une interface graphique avec JavaFX, contenant une zone de texte pour saisir le nombre, un boutton pour envoyer le nombre et une liste pour afficher les messages envoyés par l’agent serveur.
  #### Agent joueur 2 : 
  Le même que l’agent joueur 1, cet agent va jouer contrer l’Agent joueur 1 et qui possède également une interface graphique JavaFX pour saisir un nombre et l’envoyer vers l’agent serveur pour tenter de trouver le nombre magique et une liste pour visualiser les messages envoyés par l’agent serveur.

## Demo :
<div align="center">
       <p>
       <sup>  <strong>Vidéo -</strong> Jeu multi joueurs avec les SMA</sup>
       </p>
</div>

<kbd>Enjoy Code</kbd> 👨‍💻
