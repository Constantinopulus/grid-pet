FROM openjdk:13
COPY ./build/libs /tmp
WORKDIR /tmp
EXPOSE 8080
EXPOSE 5005
CMD ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "pet-0.0.1-SNAPSHOT.jar"]