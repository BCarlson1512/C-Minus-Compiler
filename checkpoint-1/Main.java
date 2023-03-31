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

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import absyn.Absyn;

class Main {
  public final static boolean SHOW_TREE = true;

  public static boolean outputAbsyn = false;
  public static boolean outputSymbolTable = false;
  public static boolean generateASM = false;

  static public void main(String argv[]) {
    /* Start the parser */
    try {
      processArgs();
      String fileName = argv[0];
      PrintStream stdout = System.out;

      //Boolean outputAbsyn = false;
      //Boolean outputSymbolTable = false;
      //Boolean generateASM = false;

      // Set booleans based on command line arguments.
      /*
      for (Integer i = 0; i < argv.length; i++) {
        if (argv[i].equals("-s")) { // -s and can happen continuously
          outputSymbolTable = true;
        } else if (argv[i].equals("-a")) {
          outputAbsyn = true;
        } else if (argv[i].equals("-sa") || argv[i].equals("-as")) {
          outputSymbolTable = true;
          outputAbsyn = true;
        } else if (argv[i].endsWith(".cm")) {
          fileName = argv[i];
        }
      }
      */
      String[] pathSegments = fileName.split("/");
      String lastSegment = pathSegments[pathSegments.length - 1];

      // The output file name is the same as the input file name, but with a different
      // file extension.
      String outputBaseFileName = lastSegment.substring(0, lastSegment.length() - 3);

      Lexer scanner = new Lexer(new FileReader(fileName));
      parser p = new parser(new Lexer(new FileReader(fileName)));

      p.outputSymbolTable = outputSymbolTable;
      p.generateASM = generateASM;

      if (generateASM) {
        System.setOut(new PrintStream(new FileOutputStream(outputBaseFileName + ".tm")));
      } else {
        System.setOut(stdout);
      }

      if (outputSymbolTable) {
        System.setOut(new PrintStream(new FileOutputStream(outputBaseFileName + ".sym")));
      } else {
        System.setOut(stdout);
      }

      Absyn result = (Absyn) (p.parse().value);

      if (outputAbsyn) {
        System.setOut(new PrintStream(new FileOutputStream(outputBaseFileName + ".abs")));
      } else {
        System.setOut(stdout);
      }

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

public void processArgs(String args[]) {
  for (String arg : args) {
    if (!arg.contains("-")) {
      continue;
    }
    outputAbsyn = arg.contains("a");
    outputSymbolTable = arg.contains("s");
    generateASM = arg.contains("c");
  }
}
