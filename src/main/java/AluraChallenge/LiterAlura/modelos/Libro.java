package AluraChallenge.LiterAlura.modelos;

import jakarta.persistence.*;
import jdk.jfr.Name;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private Long idLibro;
    @Column(unique = true)
    @Name(value = "titulo")
    private String titulo;
    private String idioma;
    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Autor autor;
    private double numDescargas;

    public Libro(){

    }

    public Libro(DatosLibro datosLibro, Autor autor, String idioma){
        this.idLibro = datosLibro.idLibro();
        this.titulo = datosLibro.titulo();
        this.numDescargas = datosLibro.numDescargas();
        this.autor = autor;
        this.idioma = idioma;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public double getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(double numDescargas) {
        this.numDescargas = numDescargas;
    }

    @Override
    public String toString() {
        String nuevoLibro = "------- LIBRO -------\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idiomas: " + Idioma.from(idioma) + "\n" +
                "Numero de Descargas: " + numDescargas + '\n';

        return nuevoLibro;
    }
}
