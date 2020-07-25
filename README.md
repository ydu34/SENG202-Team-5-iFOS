# iFOS(Intuitive Food Ordering System) 


### Key Notes:
   - The password for entering the admin screen of the application is defaulted to 1111
   - The metadata file must be in the same directory of the jar to autoload and autosave
   - Sample data is in xml files in xml folder which gets autoloaded into the application

### Table of contents: 

* I)   Running the application
* II)  Importing the project to IntellJ
* III) Running the application from IntellJ
* IV)  Importing the .xml files to the application 
* V)   Running the JUnit Tests
* VI)  Running the Acceptance testing  
* VII) Contributors

### I) Running the jar application through jar file. 
   - Go to the directory containing the jar file
   - Open terminal in the directory
   - Run application with "java -jar seng202_2019_team5_3.jar"
   - You may import pre made .xml files in the xmlFiles folder
   - Once the application is running
   - Go to Admin
   - Go to Import/Export Data
   - Click "Select Images Folder" and select the "images" folder
   

### II) Importing the project to IntellJ
   - Open IntelliJ
   - Select File > New > Project from existing sources
   - Find the directory where the contents from the zip file were extracted
   - Select the pom.xml 
   - Click Next > Select the maven project to import 
   - Select the JDK version 1.11.0 
   - Click Next > Finish 

### III) Running the application in IntellJ
   - To run the graphical application go to 
   - SENG202-Team5/src/main/java/seng202.group5/gui/Main
   - Run it buy clicking the green arrow on the top left of the screen or by right clicking Main and selecting run

### IV) Importing the test .xml files to the application
   - Once the application is running 
   - Go to Admin
   - Go to Import/Export Data
   - Click "Select Images Folder" and select the "images" folder

### V) Runng the JUnit Tests
   - In IntellJ 
   - Select SENG202-Team-5 > src > test > java
   - Right click seng202.group5 > select "Run tests in seng202.group.5"/"Run tests in seng202.group.5" with coverage

### VI) Running the Acceptance testing 
   - In IntellJ 
   - Select SENG202-Team-5 > src > test > java > stepDefinitions
   - Right Click on the RunCucumberTest and click "Run 'RunCucumberTest'"

 
### VII) Contributors
- Yu Duan
- James Kwow
- Daniel Harris
- Michale Morgoun
- Shivin Gaba
- Tasman Berry
