iFOS(Intuitive Food Ordering System) 

Table of contents: 

I)   Running the application
II)  Importing the project to Intellij
III) Running the application from Intellij
IV)  Importing the .xml files to the application 
V)   Running the JUnit Tests
VI)  Running the Acceptance testing  

I) Running the jar application through jar file. 
   - Go to the directory containing the jar file
   - Open terminal in the directory
   - Run application with "java -jar SENG202-Team-5-1.0-SNAPSHOT.jar"
   - You may import pre made .xml files in the xmlFiles folder
   - Once the application is running
   - The admin screen should launch
   - Go to Import/Export Data
   - Import each individual file in xmlFiles
   - Select Import Data

II) Importing the project to Intellij
   -Open Intellij
   -Select File > New >Project from existing sources
   -Find the directory where the contents from the zip file that were extracted
   -Select the pom.xml
   -Click Next > Select the maven project to import
   -Select the JDK version 1.11.0
   -Click Next > Finish

III) Running the application in Intellij
   - To run the graphical application go to
   - SENG202-Team5/src/main/java/seng202.group5/gui/main
   - Run it buy clicking the green arrow on the top left of the screen or by right clicking main and selecting run

IV) Importing the test .xml files to the application
  - Once on the application is running
  - Go to Admin
  - Go to Import/Export Data
  - Import each individual file in src/main/resources/data
  - Select Import Data

V) Runng the JUnit Tests
 - In intellJ
 - Select SENG202-Team-5 > src > test
 - Right click seng202.group5 > select "Run tests in seng202.group.5"/"Run tests in seng202.group.5" with coverage

VI) Running the Acceptance testing
 - In intellJ
 - Select SENG202-Team-5 > src > test > stepDefinitions
 - Right Click on the RunCucumberTest

VII) Accessing the Admin Screen
 - The default password is "1111"
 - Users can set a new password under the settings screen

