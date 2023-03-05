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
      String fileName = argv[0];

      // Should we output the abstract syntax tree in a .abs file?
      Boolean outputAbsyn = false;

      // Do we have -a in our args list?
      if (argv[0].equals("-a")) {
        fileName = argv[1];
        outputAbsyn = true;
      } else if (argv[1].equals("-a")) {
        fileName = argv[0];
        outputAbsyn = true;
      }

      if (outputAbsyn) {
        String[] pathSegments = fileName.split("/");
        String lastSegment = pathSegments[pathSegments.length - 1];

        // The output file name is the same as the input file name, but with a different file extension.
        String strippedLastSegment = lastSegment.substring(0, lastSegment.length() - 3);

        System.setOut(new PrintStream(new FileOutputStream(strippedLastSegment + ".abs")));
      }

      Lexer scanner = new Lexer(new FileReader(fileName));
      parser p = new parser(new Lexer(new FileReader(fileName)));
      Absyn result = (Absyn) (p.parse().value);
      if (SHOW_TREE && result != null) {
        System.out.println("The abstract syntax tree is:");
        ShowTreeVisitor visitor = new ShowTreeVisitor();
        result.accept(visitor, 0);
      }
    } catch (

    Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}
