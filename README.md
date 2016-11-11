# RESTRMI
## Pour tester le RMI :
Modifier le code base si vos fichier n'ont pas la disposition suivante :
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
git branch -a
```
Cet appel renvoie toutes les branches présentes : `remotes/origin/Only-RMI` est la branche qui nous intéresse

### Se placer sur la branch
```
git checkout -t remotes/origin/Only-RMI
```
l'option -t synchronise la branch local et distante
