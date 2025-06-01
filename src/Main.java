import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ThreadSafeQueue queue = new ThreadSafeQueue();

        // Crear consumidores con diferentes productores asociados
        List<Consumidor> consumidores = new ArrayList<>();



        // Crear productores y asociarlos con los consumidores
        Productor productor0 = new Productor(queue, 5, 0, consumidores);
        Productor productor1 = new Productor(queue, 5, 1, consumidores);
        Productor productor2 = new Productor(queue, 5, 2, consumidores);

        Thread t0 = new Thread(productor0);
        Thread t1 = new Thread(productor1);
        Thread t2 = new Thread(productor2);

        // Iniciar productores
        t0.start();
        t1.start();
        t2.start();


        Consumidor consumidor1 = new Consumidor(Arrays.asList(productor0,productor1), 0, queue);
        Consumidor consumidor2 = new Consumidor(Arrays.asList(productor1,productor2), 1, queue);

        consumidores.add(consumidor1);
        consumidores.add(consumidor2);

        // Iniciar consumidores
        for (Consumidor c : consumidores) {
            c.start();
        }
    }
}



