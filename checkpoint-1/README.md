# C-Minus-Compiler

Project for CIS*4650. (Compilers) Performs Lexical + Syntactic Analysis, Semantic Analysis and Code Gen

## Contributors/Status

- Ben Carlson
- Conor Roberts

Semester: W23
Current milestone: 2

## Acknowledgements/Assumptions

- This program has a couple of kinks within it
- Within the symbol table/parser we do not pick up global variables, but global arrays work fine
- Functions work, function protos dont
- 

## Documentation/Testing

- Refer to docs folder
- M1
  - Testing can be seen under `testing_plan.md`
- M2
  - Refer to  M2_Report

## Build/Run

You have two options for running this project:

- 1: use the test scripts (test.sh) and (testast.sh)
  - these test the ability to print the ast to stdout and to files
- 2: use makefile rules
  - See makefile for further explanation

### Build/Run test files

see above

## Dir Structure

This project includes multiple different components to it:

`/absyn`

- Represents grammar rules defined in the `cminus.cup` specification
- These can be split into declarations and expressions\

`/symbol`

- Represents symbol table classes
- Array, Variable, Function

`cminus.cup`

- A list of grammar rules outlined within the cminus specification files
- These create the objects within the absyn classes

`cminus.flex`

- Regex and lexical parsing in .cm files
- This data is then piped into our cminus.cup grammar

`ShowTreeVistor.java`

- Driver code for the abstract syntax tree created for a .cm file

`SemanticAnalyzer.java`

- Performs semantic analysis on .cm files

`SymbolTable.java`

- The data structure driving semantic analyzer
