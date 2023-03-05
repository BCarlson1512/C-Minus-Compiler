/*
  Created by: Fei Song
  File Name: Main.java
  To Build: 
  After the Scanner.java, cminus.flex, and cminus.cup have been processed, do:
    javac Main.java
  
  To Run: 
    java -classpath /usr/share/java/cup.jar:. Main gcd.cm

  where gcd.tiny is an test input file for the tiny language.
*/
   
import java.io.*;
import absyn.*;
   
class Main {
  public final static boolean SHOW_TREE = true;
  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      if (argv[0]=="-a" && argv.length==2){
        // Get file
        // Make sure file exists
        // Second arg is input file
        // Output file is INPUT_FILE.abs
        // Make sure errors print to stderr

        // TODO double check this bad boy
        System.setOut(new PrintStream(new FileOutputStream(argv[1]+".abs")));
      }
      Lexer scanner = new Lexer(new FileReader(argv[0])); 
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);      
      if (SHOW_TREE && result != null) {
         System.out.println("The abstract syntax tree is:");
         ShowTreeVisitor visitor = new ShowTreeVisitor();
         result.accept(visitor, 0); 
      }
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}


