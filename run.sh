git clone https://github.com/cse364-unist/projects-group12.git

cd projects-group12/rest2night

mvn jacoco:report

mvn package

java -jar target/rest2night-0.0.1-SNAPSHOT.jar