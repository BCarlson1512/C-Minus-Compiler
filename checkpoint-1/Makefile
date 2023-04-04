JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-cp /usr/share/java/cup.jar:.
CUP=cup
# JFLEX=~/Projects/jflex/bin/jflex
# CLASSPATH=-cp ~/Projects/java-cup-11b.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main

all: Main.class

Main.class: absyn/*.java parser.java sym.java Lexer.java ShowTreeVisitor.java ASMGenerator.java SemanticAnalyzer.java Scanner.java Main.java

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

M2_Ex1:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex1.cm -as

M2_Ex2:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex2.cm -as

M2_Ex3:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex3.cm -as

M2_Ex4:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex4.cm -as

M2_Ex5:
	java -cp /usr/share/java/cup.jar:. Main ./test/C2_User/Ex5.cm -as

fac:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/fac.cm -asc

gcd:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/gcd.cm -asc

booltest:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/booltest.cm -asc

mutual:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/mutual.cm -asc

sort:
	java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/sort.cm -asc

M3_Ex1:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex1.cm -c

M3_Ex2:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex2.cm -c

M3_Ex3:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex3.cm -c

M3_Ex4:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex4.cm -c

M3_Ex5:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex5.cm -c

M3_Ex6:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex6.cm -c

M3_Ex7:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex7.cm -c

M3_Ex8:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex8.cm -c

M3_Ex9:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex9.cm -c

M3_Ex10:
	java -cp /usr/share/java/cup.jar:. Main ./test/C3_User/Ex10.cm -c

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~ *.abs *.sym
