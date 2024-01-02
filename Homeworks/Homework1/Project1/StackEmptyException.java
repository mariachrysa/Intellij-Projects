package Project1;

public class StackEmptyException extends Exception {
    private static final long serialVersionUID = 1L;

    StackEmptyException(String err){
        super(err);
    }
}