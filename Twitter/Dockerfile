FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD build/libs/Twitter-0.0.1-SNAPSHOT.jar Twitter.jar
ENTRYPOINT ["java","-jar","/Twitter.jar"]