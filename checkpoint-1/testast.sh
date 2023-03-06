echo "---- '-a' Flag Testing ----"
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

echo "----- Testing C1_User Created Files -----"
make Ex1
make Ex2
make Ex3
make Ex4
make Ex5
