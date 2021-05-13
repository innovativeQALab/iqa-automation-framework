# iqa-automation-framework 

> Evaluted from [cucumber-selenium-framework](https://github.com/zodgevaibhav/cucumber-selenium-framework)

Selenium WebDriver and Appium based Web, Mobile (Android, iOS) and Windows desktop Automation Framework with BDD &amp; Non-BDD implementation support


###### iqa-automation-framework is evolved from [cucumber-selenium-framework](https://github.com/zodgevaibhav/cucumber-selenium-framework)
### Features
1) With few Configurations and adding dependencies, you are ready to use this framework
2) Framework supports to test web application, Mobile application (Native, Web, Hybrid), Windows Native applications
3) BDD based Framework:
    1) Tag based scenarios
    2) Tag based execution
4)  Parallel and Sequential execution support
5) Selenium Grid Support: It helps in running multiple tests across different browsers, operating systems, and machines in parallel
6) Database support- Do database Configuration and use it (MySQL, Oracle, etc.)
7) Test Data management facility at run time, global, static, Test script level
8) Logger and Reports Facility:
    1) Test method level - Using Extent Report
    2) Framework level - Using Log4j
    
### How To Start/Use
1) Create simple maven project in Eclipse
2) Get framework project from git and add following dependency and sure-fire plug in information in POM file
Dependency:
```xml
<dependencies>
		<dependency>
			<groupId>org.iqa</groupId>
			<artifactId>test-automation-framework</artifactId>
			<version>0.0.1</version>
		</dependency>
	</dependencies>
```
3) Create page object classes  
4) Create feature file and corresponding step definitions in it
5) Create testNG.xml file
6) Create required configuration files in resources folder

```Please refer sample project "orange-hrm" for more details```

### XML file configurations
TestNG xml file gives facility to execute test scripts as per our requirement. We need to provide the parameters which is mandatory for the execution of scripts and also as per our need
1) Parallel execution threads control:
```xml
<suite name="Selenium-Cucumber-Test" parallel="methods" thread-count="10" >    
```
2) Provide Feature File/Folder Path:
```xml
<parameter name="featureFilePath" value="src/test/resources/feature/"/>
```
3) Provide Glue code package name where page object classes are created:
```xml
<parameter name="glueCodePackageName" value="com.orangehrm.pages.web"/>
```
4) Tag based execution - Provide the tag  which need to include in execution:
```xml
<parameter name="tagsToExecute" value="@High"/> 
```
5) Select runner class Name to run test scripts **parallel** or  **sequential** :

	1) Parallel Execution
```xml
 <class name="org.iqa.test.runner.ParallelTestRunner"/>
 ```
2) Sequential Execution
```xml
 <class name="org.iqa.test.runner.SequentialTestRunner"/>
 ```
 
 ### How to write feature file
 Use one feature file for each page which covers all and any number of scenarios you want to cover for that respective page
 1) As we are using tag based execution provides tag for scenarios e.g. @Sanity, @High etc.
 2) Follow **Given**  ,  **When**, **Then** format :
    1) **Given** - Pre-requisite of the test case
    2) **When** - Actions to be performed in that test script
    3) **Then** - Result Validation
    
Example:
```
 @High
  Scenario: Verify admin login successful
    Given user navigate to orange hrm URL
    When uses enters user name as "Admin" and password as "admin123" and click on login button
    Then user should be able to see "Welcome Admin" message
```

### Test Data
1) Static Data- Define static test data (Refer PersonData class)
2) Dynamic test data generation- use RandonNumber class to generate dynamic test data at run time 
3) Provide test data to test scenario in following format
```
    Examples: 
      | name  | password | welcome_text  |
      | Admin | admin123 | Welcome Admin |
```

### How to write Page Object Classes
1) Provide all web element at the start of each page class 
    1) **@FindBy** - tag used for web/windows application web element
    2) **@AndroidFindBy** tag used for Android application web element
2) Create a method for all **GIVEN** keyword of **feature file** with tag **Given** in respective page
3) Create a method for all **WHEN** keyword of **feature file** with tag **When** in respective page
4) Create a method for all **THEN** keyword of **feature file** with tag **Then** in respective page
5) Use Assertions for validation purpose to compare actual and expected results

