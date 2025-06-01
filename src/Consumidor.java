import java.util.List;

public class Consumidor extends Thread {

    List<Productor> productoresAsociados;
    ThreadSafeQueue queue;
    int id;

    public Consumidor(List<Productor> productoresAsociados, int id, ThreadSafeQueue queue) {
        this.productoresAsociados = productoresAsociados;
        this.id = id;
        this.queue = queue;
    }

    public void run() {
        while (true) {
            // Espera pasiva si no hay items de productores asociados
            synchronized (this) {
                if (!ningunItemProducido()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // Obtener y asignar item de forma atómica
            Item item = queue.getAndAssignItem(productoresAsociados);

            if (item != null) {
                try {
                    // Simulación de consumo
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Avisar al productor que el item fue consumido
                synchronized (item.getProd()) {
                    item.setConsumido();
                    item.getProd().setProducioAlgo(false);
                    System.out.println(id + " termino de consumir item " + item.getId() + " de el productor " + item.getProd().id);
                    item.getProd().notify();
                }
            }

            // Si no se encontró item, simplemente vuelve a intentar en la siguiente iteración
        }
    }

    public boolean ningunItemProducido() {
        for (Productor productor : productoresAsociados) {
            if (productor.getProducioAlgo()) {
                return true;
            }
        }
        return false;
    }
}