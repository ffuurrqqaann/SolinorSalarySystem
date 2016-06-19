# SolinorSalarySystem
This is a salary Calculation/Viewer software that has been build for Solinor Oy as my technical test.

This demo take a csv file as an input, parse the employees daily salaries along with
the compensation and store it to the database. The Salary Viewer then show the total
monthly salary of the employees along with their compensations.

Key technologies:

1. Java 8 (Changed the jdk to 1.7 for compilation due to OpenShift not supporting java 8)
2. Spring MVC 4.2.4.RELEASE
3. Spring JDBC 3.2.6.RELEASE
4. Junit 4.12
5. Maven Tomcat Plugin 2.2
6. Maven Eclipse Plugin 2.9
6. GitHub
	
3rd Party Libraries

1. jQuery 1.11.3
2. Datatable 1.10.0
3. Bootstrap 3.3.5
4. GSON 2.2.2
5. Open CSV 2.3
6. Apache Commons fileUpload 1.3.1

Junit
Junit tests can be found in src/test folder with the file name SalaryViewerTests.java, all the tests are encapsulated in this one file can be run by Right Click on the file->JUNIT Test.

Git hub
Project on Git can be found @ https://github.com/ffuurrqqaann/SolinorSalarySystem

Project Host Url
http://demo-salaryviewer.rhcloud.com/SolinorSalarySystem/