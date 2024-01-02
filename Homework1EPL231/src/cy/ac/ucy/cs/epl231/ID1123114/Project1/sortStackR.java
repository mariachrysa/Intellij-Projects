package cy.ac.ucy.cs.epl231.ID1123114.Project1;

import java.util.Arrays;
import java.util.List;

/**
 * The sortStackR class takes a stack of any numbers and sorts them in ascending
 * order using recursion
 * @author Maria Chrysanthou
 * @since 27/09/2023
 * @param <T> generic type T
 */
public class sortStackR<T extends Comparable<T>> {

    /**
     * Empty constructor
     */
    public sortStackR() {
    }

    /**
     * This method checks if the stack is empty or key is greater than the
     * top value of the stack, which will push the key into the stack.
     * Otherwise, it pops the top item and stores it temporarily so that
     * it can recursively call sortedInsert with the updated stack and key,
     * until key is sorted into the stack. Then the top item is pushed
     * back into the stack in the correct order.
     * @param stack the given stack of a generic type T
     * @param key the item needed to be sorted
     */
    public void sortedInsert(Stack<T> stack, T key) {
        try {
            if (stack.isEmpty() ||  key.compareTo((T)stack.top()) > 0) {        // If the key value is greater than the top value
                stack.push(key);                                                // or stack is not empty, push the key onto the
                return;                                                         // stack and return
            }
            T top = (T) stack.pop();        // Pop the top value and save it temporarily in top
            sortedInsert(stack, key);       // Recursive call with the updated stack and key
            stack.push(top);                // Push the top value into the stack
        }
        catch(StackEmptyException e){       // Catch the exception of stack being empty
            e.printStackTrace();
        }
        catch(StackFullException e){        // Catch the exception of stack being full
            e.printStackTrace();
        }
    }

    /**
     * The method sortStack checks if the given stack is not empty, and it then pops an item from
     * the stack and saves it temporarily in item. Recursively it recalls sortStack with the updated
     * stack, and then calls sortedInsert so that the item will be inserted into the stack in ascending order.
     * @param stack the given stack of a generic type T
     */
    public void sortStack(Stack<T> stack) {
        try {
            if (!stack.isEmpty()) {
                T item = (T) stack.pop();       // Pop a value and save it temporarily in item
                sortStack(stack);               // Recursive call of sortStack with the updated stack
                sortedInsert(stack, item);      // Inserts the item back into the stack in order with sortedInsert
            }
        }
        catch (StackEmptyException e){          // catch the exception of stack being empty
            e.printStackTrace();
        }
    }

    /**
     * The main method takes a list of integers and pushes them onto the stack using a for loop.
     * By calling the sortStack method the process of sorting the items recursively begins, and any
     * full stack exception is caught. The stack is printed before and after sorting.
     * @param args
     */
    public static void main(String[] args) {
        try {
            List<Integer> list = Arrays.asList(5, -6, 9, -11, 25, 99, -99, 10, -45, 20);
            Stack<Integer> stack = new ArrayStack<>();
            sortStackR<Integer> ss = new sortStackR<>();
            for (Integer values : list) {
                stack.push(values);             // Create the stack by pushing every value of the list in it
            }
            System.out.println("Stack before sorting: " + stack);
            ss.sortStack(stack);            // Call sortStack to sort it in ascending order
            System.out.println("Stack after sorting: " + stack);
        } catch (StackFullException e) {
            e.printStackTrace();
        }
    }
}

