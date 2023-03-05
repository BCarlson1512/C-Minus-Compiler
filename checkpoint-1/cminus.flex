/*
  Created By: Ben Carlson and Conor Roberts.
  File Name: cminus.flex
  To Build: jflex cminus.flex

  and then after the parser is created
    javac Lexer.java
*/
   
/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;
      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* 
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java. 
*/
%class Lexer

%eofval{
  return null;
%eofval};

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column
    
/* 
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup
   
/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{   
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/*
  Macro Declarations
  
  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.  
*/
   
/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or form feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]
   
ID = [_a-zA-Z][_a-zA-Z0-9]*
NUM = [0-9]+

TRUTH = true|false

// should work on all classic c-style comments
COMMENT = \/\*.*?\*\/

   
%%
/* ------------------------Lexical Rules Section---------------------- */
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */

"bool"            {return symbol(sym.BOOL);}
"else"            {return symbol(sym.ELSE);}
"if"              {return symbol(sym.IF);}
"int"             {return symbol(sym.INT);}
"return"          {return symbol(sym.RETURN);}
"void"            {return symbol(sym.VOID);}
"while"           {return symbol(sym.WHILE);}

"="            {return symbol(sym.EQ);}
"<"            {return symbol(sym.LT);}
">"              {return symbol(sym.GT);}
"<="             {return symbol(sym.LTE);}
">="          {return symbol(sym.GTE);}
"!="            {return symbol(sym.NOTEQ);}
"=="           {return symbol(sym.EQEQ);}
"~"           {return symbol(sym.NOT);}

"&&"           {return symbol(sym.AND);}
"||"           {return symbol(sym.OR);}

";"            {return symbol(sym.SEMI);}
","           {return symbol(sym.COMMA);}

"("            {return symbol(sym.LEFT_BRAC);}
")"           {return symbol(sym.RIGHT_BRAC);}
"{"            {return symbol(sym.LEFT_CURLY_BRAC);}
"}"           {return symbol(sym.RIGHT_CURLY_BRAC);}
"["            {return symbol(sym.LEFT_SQ_BRAC);}
"]"           {return symbol(sym.RIGHT_SQ_BRAC);}

"+"            {return symbol(sym.PLUS);}
"-"           {return symbol(sym.MINUS);}
"*"            {return symbol(sym.TIMES);}
"/"           {return symbol(sym.DIVIDE);}

{TRUTH}           {return symbol(sym.TRUTH, yytext());}
{ID}              { return symbol(sym.ID, yytext()); }
{NUM}             { return symbol(sym.NUM, Integer.parseInt(yytext())); }
{WhiteSpace}           { /* Skip Whitespace */  }
{COMMENT}         { /* Do Nothing */  }

// .                 {return symbol(sym.ERROR)}
.                 {/**/}