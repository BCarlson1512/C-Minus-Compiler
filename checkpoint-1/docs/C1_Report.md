# Checkpoint/Milestone 1 Report

## Summary (TLDR)

This checkpoint/milestone involved the creation of a scanner, parser and abstract syntax tree for the C- language.
The primary deliverables for this milestone are as follows:

- Create a scanner that generates a sequence of relevant tokens
- Create a parser, utilizing CUP that generates and shows an abstract syntax tree
- Create robust error recovery, that will report errors and allow the parser to recover

Many principles, including the production rules and scanner regex within cminus.cup and cminus.flex respectively were created from the C- specs file provided.

Our program will take a .cm file, scan it into a relevant stream of tokens via JFlex and then parse it into an AST utilizing CUP.

### C minus features

- Reserved keywords
  - else, if, while
  - types: void, int, bool
  - +, -, /, \*

## Techniques Used

When creating the program, we attempted to follow best practices of OOP, abiding to SOLID. Each stage of the development process was done incrementally, followed by testing functionality once each segment of the program was created.

### Basic Understanding/Design

Following the warm up assignment, we had a working understanding of JFlex and how to utilize it to our advantage. This was only the tip of the iceberg for this milestone, where we had to read docs/experiment with CUP and the provided files to further our understanding. This was done by comparing specs for the tiny language to the c minus specs and then translating rules accordingly.

### Scanner

After getting a base understanding of the file structure and everything related, we started by tackling the scanner portion of the project, considering we already had a solid understanding of JFlex. This required a little bit of preliminary setup in the cminus.cup file so that we could return the correct output tokens as opposed to our previously created token class.

### Absyn

Once the scanner was built, we split into two. The parser and absyn classes could be built without needing each other. Building absyn was primarily done by referencing the lecture slides material. The goal was to build the most in depth, but maintainable class structure since we are only a team of 2 creating this project. Overall this took two different attempts, since a few classes were missed on the first pass, being quickly resolved within the second pass through.

### Parser


### Debugging

We grouped up again and pair programmed the remaining aspects of this milestone as well as debugging the entire program start-to-finish. This program was hard to debug in separate modules, due to the output of Scanner.java and its relation to cup. We ended up clearing all of our errors and warnings before moving onto testing our provided and custom files.

## Learning experiences

Overall this project had a few key takeaways. We were able to learn about different modules of a compiler and how they all interact with each other under the hood. This was seen through the creation of the scanner, cup parser and absyn classes. Through the error recovery portion of this project, we learned how compilers verify program structure and syntax.

This assignment like others, has always been a very good example of why to use version control in groups. We opted for git, and without it our development process would have taken much longer and been far less organized. This also allowed us to work asynchronously on the project, as our schedules did not always line up.

## Assumptions/Limitations

This program requires the following resources

- Jflex
- Cup
- Java

This program will generate a syntax tree of the following code files:

- `.cm` c minus file extensions

Assumptions:

-

Limitations:

- This program can only handle a much smaller subset of keywords, as seen within the c programming language, by design in order to minimize the scope of this project

## Possible Improvements

- Further optimizations within our grammar
- More detailed error reporting

## Contributions

### Conor

- Implemented CUP matching rules
- Aided in debugging CUP rules
- Extended JFlex rules

### Ben

- Created AST
- Created Tree visitor functions
- Wrote Scanner code
- Debugging
  - Refactored cup rules
- Create documentation


## Acknowledgements

This project was heavily based on/inspired by resources provided by Dr. Fei Song's compilers content. We were provided a rough idea of the implementation process as well as a portion of starter code. The grammars created in CUP are heavily based off the the C- specs. The absyn class structure is heavily based off of lecture content.
