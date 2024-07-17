package AluraChallenge.LiterAlura;

import AluraChallenge.LiterAlura.main.Principal;
import AluraChallenge.LiterAlura.repositorio.AutoresRepository;
import AluraChallenge.LiterAlura.repositorio.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Autowired
	private LibrosRepository librosRepository;
	@Autowired
	private AutoresRepository autoresRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(librosRepository, autoresRepository);
		principal.mostrarMenu();
	}
}
