FROM ubuntu:latest
LABEL authors="ove"

ENTRYPOINT ["top", "-b"]

# Kasutame ametlikku java kekskonda -- kerge alpine versioon
FROM eclipse-temurin:21-jdk-alpine
#LOOME konteineri sisse töökausta
WORKDIR /app
# kopeerime sinna Server.java faili
COPY Server.java .
# Kompileerime java faili konteineri sees
RUN javac Server.java
# Avame prodi 5003 mida kasutame serveri ühenduse ootamiseks
EXPOSE 5003
# Käsk mis käivitatakse siis, kui konteiner tööle pannakse
CMD ["java", "Server"]