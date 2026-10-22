# Torneo Futbol Backend

Backend REST para gestionar el Campeonato inter comunidades de la provincia de Huancasancos.

## Stack
- Java 17
- Spring Boot 4.1.1
- Maven
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- Jakarta Validation
- springdoc OpenAPI 3.1.1
- JUnit 5 + Mockito

Spring Boot 4.1.1 es una versión estable y requiere Java 17 como mínimo. springdoc 3.1.1 es compatible con Spring Boot 4.x.

## 1. Base de datos
Ejecutar `../torneofutbol.sql` en MySQL.

## 2. Configuración
Por defecto:
- BD: `torneofutbol`
- usuario: `root`
- password: `root`
- puerto: `8080`

Se pueden cambiar con `DB_USERNAME`, `DB_PASSWORD` y `ESCUDOS_DIR`.

## 3. Ejecutar
```bash
mvn clean test
mvn spring-boot:run
```

## 4. Swagger
`http://localhost:8080/swagger-ui.html`

## 5. Flujo recomendado
1. Crear campeonato.
2. Crear equipos.
3. Inscribir equipos.
4. Generar fixture indicando un domingo.
5. Registrar resultados.
6. Consultar tabla de posiciones.
7. Corregir resultados mediante PUT si es necesario.
8. Registrar abandono para aplicar walkover a partidos programados.
9. Si los dos primeros quedan iguales en PTS, DIF y GF, registrar el sorteo.

## Reglas
- Cada equipo juega contra cada otro exactamente una vez.
- No existe local/visitante: se usan equipo1/equipo2.
- Estados de partido: PROGRAMADO y JUGADO.
- No existe endpoint de eliminación física de partidos.
- Las estadísticas se calculan a partir de partidos JUGADO.
- El fixture soporta cantidad par e impar de equipos.
- Para una cantidad impar se informa el equipo que descansa en cada jornada.
