package cy.ac.ucy.cs.epl231.ID1123114.Project1;

public class StackEmptyException extends Exception {
    private static final long serialVersionUID = 1L;

    StackEmptyException(String err){
        super(err);
    }
}