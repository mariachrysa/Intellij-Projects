package cy.ac.ucy.cs.epl231.ID1123114.Project2;

import java.util.Arrays;

/**
 * This class receives a sorted array and with the use of Binary Search it
 * locates the first and last occurence of each integer. Then with a for
 * loop it iterates through each unique number in the function elementCount
 * and prints the amount of times each integer appears to be in the array.
 */
public class countElements {

    /**
     * This fucntion iterates through the sorted array A and performs
     * binary search to find the first time the key integer appears
     * @param A the sorted array for search
     * @param key the specific integer to search for
     * @return the index of the first occurence of the key integer
     */
    static int binarySearchFirst(int[] A, int key) {
        int left = 0;
        int right = A.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (A[mid] == key) {
                result = mid;
                right = mid - 1; // Continue searching on the left side
            } else if (A[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    /**
     * This function iterates through the sorted array A and performs
     * binary search to find the last time the key integer appears
     * @param A the sorted array for search
     * @param key the specific integer to search for
     * @return the index of the last occurence of the key integer
     */
    static int binarySearchLast(int[] A, int key) {
        int left = 0;
        int right = A.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (A[mid] == key) {
                result = mid;
                left = mid + 1; // Continue searching on the right side
            } else if (A[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    /**
     * This function counts and prints each occurence of the
     * unique integers by calculating it in a for loop for
     * each number in the sorted array.
     * @param A the sorted integer array
     */
    public static void elementCount(int[] A) {
        int n = A.length;
        int prev = Integer.MIN_VALUE; // Initialize prev with a value that won't be in the array

        for (int i = 0; i < n; i++) {
            if (A[i] != prev) {
                // Find the first and last occurrence of A[i]
                int first = binarySearchFirst(A, A[i]);
                int last = binarySearchLast(A, A[i]);

                // Calculate the count of A[i] and store it in the map
                int count = last - first + 1;
                System.out.println(A[i] + ": " + count);

                // Update prev to the current unique integer
                prev = A[i];
            }
        }
    }

    /**
     * The main method for demonstrating the algorithm with binary search
     * @param args command line argument purposes
     */
    public static void main(String[] args) {
        int[] sortedArray = {2,2,2,3,3,3,4,4,5,8,8,9};         // The sorted array
        int [] sortedArray2 = {1,1,1,1,1};
        System.out.print("Given array: ");
        for (int i = 0; i < sortedArray.length; i++){
            System.out.print(sortedArray[i] + ", ");
        }
        Arrays.sort(sortedArray);               // Ensure the array is sorted
        System.out.println("\nAmount of times shown for:");
        elementCount(sortedArray);              // Call the function and print the amount of each unique integers


        System.out.print("\nGiven array: ");
        for (int i = 0; i < sortedArray2.length; i++){
            System.out.print(sortedArray[i] + ", ");
        }
        Arrays.sort(sortedArray2);               // Ensure the array is sorted
        System.out.println("\nAmount of times shown for:");
        elementCount(sortedArray2);              // Call the function and print the amount of each unique integers
    }
}


