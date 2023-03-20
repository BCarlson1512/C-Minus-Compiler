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
make Ex1_sym
make Ex2_sym
make Ex3_sym
make Ex4_sym
make Ex5_sym
