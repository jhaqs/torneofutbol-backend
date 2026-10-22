# Endpoints

| Método | Endpoint | Función |
|---|---|---|
| POST | `/api/campeonatos` | Crear campeonato |
| GET | `/api/campeonatos` | Listar campeonatos |
| GET | `/api/campeonatos/{id}` | Consultar campeonato |
| POST | `/api/equipos` | Crear equipo |
| GET | `/api/equipos` | Listar equipos |
| GET | `/api/equipos/{id}` | Consultar equipo |
| PUT | `/api/equipos/{id}` | Actualizar equipo |
| POST | `/api/equipos/{id}/escudo` | Subir escudo |
| POST | `/api/campeonatos/{campeonatoId}/inscripciones/equipos/{equipoId}` | Inscribir equipo |
| GET | `/api/campeonatos/{campeonatoId}/inscripciones` | Listar inscritos |
| POST | `/api/campeonatos/{campeonatoId}/fixture/generar` | Generar fixture |
| GET | `/api/campeonatos/{campeonatoId}/jornadas` | Listar jornadas |
| GET | `/api/jornadas/{idJornada}/partidos` | Jornada + partidos + descanso |
| GET | `/api/partidos/{id}` | Consultar partido |
| POST | `/api/partidos/{id}/resultado` | Registrar resultado |
| PUT | `/api/partidos/{id}/resultado` | Corregir resultado |
| POST | `/api/campeonatos/{campeonatoId}/equipos/{equipoId}/abandono` | Registrar abandono y walkover |
| GET | `/api/campeonatos/{campeonatoId}/tabla-posiciones` | Tabla de posiciones |
| POST | `/api/campeonatos/{campeonatoId}/sorteo` | Registrar ganador del sorteo |
| GET | `/api/campeonatos/{campeonatoId}/sorteo` | Consultar sorteo |
