package AluraChallenge.LiterAlura.servicios;

import AluraChallenge.LiterAlura.modelos.Libro;
import AluraChallenge.LiterAlura.repositorio.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServicio {
    @Autowired
    public LibrosRepository libroRepository;

    public List<String> obtenerDistintosIdiomas(){
        return libroRepository.findDistinctIdiomas();
    }

    public List<Libro> obtenerLibrosPorIdioma(String idioma){
        return libroRepository.findByIdioma(idioma);
    }
}
