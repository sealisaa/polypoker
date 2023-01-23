FROM openjdk:17-jdk
ARG JAR_FILE=out/artifacts/PolyPokerServer_jar/PolyPokerServer.jar
COPY ${JAR_FILE} app.jar

EXPOSE 3306
ENTRYPOINT ["java","-jar","/app.jar"]