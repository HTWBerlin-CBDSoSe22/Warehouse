FROM openjdk:17-oracle
#RUN apt-get update && install -y wait-for-it
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY Fruits.csv  Fruits.csv
COPY Products.csv Products.csv
ENTRYPOINT ["java","-jar","/app.jar"]