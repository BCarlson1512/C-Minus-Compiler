echo "---- '-s' Flag Testing ----"
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

echo "----- Testing C2_User Created Files -----"
echo "----- Testing C2_User/Ex1.cm -----"
make M2_Ex1
echo "----- Testing C2_User/Ex2.cm -----"
make M2_Ex2
echo "----- Testing C2_User/Ex3.cm -----"
make M2_Ex3
echo "----- Testing C2_User/Ex4.cm -----"
make M2_Ex4
echo "----- Testing C2_User/Ex5.cm -----"
make M2_Ex5
