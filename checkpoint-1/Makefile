JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-cp /usr/share/java/cup.jar:.
CUP=cup
# JFLEX=~/Projects/jflex/bin/jflex
# CLASSPATH=-cp ~/Projects/java-cup-11b.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main

all: Main.class

Main.class: absyn/*.java parser.java sym.java Lexer.java ShowTreeVisitor.java Scanner.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: cminus.flex
	$(JFLEX) cminus.flex

parser.java: cminus.cup
	#$(CUP) -dump -expect 3 cminus.cup
	$(CUP) -expect 3 cminus.cup

Ex1:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex1.cm -a

Ex2:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex2.cm -a

Ex3:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex3.cm -a

Ex4:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex4.cm -a

Ex5:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex5.cm -a

Ex1_sym:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex1.cm -a -s

Ex2_sym:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex2.cm -a -s

Ex3_sym:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex3.cm -a -s

Ex4_sym:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex4.cm -a -s

Ex5_sym:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex5.cm -a -s

fac:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/fac.cm -a -s

gcd:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/gcd.cm -a -s

booltest:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/booltest.cm -a -s

mutual:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/mutual.cm -a -s

sort:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/sort.cm -a -s

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~ *.abs *.sym
