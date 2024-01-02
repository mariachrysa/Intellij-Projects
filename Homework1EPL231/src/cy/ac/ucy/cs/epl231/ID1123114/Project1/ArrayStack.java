package cy.ac.ucy.cs.epl231.ID1123114.Project1;

public class ArrayStack<E> implements Stack<E> {
    // Υλοποίηση της Java διασύνδεσης Stack (από το Σχήμα 2.1)
    // Μεταβλητές στιγμιοτύπου
    public static final int MAXSIZE = 10000; //Το προκαθορισμένο μέγιστο μήκος στοίβας
    private int MaxSize;// Το μέγιστο μήκος της στοίβας (το οποίο θα επιλέξουμε)
    private E[] T; 		// Ο γενικευμένος πίνακας T όπου θα κρατούνται τα στοιχεία της στοίβας
    private int t = -1; // Δείκτης στον κόμβο κορυφής της στοίβας
    // Κατασκευαστές
    public ArrayStack() { // Αρχικοποίηση στοίβας με προκαθορισμένο μέγιστο μήκος
        this(MAXSIZE);
    }

    public ArrayStack(int Size) {  // Αρχικοποίηση στοίβας με δεδομένο μέγιστο μήκος
        MaxSize = Size;
        T = (E[]) new Object[MaxSize];
    }

    // Μέθοδοι
    public int size() { // Επιστρέφει το τρέχον μήκος της στοίβας
        return (t + 1);
    }

    public boolean isEmpty() { // Επιστρέφει true αν και μόνο αν η στοίβα είναι κενή
        return (t == -1);
    }

    public void push(E obj) // Εισαγωγή νέου αντικειμένου στη στοίβα
            throws StackFullException {
        if (size() == MaxSize)
            throw new StackFullException("Γέμισε η στοίβα");
        T[++t] = obj;
    }

    public E top() // Επιστροφή αντικειμένου από τη στοίβα
            throws StackEmptyException {
        if (isEmpty())
            throw new StackEmptyException("Η  στοίβα είναι κενή");
        return (T[t]);
    }

    public E pop() // Επιστροφή και διαγραφή αντικειμένου  από τη στοίβα
            throws StackEmptyException{
        if (isEmpty())
            throw new StackEmptyException("Η  στοίβα είναι κενή");
        E answer = T[t];
        T[t] = null;
        t--;
        return  answer;
    }
    public String toString() {
        String s = new String();
        for (int i = 0; i < T.length && T[i] != null; i++) {
            s += T[i] + "->";
        }
        return s;
    }
    public static void main(String[] args) {
        // Παράδειγμα Χρήσης της στοίβας
        Stack<Integer> S = new ArrayStack<>();  // Περιέχει: ()
        try{
            S.push(5);                              // Περιέχει: (5)
            S.push(3);                              // Περιέχει: (5, 3)
            System.out.println(S.size());           // Περιέχει: (5, 3)     Δίνει 2
            System.out.println(S.pop());            // Περιέχει: (5)        Εξάγει 3
            System.out.println(S.isEmpty());        // Περιέχει: (5)        Δίνει false
            System.out.println(S.pop());            // Περιέχει: ()         Εξάγει 5
            System.out.println(S.isEmpty());        // Περιέχει: ()         Εξάγει true
            //System.out.println(S.pop());            // Περιέχει: ()         Exception
            S.push(7);                              // Περιέχει: (7)
            S.push(9);                              // Περιέχει: (7, 9)
            System.out.println(S.top());            // Περιέχει: (7, 9)     Δίνει 9
            S.push(4);                              // Περιέχει: (7, 9, 4)
            System.out.println(S.size());           // Περιέχει: (7, 9, 4)  Εξάγει 3
            System.out.println(S.pop());            // Περιέχει: (7, 9)     Εξάγει 4
            S.push(6);                              // Περιέχει: (7, 9, 6)
            S.push(8);                              // Περιέχει: (7, 9, 6, 8)
            System.out.println(S.pop());            // Περιέχει: (7, 9, 6)  Εξάγει 8
        }catch (StackEmptyException e){
            System.out.println(e);
        }catch (StackFullException ee){
            System.out.println(ee);
        }
    }
}

