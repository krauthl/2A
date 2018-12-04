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

# all: invader lecture interface
#
# invader:
# 	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestInvader.java
# 	java -classpath bin:bin/gui.jar TestInvader
#

all : lecture

lecture:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestLecteurDonnees.java
	java -classpath bin:bin/* TestLecteurDonnees cartes/carteSujet.map

evenement:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestEvenement.java
	java -classpath bin:bin/* TestEvenement cartes/carteSujet.map senario/scenario0.txt

scenario0:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario0.java
	java -classpath bin:bin/* TestScenario0 cartes/spiralOfMadness.map senario/scenario0.txt

scenario1:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario1.java
	java -classpath bin:bin/* TestScenario1 cartes/carteSujet.map senario/scenario1.txt

elementaire:
		javac -d bin -classpath bin/gui.jar -sourcepath src src/TestStrategieElementaire.java
		java -classpath bin:bin/* TestStrategieElementaire cartes/carteSujet.map

clean:
	rm -rf bin/*.class
	rm -rf bin/io/*.class
