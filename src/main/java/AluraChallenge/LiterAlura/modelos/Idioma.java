package AluraChallenge.LiterAlura.modelos;

public enum Idioma {
    CA("ca", "Catalán"),
    ZH("zh", "Chino (simplificado)"),
    ZH_TW("zh-TW", "Chino (tradicional)"),
    CS("cs", "Checo"),
    NL("nl", "Neerlandés"),
    EN("en", "Inglés"),
    FR("fr", "Francés"),
    DE("de", "Alemán"),
    IT("it", "Italiano"),
    JA("ja", "Japonés"),
    KO("ko", "Coreano"),
    LA("la", "Latín"),
    PT("pt", "Portugués"),
    RU("ru", "Ruso"),
    ES("es", "Español");

    private final String iniciales;
    private final String nombreCompleto;

    Idioma(String iniciales, String nombreCompleto) {
        this.iniciales = iniciales;
        this.nombreCompleto = nombreCompleto;
    }

    public static String from(String iniciales) {
        for (Idioma idioma : values()) {
            if (idioma.iniciales.equalsIgnoreCase(iniciales)) {
                return idioma.nombreCompleto;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + iniciales);
    }
}
