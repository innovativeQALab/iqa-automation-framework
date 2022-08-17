# iqa-automation-framework 

> Evaluted from [cucumber-selenium-framework](https://github.com/zodgevaibhav/cucumber-selenium-framework)

Selenium WebDriver and Appium based Web, Mobile (Android, iOS) and Windows desktop Automation Framework with BDD implementation support

### iQA Test Automation Framework Architecture

![image](https://user-images.githubusercontent.com/30923231/185048624-221c0edc-aaf8-4e82-b66d-0655f1368826.png)

### Features
1) With few Configurations and adding dependencies, you are ready to use this framework
2) Framework supports to test web application, Mobile application (Native, Web, Hybrid), Windows Native applications
3) BDD based Framework:
    1) Tag based scenarios
    2) Tag based execution
4) Parallel and Sequential execution support
5) Selenium Grid Support: It helps in running multiple tests across different browsers, operating systems, and machines in parallel
6) Database support- Do database Configuration and use it (MySQL, Oracle, etc.)
7) Test Data management facility at run time, global, static, Test script level
8) Logger and Reports Facility:
    1) Test method level - Using Extent Report
    2) Framework level - Using Log4j
9) Applitool Eye Support
    
### How To Start/Use
1) Create simple maven project in Eclipse
2) Get framework project from git and add following dependency and sure-fire plug in information in POM file
Dependency:
```xml
<dependencies>
		<dependency>
			<groupId>io.github.innovativeqalab</groupId>
			<artifactId>test-automation-framework</artifactId>
			<version>0.2.0</version>
		</dependency>
	</dependencies>
```
3) Create page object classes src/test/java folder
4) Create feature file/s in src/test/resources/feature folder and corresponding step definitions in src/test/java folder
5) Create testNG.xml file in src/test/resources folder
6) Create required configuration files in src/test/resources folder (as a properties file).
Configuration file are categorised in to three part
	1. framework - Which are set/written by user but used by Framework for execution. These can be mandatory properties.
		a. WebDriverConfig.properties - Configuration related to Selenium Grid, Platform, local execution, etc.
		b. ApplitoolEyeConfig.properties - Configuration related to Applitool (Applitool Enable/Disable, Batch Name)
	2. capabilities - capabilities for the selenium grid/appium/sauceLab. 
	3. userDefined - General properties user want to use for any purpose ex. Global Test Data, Environment specific data, etc... 

```Please refer sample project "orange-hrm" for more details```

### Properties file configurations
1) WebDriverConfig.properties is the important file which decides whether to run the tests locally or on remote machines. It contains following important items
	- Driver mode : Remote or Local. Specify DRIVER=REMOTE for facilitating remote execution using remote driver. RemoteWebDriver capabilities must set in respective property files in src/test/resources/capabilities folder. DRIVER=BROWSER for execution on local machine.
	- Platform : WINDOWS or LINUX or IOS or ANDROID ...
	- Browser Name : Specify the browser you want to use
	- Browser Version : Specify the browser version. If not specified, it will consider the default one.
	- Driver Executables : Specify the complete path to driver exe. If Driver Mode is 'Remote', this property is not required.
	- Driver Property Name : Specify property name of the driver e.g for chrome driver, 'web driver.chrome.driver'. If Driver Mode is 'Remote', this property is not required.
	- Hub Url : This is required if the Driver Mode is Remote. Specify the selenium grid hub url or appium url as applicable

2) Environment.properties file will have name of the environment. Corresponding to each environment e.g QA, there needs be properties file in the user-defined folder like 'QAEnvironmentConfig.properties'. This file will have environment URL and other environment specific properties.

3) Capabilities : This folder will contain Browser, iOS, Android etc specific properties in the separate files as per our need. Please refer to sample project 'orange-hrm' for more details.


### XML file configurations
TestNG xml file gives facility to execute test scripts as per our requirement. We need to provide the parameters which is mandatory for the execution of scripts and also as per our need
1) Parallel execution threads control:
```xml
<suite name="Selenium-Cucumber-Test" parallel="methods" data-provider-thread-count="10" >    
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

### Applitool Integration
1) Create Property file in src/test/resources/properties/framework/ApplitoolEyeConfig.properties
2) Add properties as EYE_ENABLE=TRUE and BATCH_NAME=Regression Suite
3) Set Applitool API Key in System Environmental variable with name "APPLITOOLS_API_KEY". Variable can be sent as TestNg Environmental parameter, Maven Parameter, Jenkins Parameter or can be set in Runner Machine.
