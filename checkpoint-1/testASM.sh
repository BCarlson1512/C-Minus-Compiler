echo "---- '-c' Flag Testing ----"
echo "----- Cleaning Directory -----"
make clean
echo "----- Compiling -----"
make
echo "----- Testing C1_Provided Files -----"
make fac
make booltest
make gcd
make mutual
make sort

echo "----- Testing C3_User Created Files -----"
echo "----- Testing C3_User/Ex1.cm -----"
make M3_Ex1
echo "----- Testing C2_User/Ex2.cm -----"
make M3_Ex2
echo "----- Testing C2_User/Ex3.cm -----"
make M3_Ex3
echo "----- Testing C2_User/Ex4.cm -----"
make M3_Ex4
echo "----- Testing C3_User/Ex5.cm -----"
make M3_Ex5
echo "----- Testing C3_User/Ex6.cm -----"
make M3_Ex6
echo "----- Testing C3_User/Ex7.cm -----"
make M3_Ex7
echo "----- Testing C3_User/Ex8.cm -----"
make M3_Ex8
echo "----- Testing C3_User/Ex9.cm -----"
make M3_Ex9
echo "----- Testing C3_User/Ex10.cm -----"
make M3_Ex10
