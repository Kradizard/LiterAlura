package AluraChallenge.LiterAlura.main;

import AluraChallenge.LiterAlura.modelos.*;
import AluraChallenge.LiterAlura.repositorio.AutoresRepository;
import AluraChallenge.LiterAlura.repositorio.LibrosRepository;
import AluraChallenge.LiterAlura.servicios.AutorServicio;
import AluraChallenge.LiterAlura.servicios.ConsumoAPI;
import AluraChallenge.LiterAlura.servicios.ConversorDeDatos;
import AluraChallenge.LiterAlura.servicios.LibroServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    public LibrosRepository librosRepository;
    private AutoresRepository autoresRepository;
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConversorDeDatos conversor = new ConversorDeDatos();
    private DatosAutor datosAutor;
    private final LibroServicio libroServicio = new LibroServicio();
    private final AutorServicio autorServicio = new AutorServicio();
    private Autor autorLibro;
    private Libro libroBuscado;
    private List<Autor> autores;
    private List<Libro> libros;

    public Principal(LibrosRepository libroRepository, AutoresRepository autorRepository) {
        this.autoresRepository = autorRepository;
        this.librosRepository = libroRepository;
    }

    public void mostrarMenu() throws Exception {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n ******************************************* 
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por el idioma             
                    0 - Salir
                    \n 
                    Elija el numero en base a la opción deseada: 
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    obtenerLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;

                case 3:
                    listarAutoresRegistrados();
                    break;

                case 4:
                    listarAutoresVivosPorAnio();
                    break;

                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void obtenerLibroPorTitulo() throws Exception {
        busquedaDeLibros();
    }

    private void listarLibrosRegistrados() {
        System.out.println("\nLibros registrados: ");
        libros = librosRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        System.out.println("\nAutores registrados: ");
        autores = autoresRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("\nIndique el año a buscar: ");
        var ano = teclado.nextInt();
        autores = autoresRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en ese año..");
        } else {
            System.out.println(ano);
            System.out.println("\nListado de autores vivos en el año " + ano + ": ");
            autores = autoresRepository.findAutorByFecha(ano);
            autores.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("\nListado de idioma registrados: ");
        var listaDeIdiomas = librosRepository.findDistinctIdiomas();
        for (int i = 0; i < listaDeIdiomas.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + Idioma.from(listaDeIdiomas.get(i)));
        }
        System.out.println("\nIndique el idioma que busca: ");
        var eleccion = teclado.nextInt();
        if (eleccion < 1 || eleccion > listaDeIdiomas.size()) {
            System.out.println("Opción Inválida..");
            return;
        }
        String idiomaSeleccionado = Idioma.from(listaDeIdiomas.get(eleccion - 1)).toString();
        System.out.println("\nListado de libros en - [ " + idiomaSeleccionado.toUpperCase() + " ]: ");
        libros = librosRepository.findByIdioma(listaDeIdiomas.get(eleccion - 1));
        libros.forEach(System.out::println);
        System.out.println();
    }

    private void busquedaDeLibros() throws Exception {

        System.out.println("Indique el libro que desea buscar: ");
        var tituloLibro = teclado.nextLine();
        var resultadoBusqueda = new ConsumoAPI().obtenerDatosLibro(tituloLibro);

        JSONObject jsonObject = new JSONObject(resultadoBusqueda);
        JSONArray resultsArray = jsonObject.getJSONArray("results");

        if (resultsArray.isEmpty()) {
            System.out.println("Libro no encontrado con el título: " + tituloLibro);
            return;
        }

        System.out.println(resultsArray.length() + " libros encontrados: \n");
        for (int i = 0; i < resultsArray.length(); i++) {
            System.out.println("[" + (i + 1) + "] " + resultsArray.getJSONObject(i).getString("title"));
        }

        System.out.println("\nIndique el numero del libro buscado, de otro modo presione 0. ");
        var numeroLibro = teclado.nextInt();
        if (numeroLibro == 0) {
            return;
        }
        numeroLibro = numeroLibro - 1;

        jsonObject = new JSONObject(resultsArray.getJSONObject(numeroLibro).toString());
        DatosLibro datosLibro = conversor.obtenerDatos(jsonObject.toString(), DatosLibro.class);

        Optional<Libro> libro = librosRepository.findById(datosLibro.idLibro());
        if (libro.isPresent()) {
            System.out.println("Libro ya registrado");
            System.out.println();
            System.out.println(libro.get());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map jsonMap = mapper.readValue(jsonObject.toString(), Map.class);
        String authorsJson = mapper.writeValueAsString(jsonMap.get("authors"));
        String resultado = authorsJson.substring(1, authorsJson.length() - 1);
        datosAutor = conversor.obtenerDatos(resultado, DatosAutor.class);

        String idioma = new ConsumoAPI().getIdioma(jsonMap);

        autores = autoresRepository.findAll();
        Optional<Autor> autor = autores.stream()
                .filter(a -> a.getNombre().equals(datosAutor.nombre()))
                .findFirst();
        if (autor.isPresent()) {
            autorLibro = autor.get();
        } else {
            autorLibro = new Autor(datosAutor);
            autoresRepository.save(autorLibro);
        }
        Libro libroNuevo = new Libro(datosLibro, autorLibro, idioma);
        librosRepository.save(libroNuevo);
        System.out.println("Libro registrado exitosamente");
        System.out.println();
        System.out.println(libroNuevo);
    }

}













