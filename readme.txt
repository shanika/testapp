Please make sure JAVA_HOME has been set to JDK 7 or above.
Require maven 3.0.5 or above (Tested on 3.0.5)

Build
--------
mvn clean install

Run
--------
java -jar target/dependency/webapp-runner.jar --port 9000 target/*.war

UI
--------
I just added a simple UI to try the REST api using ng-admin. (http://localhost:9000/)


REST API
--------

Create		POST	/api/customers
Update		PUT	/api/customers/:id
Read		GET	/api/customers/:id
ReadAll		GET	/api/customers/
DELETE		DELETE	/api/customers/:id

