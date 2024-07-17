package AluraChallenge.LiterAlura.repositorio;

import AluraChallenge.LiterAlura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
    List<Autor> findAutorByNombre(String nombre);

    @Query(value = "SELECT a FROM Autor a WHERE a.fechaMuerte >:year AND a.fechaNacimiento <:year")
    List<Autor> findAutorByFecha(int year);

}
