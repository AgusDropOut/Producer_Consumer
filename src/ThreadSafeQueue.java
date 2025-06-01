import java.util.*;

public class ThreadSafeQueue implements Queue {
    private ArrayList<Item> queue = new ArrayList<Item>();

    public synchronized void addItem(Item item) {
        queue.add(item);
    }
    public synchronized Item getItem(int i) {
        return queue.get(i);
    }
    public synchronized Item removeItem(int i) {
        return queue.remove(i);
    }

    @Override
    public synchronized int size() {
        return queue.size();
    }
    public synchronized Item getAndAssignItem(List<Productor> productoresAsociados) {
        for (int i = 0; i < queue.size(); i++) {
            Item item = queue.get(i);
            if (!item.isEstaAsignado() && productoresAsociados.contains(item.getProd())) {
                item.setEstaAsignado(true);
                return queue.remove(i);
            }
        }
        return null;
    }
























    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public synchronized boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }
}
