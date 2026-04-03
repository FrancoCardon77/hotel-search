# hotel-search

Microservicio para registrar búsquedas de disponibilidad hotelera y contar cuántas veces se repiten. Usa Spring Boot 3.3.5, Java 21, Kafka y PostgreSQL con arquitectura hexagonal.

Se usó PostgreSQL en vez de Oracle porque la imagen Docker de Oracle pesa ~2GB contra ~200MB de Postgres, lo que hace mucho más pesado levantarlo.

## Levantar

Solo necesitás Docker Compose instalado, nada más. La compilación ocurre dentro del contenedor:

```bash
docker-compose up --build
```

La app queda en el puerto 8080. Para bajarla:

```bash
docker-compose down
```

## Endpoints

**POST /search** — registra una búsqueda y devuelve su ID

```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{"hotelId":"1234aBc","checkIn":"29/12/2023","checkOut":"31/12/2023","ages":[30,29,1,3]}'
```

```json
{ "searchId": "550e8400-e29b-41d4-a716-446655440000" }
```

**GET /count?searchId=xxx** — cuántas veces se hizo esa búsqueda exacta (el orden de ages importa)

```bash
curl "http://localhost:8080/count?searchId=550e8400-e29b-41d4-a716-446655440000"
```

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000",
  "search": { "hotelId": "1234aBc", "checkIn": "29/12/2023", "checkOut": "31/12/2023", "ages": [30,29,1,3] },
  "count": 5
}
```

## Swagger

```
http://localhost:8080/swagger-ui.html
```

## Tests y cobertura

```bash
mvn clean verify
```
