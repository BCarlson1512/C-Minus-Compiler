# Testing Plan

## Current status (M2)

- Bash script that will compile all compiler related files and test against all supplied .cm files
- Bash script that tests exclusively the -a flag
- Bash script that tests exclusively the -s flag (WIP)
- Bash script that tests -a and -s together (WIP)

### Provided files

- [ ] fac.cm
- [ ] booltest.cm
- [ ] gcd.cm
- [ ] sort.cm
- [ ] mutual.cm

### User files

- [ ] ex1.cm
- [ ] ex2.cm
- [ ] ex3.cm
- [ ] ex4.cm
- [ ] ex5.cm

## Explanations of each file

### Ex1.cm

- This file demonstrates deep nesting of if conditions
  - [x] Generates Syntax tree
  - [ ] Generates Symbol table
- Testing status: Not Working.

### Ex2.cm

- This file demonstrates error recovery from conditional syntax errors
  - [x] 1+1 == 0
  - [x] 5+5 == 10
  - [x] Generates Syntax Tree
  - [ ] Generates Symbol table
- Testing status: Not Working.

### Ex3.cm

- This file contains two syntactical errors b2b
  - [x] int main(it p)
  - [x] Generates Syntax Tree
  - [ ] Generates Symbol Table
- Testing status: Not Working.

### Ex4.cm

- This file should throw multiple compile time errors, while creating a proper AST
  - [x] Status of test 1
  - [x] Generates syntax tree
  - [ ] Generates symbol table
- Testing status: Not Working.

### Ex5.cm

- The pinnacle of unhinged behavior... This file creates a freaking arrow... yeah you heard me an arrow...
  - [x] Status of test 1
  - [x] Generates syntax tree
  - [ ] Generates symbol table
- Testing status: Not Working.
