# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: sujet

scenario0:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario0.java
	java -classpath bin:bin/*  TestScenario0 cartes/carteSujet.map

scenario1:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario1.java
	java -classpath bin:bin/*  TestScenario1 cartes/carteSujet.map

sujet:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestChefPompier.java
	java -classpath bin:bin/*  TestChefPompier cartes/carteSujet.map

desert:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestChefPompier.java
	java -classpath bin:bin/*  TestChefPompier cartes/desertOfDeath-20x20.map

mush:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestChefPompier.java
	java -classpath bin:bin/*  TestChefPompier cartes/mushroomOfHell-20x20.map

mad:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestChefPompier.java
	java -classpath bin:bin/*  TestChefPompier cartes/spiralOfMadness-50x50.map

docu:
	javadoc io -sourcepath src -d ./doc

clean:
	rm -rf bin/*.class
	rm -rf bin/io/*.class
