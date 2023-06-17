~~~
Compiling the jar:

1.Run the following command in the terminal in the "Source Files" directory:

javac -d bin src/main/*.java

2.Run the following command in the terminal in the 'bin' directory.

jar cfe bsm62_mdi48_SportsTournament.jar main/TitleScreen main/*.class

This will create a compiled jar file called bsm62_mdi48_SportsTournament.jar in the 'bin' directory.

~~~
Running the game:

1.Run the following command in a terminal from the directory where the bsm62_mdi48_SportsTournament.jar file is located:

java -jar bsm62_mdi48_SportsTournament.jar

~~~
Importing the project into the Eclipse IDE:

1. Launch Eclipse. 

2. Select File->Open Projects from file system

3. Browse to the "Exported Project" directory and select it.	

4. Select finish. The project should now be imported into eclipse

5. Right click on the project folder and select Build Path -> add Libraries

6. Select JUnit, then JUnit 5, then finish.

The project should now be fully imported and set up.
