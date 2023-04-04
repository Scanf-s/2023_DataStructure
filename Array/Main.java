import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DoubleArraySeq arr1 = new DoubleArraySeq();
        DoubleArraySeq arr2 = new DoubleArraySeq(15);
        DoubleArraySeq cloneTest = new DoubleArraySeq();
        DoubleArraySeq arrayConcat = new DoubleArraySeq();

        //Try addAfter, start, advance method
        arr1.addAfter(3.2); //add element (manyItems = 1, currentIndex = 0)
        System.out.print("Stored Elements : ");
        System.out.print(arr1.getCurrent() + " ");  //print 3.2
        arr1.addAfter(4.2); //add element (manyItems = 2, currentIndex = 1)
        arr1.addAfter(5.5); //add element (manyItems = 3, currentIndex = 2)
        System.out.println(arr1.getCurrent() + " "); //print 5.5
        System.out.println("------------------------------------------------------");

        arr1.start(); //currentIndex = 0
        arr1.advance(); //currentIndex = 1
        System.out.println("Move to head : " + arr1.getCurrent()); //print 4.2
        System.out.println("------------------------------------------------------");

        //Try addBefore Method
        arr1.addBefore(1.1); //add element before element "4.2" (manyItems = 4, currentIndex = 1)
        arr1.start();
        System.out.println("Elements " );
        for(int i = 1 ; i <= arr1.manyItems; i++){
            System.out.print(arr1.getCurrent() + " ");
            arr1.advance();
        }
        System.out.println("\n------------------------------------------------------");

        //Try addAfter with for loop
        for(int i = 1 ; i <= arr2.getCapacity(); i++){
            arr2.addAfter((double)i); //input [1 ~ 15] to arr2.data
        }
        System.out.println("Last element : " + arr2.getCurrent() + " "); //manyItems = 15, currentIndex = 14 --> array is full!
        arr2.addAfter(22.2);
        System.out.println("Current Capacity : " + arr2.getCapacity() + " "); // array capacity increased by currentCap + 1 --> 16
        System.out.println("Last element : " + arr2.getCurrent()); // print 22.2 --> manyItems = 16, currentIndex = 15;
        System.out.println("------------------------------------------------------");

        //Try addAll method
        arr1.addAll(arr2);
        System.out.println("Combined capacity : " + arr1.getCapacity()); // ( arr1 manyItems = 4 ) + ( arr2 cap = 16 ) + 1 = 21
        arr1.start();
        System.out.print("Elements : ");
        for(int i = 0; i < arr1.manyItems; i++) {
            System.out.print(arr1.getCurrent() + " ");
            arr1.advance();
        }
        System.out.println("\n------------------------------------------------------");

        //Try removeCurrent method
        arr1.start();
        System.out.println("Current head element : " + arr1.getCurrent());
        arr1.removeCurrent(); //remove first element 3.2
        System.out.println("Head Element : " + arr1.getCurrent()); //arr1's first element becomes 1.1
        System.out.println("------------------------------------------------------");

        //Try clone method
        cloneTest = (DoubleArraySeq) arr1.clone(); //cloned
        //if cloned arr "cloneTest" modified, then original arr "arr1" doesn't change.
        cloneTest.removeCurrent(); // remove 1.1
        cloneTest.removeCurrent(); // remove 4.2, then cloneTest's first element becomes 5.5
        System.out.println("Cloned head element : " + cloneTest.getCurrent());
        //but arr1's first element is still 1.1
        System.out.println("Original head element : " + arr1.getCurrent());
        System.out.println("------------------------------------------------------");

        //Try concatenation method
        arrayConcat = DoubleArraySeq.concatenation(arr1, arr2);
        System.out.println("arr1's capacity : " + arr1.getCapacity() + " // arr1's manyItems = " + arr1.manyItems);
        System.out.println("arr2's capacity : " + arr2.getCapacity() + " // arr2's manyItems = " + arr2.manyItems);
        System.out.println("combined arr's capacity : " + arrayConcat.getCapacity());
        System.out.println("------------------------------------------------------");

        //Try trimToSize method
        arrayConcat.trimToSize();
        System.out.println("Trimmed size : " + arrayConcat.getCapacity());
    }
}