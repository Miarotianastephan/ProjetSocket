# ProjetSocket
Socket_FileTransfer
--Fonctionalitées:
  Client Socket(Client.java+Myaction.java+progress.java):
  -champ pour entrer le port et l'adresse de connexion au serveur
  -bounton choose :selection du fichier à envoyer: .mp3,.mp4,....
  -bouton send: envoie du fichier si avy misafidy
  
  Server socket(Server.java):
  -Interface d'entrer du port d'ecoute utilisé par le serveur
  -le serveur recoit les fichiers envoyées par le client et effectue une download dans le chemin specifié
  
  Fichier de configuration: "param.ini" --> à éditer pour choisir l'emplacement du sauvegarde  
