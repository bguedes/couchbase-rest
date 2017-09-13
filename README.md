# couchbase-rest

### Prerequisites :

1. A Couchbase cluster UP and running
2. JDK 1.8
3. Maven
3. JMeter installed <http://jmeter.apache.org/download_jmeter.cgi>

### Compilation :

git clone https://github.com/bguedes/couchbase-rest.git

mvn clean package

### To run the web proxy :

mvn spring-boot:run

### To run the test JMeter script :

/apache-jmeter-3.2/bin
sh jmeter.sh

The script can be found in src/main/java/resources/RestTest.jmx

### Web proxy parameters :

By default Spring Boot use the port 8080

For Couchbase configuration, you can modify the file src/main/java/resources/applications.properties

* storage.host=10.112.163.101 (couchbase ip adress)
* storage.bucket=beer-sample
* storage.username=Administrator
* storage.password=couchbase

Enjoy
