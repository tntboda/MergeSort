import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Class Sorter sort the array passed into with merge sort using fork/join framework.
 * Returns the sorted array with getList().
 *
 * @author Bohdan k.
 * @last_upd 07.10.21 03:18
 * @since 1.00
 */


public class Sorter extends RecursiveAction {

    private List<Integer> list;
    private List<Integer> half_array1;
    private List<Integer> half_array2;

    public Sorter(List<Integer> list) {
        this.list = list;
        int size = list.size() % 2 == 0 ? list.size() : list.size() + 1;
        half_array1 = new ArrayList<>(List.copyOf(list).subList(0, size / 2));
        half_array2 = new ArrayList<>(List.copyOf(list).subList(size / 2, list.size()));
    }

    @Override
    protected void compute() {
        if (half_array1.size() <= 2 && half_array2.size() <= 2) {
            sort();
        } else {
            Sorter sorter1 = new Sorter(half_array1);
            Sorter sorter2 = new Sorter(half_array2);

            sorter1.fork();
            sorter2.fork();

            sorter1.join();
            sorter2.join();

            half_array1 = sorter1.getList();
            half_array2 = sorter2.getList();

            sort();
        }
    }

    /**
     * This method sort elements from list.
     * Uses an copies of half_array 1-2 instead of Iterator.
     *
     * ha1dev = half array 1 deviation
     */
    public void sort() {
        list = new ArrayList<>();
        List<Integer> copy1 = List.copyOf(half_array1);
        List<Integer> copy2 = List.copyOf(half_array2);

        int ha1dev = 0;
        int ha2dev = 0;

        while (copy1.size() > ha1dev && copy2.size() > ha2dev) {

            int ha1 = copy1.get(ha1dev);
            int ha2 = copy2.get(ha2dev);

            if (ha1 < ha2) {
                list.add(ha1);
                half_array1.remove(0);
                ha1dev++;
            } else if (ha2 < ha1) {
                list.add(ha2);
                half_array2.remove(0);
                ha2dev++;
            } else if (ha1 == ha2) {
                list.add(ha1);
                list.add(ha2);
                half_array1.remove(0);
                half_array2.remove(0);
                ha1dev++;
                ha2dev++;
            }
        }

        if (half_array1.isEmpty() && !half_array2.isEmpty()) {
            list.addAll(half_array2);
            half_array2.clear();
        } else if (half_array2.isEmpty() && !half_array1.isEmpty()) {
            list.addAll(half_array1);
            half_array1.clear();
        }
    }

    public List<Integer> getList() {
        return list;
    }
}