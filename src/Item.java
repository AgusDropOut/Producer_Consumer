public class Item {
    private int id;
    private Productor prod;
    private boolean consumido;
    private boolean estaAsignado;

    public Item(int i, Productor p) {
        id = i;
        prod = p;
        consumido = false;
        estaAsignado = false;
    }

    public synchronized void setConsumido() {
        consumido = true;
    }

    public synchronized boolean consumido() {
        return consumido;
    }

    public int getId() {
        return id;
    }
    public Productor getProd() {
        return prod;
    }
    public synchronized void setEstaAsignado(boolean estaAsignado) {
        this.estaAsignado = estaAsignado;
    }
    public synchronized boolean isEstaAsignado() {
        return estaAsignado;
    }

    //Se podria agregar un boolean de esta asignado a un consumidor y utilizar eso para armar un monitor por item, pero seri amas complicado
}