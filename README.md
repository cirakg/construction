# construction

## Development

precondition: You need to have MySQL database running on port 3306 and with empty database called "construction"
			  Username and password for MySQL should be root/root, otherwise you can change your connection details in application-dev.yml file


(optional): run maven install in the root project folder. This will build the project and run integration tests.
You can see the test execution details in the generated target folder after build passes.

To start your application in the dev profile, run:

    ./mvnw

or start it as a Spring Boot app on any other way

After this, API will be ready on localhost:8080, and you can access Swagger API docs on: http://localhost:8080/swagger-ui.html

File construction.json in the root folder of the project contains API tests you can import in Talend API tester (https://www.talend.com/) and execute API test scenarios. 
(the ids used in the API test scenario can conflict with integration tests, so please use API tests on a clean database - just start the BE on a clean database and
it will execute the migration scripts, after which you can test the API).

You can also test the API using the swagger page.

Enjoy