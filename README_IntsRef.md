### Documentation des tests pour IntsRef.java dans storage.IntsRefTest.java

![Mutation Score - New Tests](images/image-1.png)
Score de mutation avec nouveaux tests pour classe IntsRef: 12%.

![Mutation Score - Original Tests](images/image-2.png)
Score de mutation avec tests originaux pour classe IntsRef: 26%.

![Conditional Negation Mutant - Null Array](images/image.png)
Ce mutant de négation du conditionnel a été détecté puisque "ints" est configuré à null dans le test.

![Conditional Mutants - Negative Length](images/image-3.png)
Ces mutants de conditionnels ont été détectés puisque length a été configuré à -1, donc, sera vrai seulement pour conditionnel acceptant nombre négatif.

![Conditional Mutants - Length Out of Bounds](images/image-4.png)
Ces mutants de conditionnels ont été détectés puisque length=5 est plus grand que ints.length=0, donc, sera vrai seulement pour conditionnel length plus grand que ints.length.

![Conditional Negation Mutant - Negative Offset](images/image-5.png)
Ce mutant de négation du conditionnel a été détecté puisque "offset" est configuré entre -10 et -1 dans le test.

![Conditional Mutants - Offset Out of Bounds](images/image-6.png)
Ces mutants de conditionnels ont été détectés puisque "offset" est configuré entre 5 et 10 et ints.length = 0 dans le test.
