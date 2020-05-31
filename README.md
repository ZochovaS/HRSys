### HRSys
Application created for use of interview in USU Software, s.r.o.

#### Assignment
The goal of this task is to develop a simple information system for HR (Human Resources) department. The system must allow tracking and management of employees and provide simple statistics about them.

##### Functional Requirements
The employee should have attributes:
* Name
* Age
* Position in the company

**In order to keep track of employees the system must support operations:**
* Add and remove an employee
* Show list of all employees in the company

**The system will be used also by management. Therefore the system should be able calculate following statistics:**
* Count and average age of employees at every position. The table should be ordered by count of employees in descending order.

### Instructions for running the app

##### Prerequisites
* Java 11
* Database HSQLDB

##### Setting up Database
1. download [HSQLDB](https://sourceforge.net/projects/hsqldb/files/)
2. copy *data* folder from source code into the main folder of hsqldb you have just installed
3. open command prompt from main hsqldb folder and run 
> java -cp ./lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:data/hrsysdb --dbname.0 hrsys

##### Generating sample data to empty database
If you are willing to fill the database with more sample data you can do it either by running java spring boot application in Intellij IDEA from class: 
> db-tool/src/main/java/cz/zochova/interview/hrsys/LoadSampleDataToDatabaseApplication.java

or run following command in Terminal:
> java -jar db-tool/target/db-tool-0.0.1-SNAPSHOT.jar

##### Running the application
If you want to start the application you can either run following class in Intellij IDEA
> web-ui/src/main/java/cz/zochova/interview/hrsys/HrSysApplication.java

or run following command in Terminal:
> java -jar web-ui/target/web-ui-0.0.1-SNAPSHOT.jar

### System limitations
* Currently, there is no possibility to save two employees which have same first name, last name and age

### Known issues
* Data validation not working

### Next steps for future development
* Propagate validation alerts to GUI
* Enable searching Employees by e.g. last name (method already implemented in service layer)
* Change age to date of birth and calculate age dynamically on database layer
* Enable management of job positions (add, remove, ...)