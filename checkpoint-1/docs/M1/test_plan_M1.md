# Testing Plan

## Current status (M1)

- Bash script that will compile all compiler related files and test against all supplied .cm files

### Provided files

- [x] fac.cm
- [x] booltest.cm
- [x] gcd.cm
- [x] sort.cm
- [x] mutual.cm

### User files

- [x] ex1.cm
- [x] ex2.cm
- [x] ex3.cm
- [x] ex4.cm
- [x] ex5.cm

## Explanations of each file

### Ex1.cm

- This file demonstrates deep nesting of if conditions
  - [x] Generates Syntax tree
- Testing status: Working.

### Ex2.cm

- This file demonstrates error recovery from conditional syntax errors
  - [x] 1+1 == 0
  - [x] 5+5 == 10
- Testing status: Working.

### Ex3.cm

- This file contains two syntactical errors b2b
  - [x] int main(it p)
- Testing status: Working.

### Ex4.cm

- This file should throw multiple compile time errors, while creating a proper AST
  - [ ] Status of test 1
  - [ ] Status of test 2
  - [ ] Status of test 3
- Testing status: Partially working.

### Ex5.cm

- The pinnacle of unhinged behavior... This file creates a freaking arrow... yeah you heard me an arrow...
  - [x] Status of test 1
- Testing status: Working.
