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

The script can be found in src/test/java/RestTest.jmx

Enjoy
