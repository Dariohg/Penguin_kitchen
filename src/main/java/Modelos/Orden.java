package Modelos;

public class Orden {
    private final int id;
    private String estado;

    public Orden(int id) {
        this.id = id;
        this.estado = "EN PROCESO";
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}