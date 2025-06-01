import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Productor implements Runnable {
    ThreadSafeQueue cola;
    int items, id;
    List<Consumidor> consumidores;
    public boolean producioAlgo = false;

    public Productor(ThreadSafeQueue cola, int cantItems, int id, List<Consumidor> consumidores) {
        this.cola = cola;
        this.items = cantItems;
        this.id = id;
        this.consumidores = consumidores;
    }

    public void run() {
        for (int i = 0; i < items; i++) {
            Item it = null;
            System.out.println("Productor_" + id + "_produce_item_" + i);
            try {
                // simulo un tiempo de producción de cada item
                Thread.sleep((int)(500 * Math.random()));
                it = new Item(i, this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cola.addItem(it);
            //se le avisa a un consumidor asociado que ya hay un item disponible
            setProducioAlgo(true);
            avisarConsumidor();
            System.out.println(id + "_espera_que_item_" + i + "_se_consuma");
            //espera pasiva mientras su item no sea consumid(es despertado por el consumidor cuando sea oportuno)
            synchronized (this) {
                while (!it.consumido()) {
                    try {wait();} catch (InterruptedException e) {throw new RuntimeException(e);}
                }
            }
        }
    }
    public void avisarConsumidor() {
        for (Consumidor c : consumidores) {
            if(c.productoresAsociados.contains(this)) {
                synchronized (c) {
                    c.notify();
                }
            }
        }
    }
    public synchronized void setProducioAlgo(boolean producioAlgo) {
        this.producioAlgo = producioAlgo;
    }
    public synchronized boolean getProducioAlgo() {
        return producioAlgo;
    }

}
