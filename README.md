# RESTRMI

## Créer le projet 
Créer des projet Java normaux, ne pas mettre de nom de projet, à la place definissez la location des sources : `ServerCarRenting` et `ClientCarRenting`.
Verifier que le fichier output est bien bin

## Faire marcher le dynamic web service
1. Transformer le projet en Dynamic web service : click droit sur le projet -> Configure -> Facets  -> verifier que uniqument Java et dynamic web module ne soient cochés
Si cette étape ne marche pas créer un nouveau projet dynamic web

2. Ajouter les config de la VM pour éclispe, définir les propriétés : `-Djava.rmi.server.codebase` et `-Djava.security.policy`
Pour définir les propriétés : click droit sur la classe MLVCarsService -> Run As -> Run Configuration -> Arguments -> VM Arguments

```
exmaple :
-Djava.rmi.server.codebase="file:/Users/christophechheang/Documents/cours/rest/project/RESTRMI/ServerAndClient/ServerCarRenting/bin/" -Djava.security.manager 
-Djava.security.policy="file:/Users/christophechheang/Documents/cours/rest/project/RESTRMI/ServerAndClient/ServerCarRenting/bin/grant.policy"
```

Après avoir créer le web service, si vous testez n'oubliez pas de lancer le serveur et un client pour que des véhicules soit dans la base.

## Pour tester le RMI :
Modifier le codebase si vos fichier n'ont pas la disposition suivante :
```
ServerAndClient
├── ClientCarRenting
|   ├── bin
|   └── src
├── ServerCarRenting
|   ├── bin
|   └── src
```
Si vous avez cette achitecture : se placer dans les dossiers bin de chaque projet et lancer `rmicregistry`, `java CarServer` et `java Carclient`

## Si la branch OnlyRmi n'est pas celle chargée avec un pull : il faut changer de branche

Utiliser la branche OnlyRmi : 

### Recupérer la branche Only RMI
```
git fetch
git branch -a
```
Cet appel renvoie toutes les branches présentes : `remotes/origin/Only-RMI` est la branche qui nous intéresse

### Se placer sur la branch
```
git checkout -t remotes/origin/Only-RMI
```
l'option -t synchronise la branch local et distante
