package AluraChallenge.LiterAlura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("id") Long idLibro,
                         @JsonAlias("title") String titulo,
                         @JsonAlias("download_count") Double numDescargas) {
}
