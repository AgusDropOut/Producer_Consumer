import java.util.List;
import java.util.Queue;

public class Consumidor extends Thread {

    List<Productor> productoresAsociados;
    ThreadSafeQueue queue;
    boolean listoParaConsumir = false;
    int id;
    public Consumidor(List<Productor> productoresAsociados, int id, ThreadSafeQueue queue) {
        this.productoresAsociados = productoresAsociados;
        this.id = id;
        this.queue=queue;
    }
    public void run() {
        Item item=null;
        while (true) {
            //Espera pasiva si no hay items del productor asociado en la cola(solo es despertado por el productor)
            synchronized (this) {
                while (!listoParaConsumir) {
                    try {wait();} catch (InterruptedException e) {throw new RuntimeException(e);}
                }
            }
            //Por cada productor asociado, si la cola tiene algun item de ellos se agarra atomicamente, el monitor es por productor, para evitar que dos
            //consumidores se peleen por los items de un mismo consumidor, ademas se pregunta si existe alguno y se agarra atomicamente para evitar race conditions.
            for (Productor productor : productoresAsociados) {
                synchronized (productor) {
                    for (int i = 0; i<queue.size(); i++) {
                        if(queue.getItem(i).getProd().equals(productor)) {
                            item = queue.getItem(i);
                            queue.removeItem(i);
                            //si agarro un iteam salimos del for y vamos a consumirlo para dejarle el paso a los demas consumidores de ese mismo productor.
                            break;
                        }
                    }
                }
                //simulacion de consumision
                try {sleep(2000);} catch (InterruptedException e) {throw new RuntimeException(e);}
                //monitor asociado a productor del item consumido, dentro se le despierta avisandole que su item fue consumido para que pueda
                //seguir trabajando
                synchronized (item.getProd()) {
                    item.setConsumido();
                    System.out.println(id + "_termino_de_consumir_item_" + item.getId());
                    this.listoParaConsumir = false;
                    item.getProd().notify();
                }
            }



        }
    }
}
