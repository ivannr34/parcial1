package pa.parcial1Sumo.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Representa un luchador de sumo.
 * Contiene sus datos básicos, técnicas, victorias, disponibilidad y rival.
 */
public class Luchador implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombre;
    private double peso;
    private String tunica;
    private boolean dentroDohyo = true;
    private boolean disponible = true;
    private int victorias = 0;
    private Luchador rival;
    private List<String> tecnicas;
    private final Random random = new Random();

    /**
     * Constructor vacío.
     */
    public Luchador() {
        this.tecnicas = new ArrayList<>();
    }

    /**
     * Constructor base usado por la vista del cliente.
     *
     * @param nombre nombre del luchador
     * @param peso peso del luchador
     * @param tunica color o tipo de túnica
     * @param tecnicas lista de técnicas seleccionadas
     */
    public Luchador(String nombre, double peso, String tunica, List<String> tecnicas) {
        this.nombre = nombre;
        this.peso = peso;
        this.tunica = tunica;
        this.tecnicas = tecnicas == null ? new ArrayList<>() : new ArrayList<>(tecnicas);
    }

    /**
     * Constructor completo para mapeo desde base de datos.
     *
     * @param id identificador
     * @param nombre nombre del luchador
     * @param peso peso del luchador
     * @param tunica color o tipo de túnica
     * @param tecnicas técnicas permitidas
     * @param disponible si puede o no participar en combate
     * @param victorias cantidad de victorias
     */
    public Luchador(Integer id, String nombre, double peso, String tunica,
                    List<String> tecnicas, boolean disponible, int victorias) {
        this.id = id;
        this.nombre = nombre;
        this.peso = peso;
        this.tunica = tunica;
        this.tecnicas = tecnicas == null ? new ArrayList<>() : new ArrayList<>(tecnicas);
        this.disponible = disponible;
        this.victorias = victorias;
    }

    /**
     * Escoge aleatoriamente una técnica de la lista del luchador.
     *
     * @return técnica seleccionada
     */
    public String usarTecnica() {
        if (tecnicas == null || tecnicas.isEmpty()) {
            return "No seleccionó técnica";
        }
        int index = random.nextInt(tecnicas.size());
        return tecnicas.get(index);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    public String getTunica() {
        return tunica;
    }

    public List<String> getTecnicas() {
        return Collections.unmodifiableList(tecnicas);
    }

    public void setTecnicas(List<String> tecnicas) {
        this.tecnicas = tecnicas == null ? new ArrayList<>() : new ArrayList<>(tecnicas);
    }

    public boolean estaDentroDohyo() {
        return dentroDohyo;
    }

    public void setDentroDohyo(boolean dentroDohyo) {
        this.dentroDohyo = dentroDohyo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public void sumarVictoria() {
        victorias++;
    }

    public void setRival(Luchador rival) {
        this.rival = rival;
    }

    public Luchador getRival() {
        return rival;
    }
}