# api-test-rest-assured

Tools Used: Maven, Java, rest-assured, TestNG, json-simple, allure-report

This is automation API test for endpoint [this url](https://stagingapi.cicle.app)

# Setting up and running tests

* Open project as Maven Project in Eclipse, Intellij, or other IDE
* Run runAll.xml file using TestNG. The directory file:
```
{YourProjectPath}/src/test/java/suites
```
* To generate report, you can run this cmd command on project directory, make sure allure has been installed in your device:
```
allure serve allure-results
```