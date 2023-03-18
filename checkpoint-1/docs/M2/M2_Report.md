# Checkpoint/Milestone 2 Report

## Summary (TLDR)

This milestone aims to perform type checking and semantical analysis on c- programs.

## Techniques Used

Similar to the last milestone, we attempted to test code as it was built in iterations, but ultimately ended up testing at the end once all modules were built.
We attempted to follow SOLID and OOP principles to produce the best quality code we could as a team of two.

## Learning experiences

## Assumptions/Limitations

This program requires the following resources

- Jflex
- Cup
- Java

This program will generate a syntax tree of the following code files:

- `.cm` c minus file extensions

Assumptions:

- Declarations have to come before statements
  - ie: `int i;` needs to come before `if (v == 0)`
  - Void arrays are considered invalid
  - empty void variables are considered invalid

Limitations:

- This program can only handle a much smaller subset of keywords, as seen within the c programming language, by design in order to minimize the scope of this project

## Possible Improvements

- More in-depth semantical analysis
- Better testing scripts
- Output to sym and abs folders, rather than dumping in the project

## Contributions

### Conor

### Ben

- Create package symbol, containing related symbol table classes
- Create SymbolTable.java the data structure driving our compiler

## Acknowledgements

This project was heavily based on/inspired by resources provided by Dr. Fei Song's compilers content. We were provided a rough idea of the implementation process as well as a portion of starter code. The grammars created in CUP are heavily based off the the C- specs. The absyn class structure is heavily based off of lecture content.
