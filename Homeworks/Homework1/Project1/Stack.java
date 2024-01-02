package Project1;

public interface Stack<E> {
    //Μέθοδοι πρόσβασης
    public int size(); 			// Αντιστοιχεί στην πράξη Length(S)
    public boolean isEmpty(); 	// Αντιστοιχεί στην πράξη IsEmptyStack(S)
    public   Object top()  		// Αντιστοιχεί στην πράξη  Top(S)
            throws StackEmptyException; // Μήνυμα λάθους αν η στοίβα είναι κενή
    //Μέθοδοι ανανέωσης
    public void push (E obj)  	// Αντιστοιχεί στην πράξη  Push(x, S)
            throws StackFullException;
    public   Object pop()  				// Αντιστοιχεί στην πράξη  Pop(S)
            throws StackEmptyException;  		// Μήνυμα λάθους αν η στοίβα είναι κενή
}
