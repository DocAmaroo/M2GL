# AnalyseAvecSpoon
Basic Spoon project using Maven for a lesson in HMIN306 - Évolution et restructuration. The goal of this project is to provide students with a starter project for code analysis and refactoring.


# How to use

First clone the project using `git clone <insert link>`.

If you are new to Maven, and are using Eclipse then go to `File >> Import... >> Maven >> Existing Maven Projects`. From there, select the folder containing the project. Once, the project has been imported, it is nearly ready to be used.

Create a run configuration via the green 'play' button and choosing the option `Run Configurations...`. Create a java application configuration pointing to the `Main.java` class. Finally, add the appropriate arguments described by the argument parser that uses JCommander.

An example argument to start analysing your first java project should be `-s <path-to-java-project>`. This argument should be the path to the application you wish to analyse. From there the basic application in the `Main.java` class will find all methods found in the application and print their names.
