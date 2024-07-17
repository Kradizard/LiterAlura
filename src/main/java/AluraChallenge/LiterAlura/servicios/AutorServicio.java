package AluraChallenge.LiterAlura.servicios;

import AluraChallenge.LiterAlura.modelos.Autor;
import AluraChallenge.LiterAlura.repositorio.AutoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServicio {
    @Autowired
    private AutoresRepository autoresRepository;

    public List<Autor> getAutoresVivosPorAnio(int year){
        return  autoresRepository.findAutorByFecha(year);
    }
}
