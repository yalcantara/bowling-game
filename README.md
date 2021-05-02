# bowling
A Java code challenge project.

#### Requirements
To execute this project correctly, the following software components are needed:

* Java version 11 or higher
* Apache Maven version 3.6.0 or higher 

#### Running and Testing

To run the test cases, navigate to the root folder and type the following command:

```
mvn test
```

To build, navigate to the root folder and type the following command:

```
mvn clean package
```

Maven will build the project and run some test files. To run type:


```
java -jar target/bowling-jar-with-dependencies.jar files/game.txt
```

You can substitute the game.txt file for whatever text file containing the game data. Make sure the proper file path is passed as argument to the program.
