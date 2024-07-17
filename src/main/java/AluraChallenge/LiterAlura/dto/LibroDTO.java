package AluraChallenge.LiterAlura.dto;

import AluraChallenge.LiterAlura.modelos.Autor;

public record LibroDTO(int idLibro,
                       String titulo,
                       Autor autor,
                       String idioma,
                       Double numDescargas) {
}
