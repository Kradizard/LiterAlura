package AluraChallenge.LiterAlura.servicios;

public interface IConversorDeDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

