# C-Minus-Compiler

Project for CIS*4650. (Compilers) Performs Lexical + Syntactic Analysis, Semantic Analysis and Code Gen

## Contributors/Status

- Ben Carlson
- Conor Roberts

Semester: W23
Current milestone: 1

## Acknowledgements/Assumptions

## Documentation/Testing

- Refer to docs folder
- Testing can be seen under `testing_plan.md`

## Build/Run

### Build/Run test files

## Dir Structure

This project includes multiple different components to it:

`/absyn`

- Represents grammar rules defined in the `cminus.cup` specification
- These can be split into declarations and expressions\

`cminus.cup`

- A list of grammar rules outlined within the cminus specification files
- These create the objects within the absyn classes

`cminus.flex`

- Regex and lexical parsing in .cm files
- This data is then piped into our cminus.cup grammar

`DisplayTreeVistor.java`

- Driver code for the abstract syntax tree created for a .cm file
