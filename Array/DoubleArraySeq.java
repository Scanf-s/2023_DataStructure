public class DoubleArraySeq implements Cloneable {
    /**
     * A DoubleArraySeq keeps track of a sequence numbers.
     * The sequence can have a special "current element",
     * which is specified and accessed through four methods
     * that are not available in the bag class (start, getCurrent, advance and isCurrent).
     */

    /**
     * Invariant of the DoubleArraySeq class:
     *
     * 1. The capacity of a sequence can change after it's created,
     * but the maximum capacity is limited by the amount of free memory on the machine.
     * The constructor, addAfter, addBefore, clone, and concatenation will
     * result in an OutOfMemoryError when free memory is exhausted.
     *
     * 2. A sequence's capacity cannot exceed the largest integer 2,147,483,647 (Integer.MAX_VALUE).
     * Any attempt to create a larger capacity results in failure due to an arithmetic overflow.
     */
    public double[] data; //array to store data
    public int manyItems = 0; //number of elements
    public int currentIndex = 0; //array index

    /**
     * Initialize an empty sequence with a specified initial capacity.
     * addAfter and addBefore methods work efficiently until this capacity is reached
     * @param - none
     * @throws OutOfMemoryError Indicates insufficient memory for new double[10].
     * @postcondition This sequence is empty and has an initial capacity of 10.
     **/
    public DoubleArraySeq() {
        data = new double[10];
        manyItems = 0;
        currentIndex = 0;
    }

    /**
     * Initialize an empty sequence with a specified initial capacity.
     * addAfter and addBefore methods work efficiently until this capacity is reached.
     * @param initialCapacity the initial capacity of this sequence
     * @exception IllegalArgumentException Indicates that initialCapacity is negative.
     * @throws OutOfMemoryError         Indicates insufficient memory for: new double[initialCapacity].
     * @precondition initialCapacity is non-negative.
     * @postcondition This sequence is empty and has the given initial capacity.
     **/
    public DoubleArraySeq(int initialCapacity) {
        if(initialCapacity > 0){
            data = new double[initialCapacity];
            manyItems = 0;
            currentIndex = 0;
        }
        else{
            throw new IllegalArgumentException("InitialCapacity is negative");
        }
    }

    /**
     * Change the current capacity of this sequence.
     * @param minimumCapacity the new capacity for this sequence
     * @postcondition This sequence's capacity has been changed to at least minimum Capacity
     * @throws OutOfMemoryError Indicates insufficient memory for : new double[minimumCapacity].
     **/
    public void ensureCapacity(int minimumCapacity){
        double newArr[]; //create new array
        if (getCapacity() < minimumCapacity) { //if current data's array length is not enough
            newArr = new double[minimumCapacity];
            System.arraycopy(data, 0, newArr, 0, manyItems);
            data = newArr;
        }
    }

    /**
     * Accessor method to determine whether this sequence has a specified current element that can
     * be retrieved with the getCurrent method
     * @param - none
     * @return if there is a current element then return true, else return false.
     **/
    public boolean isCurrent(){
        if(currentIndex < manyItems) return true;
        else return false;
    }

    /**
     * Accessor method to determine the current element of this sequence
     * @returns the current element of this sequence.
     * @param - none
     * @precondition isCurrent() returns true
     * @exception IllegalStateException Indicates that there is no current element
     **/
    public double getCurrent(){
        if(isCurrent()){
            return data[currentIndex];
        }
        else throw new IllegalStateException("There is no current element");
    }

    /**
     * Accessor method to determine the current capacity of this sequence.
     * The addBefore and addAfter methods works efficiently until this capacity is reached.
     * @param - none
     * @return the current capacity of this sequence
     **/
    public int getCapacity() { return data.length; }

    /**
     * Remove the current element from this sequence
     * @param - none
     * @precondition isCurrent() returns true.
     * @postcondition
     * The current element has been removed from this sequence
     * and the following element is now the new current element.
     * If there was no following element, then there is now no current element
     * @exception IllegalStateException
     * Indicates that there is no current element, so removeCurrent may not be called.
     **/
    public void removeCurrent(){
        if(isCurrent()){
            for(int i = currentIndex; i < manyItems; i++){
                data[i] = data[i + 1]; //move next elements to target
            }
            manyItems--; //number of elements decreases.
        }
        else throw new IllegalStateException("there is no current element");
    }

    /**
     * Accessor method to determine the number of elements in this sequence.
     * @param - none
     * @return the number of elements in this sequence
     */
    public int size(){
        return manyItems;
    }

    /**
     * Set the current element at the front of this sequence
     * @param - none
     * @postcondition
     * The front element of this sequence is now the current element
     * @exception IllegalStateException this sequence has no elements at all
     */
    public void start(){
        if (manyItems > 0) currentIndex = 0;
        else throw new IllegalStateException("This sequence has no elements at all");
    }

    /**
     * Move foward, so that the current element is now the next element in this sequence
     * @param - none
     * @precondition isCurrent() returns true.
     * @postcondition
     * If the current element was already the end element of this sequence,
     * then there is no longer any current element. Otherwise, the new element is the element
     * immediately after the original current element.
     * @exception IllegalStateException Indicates that there is no current element, so advance may not be called.
    **/
    public void advance(){
        if (isCurrent()) {
            currentIndex++;
        }
        else throw new IllegalStateException("there is no current element");
    }

    /**
     * Reduce the current capacity of this sequence to its actual size
     * @param - none
     * @postcondition
     * this sequence's capacity has been changed to its current size.
     * @throws OutOfMemoryError Indicates insufficient memory for altering the capacity.
     */
    public void trimToSize(){
        try {
            if (data.length > manyItems) {
                double[] newArr = new double[manyItems];
                System.arraycopy(data, 0, newArr, 0, manyItems);
                data = newArr;
            }
        }
        catch(OutOfMemoryError err){
            throw new OutOfMemoryError("insufficient memory for altering the capacity.");
        }
    }

    /**
     * Generate a copy of this sequence.
     * @param - none
     * @return
     * The return value is a copy of this sequence. Subsequent changes to the copy will no affect the original, nor vice versa.
     * The return value must be typecast to a DoubleArraySeq before it is used.
     * @throws OutOfMemoryError Indicates insufficient memory for creating the clone.
     */
    public Object clone(){
        DoubleArraySeq cloneSequence = null; //initialize
        try {
            cloneSequence = (DoubleArraySeq) super.clone(); //if this class doesn't support clone, we have to handle the exception
        }
        catch(CloneNotSupportedException err){
            System.out.println("This class doesn't support clone!");
        }

        cloneSequence.data = data.clone();
        return cloneSequence;
    }

    /**
     * Create a new sequence that contains all the elements from one sequence followed by another.
     * @param
     * s1 - the first of two sequences
     * s2 - the second of two sequences
     * @precondition
     * Neither s1 nor s2 is null
     * @return
     * A new sequence that has the elements of s1 followed by the elements of s2
     * @exception NullPointerException Indicates that one of the arguments is null
     * @throws OutOfMemoryError Indicates insufficient memory for the new sequence
     * @note
     * An attempt to increase the capacity beyond Integer.MAX_VALUE will cause this sequence
     * to fail with an arithmetic oveflow.
     */
    public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2) {
        DoubleArraySeq concatenatedArr = null;
        try {
            concatenatedArr = new DoubleArraySeq(s1.manyItems + s2.manyItems);
            System.arraycopy(s1.data, 0, concatenatedArr.data, 0, s1.manyItems);
            System.arraycopy(s2.data, 0, concatenatedArr.data, s1.manyItems, s2.manyItems);
            concatenatedArr.manyItems = s1.manyItems + s2.manyItems;
        } catch (NullPointerException err) {
            System.out.println("Neither s1 nor s2 is null!");
        }
        return concatenatedArr;
    }

    /**
     * Adds a new element to this sequence, either before or after the current element.
     * If this new element would take this sequence beyond its current capacity, the the capacity is increased
     * before adding the new element
     * @param element Indicates that the new element that is being added
     * @throws OutOfMemoryError Indicates insufficient memory to increase the size of this sequence.
     * @postcondition
     * A new copy of the element has been added to this sequence
     * If there was a current element, then addAfter places the new element after the current element
     * If there was no current element, then addAfter places the new element at the end of this sequence
     * In all cases, the new element becomes the new current element of this sequence
     * @note
     * An attempt to increase the capacity beyond Integer.MAX_VALUE will cause this sequence to fail
     * with an arithmetic overflow.
     **/
    public void addAfter(double element) {
        if(getCapacity() - 1 == currentIndex) ensureCapacity(getCapacity() + 1); //ensure the array Capacity
        if(isCurrent()) { //if there is an element
            for(int i = manyItems; i > currentIndex; i--){
                data[i] = data[i - 1]; //move all after elements to backward to make a place
            }
            data[currentIndex + 1] = element;
            manyItems++;
            advance(); //if an element added by addAfter, then currentIndex have to move next
        }
        else {
            data[currentIndex] = element;
            manyItems++;
            //we don't have to call advance() because it has already in the end of the sequence
        }
    }

    /**
     * Adds a new element to this sequence, either before or after the current element.
     * If this new element would take this sequence beyond its current capacity, the capacity is increased
     * before adding the new element
     * @param element Indicates that the new element that is being added
     * @throws OutOfMemoryError Indicates insufficient memory to increase the size of this sequence.
     * @precondition
     * A new copy of the element has been added to this sequence
     * If there was a current element, then addBefore places the new element before the current element.
     * If there was no current element, then addBefore places the new element at the front of this sequence
     * In all cases, the new element becomes the new current element of this sequence
     * @postcondition This sequence is empty and has the given initial capacity.
     * @note
     * An attempt to increase the capacity beyond Integer.MAX_VALUE will cause this sequence to fail
     * with an arithmetic overflow.
     **/
    public void addBefore(double element){
        ensureCapacity(getCapacity() + 1); //ensure the array Capacity
        if(isCurrent()) { //if there is an element
            for(int i = manyItems; i > currentIndex; i--){
                data[i] = data[i - 1]; //move all after elements to backward to make a place
            }
            data[currentIndex] = element;
            manyItems++;
        }
        else { //if there was no current element, it places the new element at the FRONT of this sequence
            for(int i = manyItems; i > 0; i--){
                data[i] = data[i - 1];
            }
            currentIndex = 0; //to place the new element at the front of this sequence
            data[currentIndex] = element;
            manyItems++;
        }
    }

    /**
     * Place the contents of another sequence at the end of this sequence
     * @param addend - a sequence whose contents will be placed at the end of this sequence
     * @Precondition
     * The parameter, addend, is not null
     * @postcondition
     * The elements from addend have been placed at the end of this sequence.
     * The current element of this sequence remains where is was, and the addend is also unchanged.
     * @exception  NullPointerException Indicates that addend is null
     * @throws OutOfMemoryError Indicates insufficient memory to increase the capacity of this sequence.
     * @note
     * An attempt to increase the capacity beyond Integer.MAX_VALUE will cause this sequence to fail
     * with an arithmetic overflow.
     */
    public void addAll(DoubleArraySeq addend){
        ensureCapacity(manyItems + addend.manyItems + 1); //make sure enough space to store data
        System.arraycopy(addend.data, 0, data, manyItems, addend.manyItems);
        manyItems = addend.manyItems + manyItems;
    }
}