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

--- 
