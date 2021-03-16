# Spring Boot Sample Application

## How to run
* Update application.properties with connection string and queue/topic name.
* Open a command window and cd to root of this application. Run following command to start application 'mvn spring-boot:run'.
* send the order by calling the http endpoint `curl -X POST -d "order=1"  http://127.0.0.1:8080/sendOrder`
* Custom Serializer : send the order by calling the http endpoint `curl -X POST -d "order=1"  http://127.0.0.1:8080/sendOrderCustomSerializer`
