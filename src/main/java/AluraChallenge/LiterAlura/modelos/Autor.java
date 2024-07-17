package AluraChallenge.LiterAlura.modelos;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;
    @Column(unique = true)
    private String nombre;
    private String fechaNacimiento;
    private String fechaMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libro;

    public Autor(){

    }

    public Autor(String nombre, String fechaNacimiento, String fechaMuerte){
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaMuerte = fechaMuerte;
    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaMuerte = datosAutor.fechaMuerte();
    }

    public Long getIdAutor() {
        return idAutor;
    }
    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getFechaMuerte() {
        return fechaMuerte;
    }
    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }
    public List<Libro> getLibro() {
        return libro;
    }
    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }

    private String librosAutor(List<Libro> libro) {
        return libro.stream()
                .map(Libro::getTitulo).collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        String datosAutor = "********Autor********\n" +
                "Nombre: " + nombre + "\n" +
                "Fecha De Nacimiento: " + fechaNacimiento + "\n" +
                "Fecha De Muerte: " + fechaMuerte + "\n" +
                "\nLibros publicados: \n" + librosAutor(libro) + "\n";
        return datosAutor;
    }

}
