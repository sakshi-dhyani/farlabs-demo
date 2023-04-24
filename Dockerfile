FROM openjdk:8
ADD target/farelabs-0.0.1-SNAPSHOT.jar farelabs-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar" , "farelabs-0.0.1-SNAPSHOT.jar"]