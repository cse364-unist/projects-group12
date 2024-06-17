
# Use an official Tomcat base image
FROM tomcat:9.0.70-jdk11

WORKDIR /usr/local/tomcat

# Copy the war file to the webapps directory of Tomcat
COPY rest2night/target/rest2night.war webapps/

# Copy the run.sh script to the /root/project directory
COPY run.sh /root/project/

RUN chmod +x /root/project/run.sh

# Expose the port Tomcat is running on
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]