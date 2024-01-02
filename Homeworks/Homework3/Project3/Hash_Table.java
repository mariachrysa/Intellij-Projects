package Project3;

import java.util.ArrayList;
import java.util.List;

public class Hash_Table<E extends Iterable<X>, X extends Item<K, ?>, K extends Comparable<? super K>> {
    Sequence<E, X, K> A[];
    int HT_SIZE;
    int size;

    public Hash_Table(int size) {
        HT_SIZE = size;
        A = new Sequence[size];
//		for (int i = 0; i < size; i++)
//			A[i] = new Link_List_Seq<>();
    }
    private int _hash(int k) {
        int h = (10 * k + 4) % HT_SIZE;
        return h;
    }
    public void insert(X x) {
        int h = this._hash((Integer) x.key);
        System.out.println("K:" + x.key + " hash:" + h);
        if (A[h] == null)
            A[h] = new Link_List_Seq<>();
        this.A[h].insert_first(x);
        this.size += 1;
    }

    public X find(K key) {
        int h = this._hash((Integer) key);
        int i = 0;
        X a = null;
        if (A[h] != null && A[h].len() > 0) {
            a = A[h].get_at(i);
            while (a != null && i < A[h].len()-1 &&  a.key.compareTo(key) != 0) {
                a = A[h].get_at(++i);
            }
        }
        if (a == null) return null;
        else return (a.key.compareTo(key) == 0)?a:null;
    }

    public E iter_ord() {
        List<X> data = new ArrayList<X>();
        for (int i = 0; i < A.length; i++) {
            if (A[i] != null) {
                Sequence<E, X, K> a = A[i];
                for (int j = 0; j < a.len(); j++)
                    data.add(a.get_at(j));
            }
        }
        return (E) data;
    }

    private int _hashB(int k, int c) {
        int h = (Math.abs((10 * k + 4) % c) % HT_SIZE);
        return h;
    }

    void partBCollitionTest(Hash_Table<E, X, K> ht) {
        System.out.println("\n--------------------------------------------------------------------------");
        System.out.print("Key\t\t\t|");
        for (Object a : ht.iter_ord())
            System.out.print(((X)a).key + "\t");
        System.out.println("\n--------------------------------------------------------------------------");
        for (int c = 7; c <= 20; c++) {
            System.out.print("h=(10*k+4)% " + c + " %7\t|");
            for (Object a : ht.iter_ord())
                System.out.print(_hashB((Integer) ((X)a).key, c) + "\t");
            System.out.println("");
        }
    }
    // O(|A|)
    public void buildAnagramsStructure(String A, int K) {
        Integer  codeA = 0;
        for (int i = 0; i < A.length() - K; i++) {
            //codeA = 0;
			/*for (int j = 0; j < K; j++) {//codeA += A.charAt(i+j)-'a';
				codeA += 1<<A.charAt(i+j)-'a';
			}*/
            if (i == 0)
                for (int j = 0; j < K; j++) codeA += 1<<A.charAt(i+j)-'a';
            else {
                codeA -= 1<<A.charAt(i-1)-'a';
                codeA += 1<<A.charAt(i+K-1)-'a';
            }
            //System.out.println(codeA);
            int x = 1;
            Item item = this.find((K)codeA);
            if (item != null) {
                x = ((Integer)item.value).intValue();
                x++;
                item.value = Integer.valueOf(x);
            }else this.insert((X)new Item(codeA,x));
        }
    }
    public int findAnagrams(String S) {
        Integer  codeS = 0;
        for (int j = 0; j < S.length(); j++) {
            //codeS += S.charAt(j)-'a';
            codeS += 1<<S.charAt(j)-'a';
        }
        System.out.print("***"+codeS+"***");
        return (int)((X)this.find((K)codeS)).value;
    }

    /**
     * Finds and returns a list of integers from the input array that appear an odd number of times.
     *
     * @param array The input array of integers.
     * @return A list of integers with odd occurrences.
     */
    public List<Integer> findOddOccurrences(int[] array) {
        // Initialize a custom hash table with a capacity of 100
        Hash_Table<List<Item<Integer, Integer>>, Item<Integer, Integer>, Integer> ht = new Hash_Table<>(100);

        // Count the occurrences of each number in the input array
        for (int num : array) {
            // Try to find an item with the same value (integer) in the hash table
            Item<Integer, Integer> item = ht.find(num);

            if (item != null) {
                // If the item is found, increment the count associated with it
                int count = (int) item.value;
                item.value = count + 1;
            } else {
                // If the item is not found, insert a new item with the number and an initial count of 1
                ht.insert(new Item<>(num, 1));
            }
        }

        // Prepare an empty list to store integers with odd occurrences
        List<Integer> result = new ArrayList<>();

        // Iterate through the hash table and find numbers with odd occurrences
        for (Item<Integer, Integer> item : ht.iter_ord()) {
            if ((int) item.value % 2 != 0) {
                // If the count is odd, add the integer to the result list
                result.add(item.key);
            }
        }

        // Return the list of integers with odd occurrences
        return result;
    }

    public static void main(String[] args) {
        Hash_Table<List<Item<Integer, Integer>>, Item<Integer, Integer>, Integer> ht = new Hash_Table<>(100);

        int[] array = {47483644, 47483643, 47483646, 47483642, 47483644, 47483642, 47483643, 47483644, 47483643, 47483643};
        //int[] array = {4, 3, 6, 2, 4, 2, 3, 4, 3, 3};
        List<Integer> oddOccurrences = ht.findOddOccurrences(array);

        System.out.println("Numbers with odd occurrences: " + oddOccurrences);
    }
}