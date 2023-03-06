echo "----- Cleaning Directory -----"
make clean
echo "----- Compiling -----"
make
echo "----- Testing C1_Provided Files -----"
java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/fac.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/booltest.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/gcd.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/mutual.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_Provided/sort.cm

echo "----- Testing C1_User Created Files -----"
java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex1.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex2.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex3.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex4.cm
java -cp /usr/share/java/cup.jar:. Main ./test/C1_User/Ex5.cm
