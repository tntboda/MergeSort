import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * Solution is interactive class just for demonstration sorter workprocess.
 */


public class Solution {

    static int cores = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {
        /*
         * Ask user for "num" of element to sort
         */
        Scanner scanner =new Scanner(System.in);
        System.out.print("Сколько эллементов будем сортировать?: ");
        int num = scanner.nextInt();
        scanner.close();

        System.out.println("Заполняю массив...");
        ArrayList <Integer> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        System.out.println("Массив перемешан");

        /*
         * Magic starts.
         */
        System.out.println("Сортирую... Время начала: "+ new Date());
        Sorter res = new Sorter(list);
        ForkJoinPool fjk = new ForkJoinPool(cores);
        fjk.invoke(res);
        list = new ArrayList<>(res.getList());

        System.out.println("Закончил. Время конца: "+ new Date());
    }
}