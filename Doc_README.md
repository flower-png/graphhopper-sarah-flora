# Documentation des tests
## Consignes
Documenter précisément chaque cas de test: 
* nom du test
* intention du test (quel comportement est testé)
* motivation des données de test choisies
* explication de l'oracle (comment déterminer le comportement attendu).
* calculer le score de mutation avec les tests originaux pour les classes sélectionnées
* calculer le score de mutation avec les nouveaux tests et déterminer si les nouveaux tests détectent de nouveaux mutants. Si oui, expliquez quels mutants sont détectés et pourquoi. Si non, ajoutez des tests pour détecter au moins 2 nouveaux mutants et documentez / justifiez votre démarche.

## Profile.java (tests dans GraphHopperProfileTest.java)

#### 1. `testHashCodeEqualHash()`
* **Intention:**   
Vérifier que la méthode `hashCode()` retourne le même hashage pour le même objet Profile.  
* **Motivation des données:**   
On hash 2 fois le nom du profile sans modification entre les 2 appels pour voir si on a le même hashage.  
* **Explication oracle:**   
On compare les deux hash et vérifie que c'est la même valeur.  
Un mutant a été détecté par pitest.   
replaced int return with 0 for com/graphhopper/config/Profile::hashCode → KILLED

#### 2. `testHashCodeDifferentHash()`  
* **Intention:**   
Vérifier que la méthode `hashCode()` des valeurs différentes pour des objets Profile différents.  
* **Motivation des données:**   
On teste avec 2 objets Profile avec des noms différents. Puisque la méthode `hashCode()` dépend du name et hash le nom. Donc pour que les hashs soient différents on utilise des noms différents.   
* **Explication oracle:**   
On compare les 2 hashages et vérifie que c'est des valeurs retournées différentes. 

#### 3. `testEqualSameObject()`  
* **Intention:**   
Vérifier le cas du premier if dans la méthode `equals(Object o)`.  
* **Motivation des données:**   
On utilise 1 objet Profile parce qu'on veut le comparer à lui-même.  
* **Explication oracle:**   
L'objet doit être équivalent à lui même.

#### 4. `testEqualNullObject()`  
* **Intention:**  
Vérifier le cas du 2ème if dans la méthode `equals(Object o)`, spécifiquement la première condition dans le OU logique. On veut vérifier que la méthode retourne False quand on compare un objet Profile à null.  
* **Motivation:**   
On utilise un objet Profile pour pouvoir appeler la méthode `equals(Object o)` et pour la comparer à null.   
* **Explication oracle:**   
La méthode retourne false car, un objet null n'est pas équivalent à un objet Profile. 

#### 5. `testEqualDifferentClass()`  
* **Intention:**   
Vérifier le cas du 2ème if dans la méthode `equals(Object o)`, spécifiquement la deuxième condition dans le OU logique. On veut vérifier que la méthode retourne False quand on compare un objet Profile à un autre objet qui n'est pas de la même classe.    
* **Motivation:**   
On utilise un objet Profile et un objet quelconque, on a choisi string abitrairement, pour les comparer.   
* **Explication oracle:**  
La méthode retourne false car, un objet Profile n'est pas équivalents à un autre objet d'une classe différente.   

#### 6. `testEqualSameClassDifferentObject()`  
* **Intention:**   
Vérifier que pour deux objets Profile différents la méthode `equals(Object o)` retourne false (le dernier cas après les if).
* **Motivation des données:**   
On a utilisé 2 objets Profile avec des noms différents. Puisque la méthode vérifie seulement si les noms sont les mêmes.  
* **Explication oracle:**   
La méthode retourne false car, les noms des profiles sont différents alors elles ne sont pas équivalentes. 

#### 7. `testEqualSameNameDifferentObject()`  
* **Intention:**  
Vérifier que la méthode retourne True pour deux objets Profile différents mais avec le même nom.
* **Motivation des données:**   
On a utilisé 2 objets Profile différent mais avec les mêmes noms. Puisque la méthode vérifie seulement si les noms sont les mêmes.  
* **Explication oracle:**   
La méthode retourne True car, les noms des profiles sont les mêmes alors elles seronts équivalentes. 

### Mutations
#### `Profile.java`

|                 | Avant |Après|
|-----------------|---------- |-----|
|Score de mutation|![alt text](images/profile/pitTestProfilebefore.png)| ![alt text](images/profile/pitTestProfileAfter.png)|
|Détection mutants| ![alt text](images/profile/fullSurvivingMutantsBefore.png)| ![alt text](images/profile/survivingMutantsAfter.png)| 

Après l'ajout des nouveaux test, les tests on a pu détecté 8 mutants. 
Nouveaux mutants découverts: 
#### Dans `equals()`  
```
if (this == o) return true;
```
1.  replaced boolean return with false   
Dans le `testEqualSameObject()`, on a que le profile est égale à soi-même. Ce qui est supposé retourner True, mais la fonction `equals()` retourne False ce qui échoue le assertTrue du test `testEqualSameObject()`.

2. negated conditional   
Ici on change `this == o` à `this != o return true`. Pour les cas où l'objet Profile n'égale pas à `o` ça va retourner `false` quand on attend un `true`.  
Par exemple, dans `testEqualNullObjetc()`avec `this != o return true`, on aurait que la fonction `equals`retourne `true` quand on attend `false`. 
  
```
if (o == null || getClass() != o.getClass()) return false;
```
3. negated conditional  
`(o != null || getClass() != o.getClass()) return false`   
Si on test `testEqualNullObject()` notre `null` passera à la dernière ligne de code où on compare le nom des objets. Puisque le null n'a pas de nom il va envoyer une exception ce qui fait que le test échoue.   
[source](https://stackoverflow.com/questions/68808710/how-to-know-if-test-was-killed-by-junit-assertion-error-in-pit-mutation-testing)

4. replaced boolean return with true  
Si `o == null` est vrai ça retourne `true` mais ça ne marche pas. Le test échoué, car en attend un false.  
Idem pour si `o` n'est pas de la même classe qu'un objet Profile.  

5. negated conditional  
`(o == null || getClass() == o.getClass()) return false`  
Selon le test `testEqualSameNameDifferentObject()`, profile et profile2 sont de la même classe. Alors, `getClass() == o` est vrai et la méthode `equals()` va retourner false mais nous attendons `true`. Donc le test va échoué. 

```
return name.equals(profile.name);
```
6. replaced boolean return with true  
Pour les tests qui attendent `false`, les tests vont échoués. 

7. replaced boolean return with false  
Pour les tests qui attendent `true`, les tests vont échoués

#### Dans `hashCode()`
```
return name.hashCode();
```
8. replaced int return with 0  
Pour le test `testHashCodeDifferentHash`, on s'attend à ce que les hash soient différents, mais ils sont tous une valeur de 0 à cause de la mutation ce qui fait que le test échoue. 


--- 

