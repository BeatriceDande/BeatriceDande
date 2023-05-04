import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String> {
    private LinkedList<String>[] storage_;
    int size_;
    private int collision_;
    private HashFunctor functor_;
     int capacity_;

    public int getCollision() {
        return collision_;
    }

    @SuppressWarnings("unchecked")
    public ChainingHashTable(int capacity, HashFunctor functor) {
        capacity_=capacity;
        storage_ = (LinkedList<String>[]) new LinkedList[capacity];
        functor_ = functor;
        for (int i = 0; i < storage_.length; i++) {
            storage_[i] = new LinkedList<>();
        }


    }

    public int getIndex(String value) {
        if(capacity_==0){
            throw new ArithmeticException();
        }
        return this.functor_.hash(value) % capacity_;
    }

    /**
     * Ensures that this set contains the specified item.
     *
     * @param item - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually inserted); otherwise, returns false
     */
    @Override
    public boolean add(String item) {
        if(item==null){
            throw new NullPointerException();
        }
        boolean isCollision = false;
        int hashIndex = getIndex(item);
        LinkedList<String> linkedListData = storage_[hashIndex];
        if (!linkedListData.isEmpty()) {
            isCollision = true;
        }
        if (!linkedListData.contains(item)) {
            linkedListData.add(item);
            size_++;
            if (isCollision) {
                collision_++;
            }
            return true;
        }

        return false;
    }

    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * any item in the input collection was actually inserted); otherwise,
     * returns false
     */
    @Override
    public boolean addAll(Collection<? extends String> items) {
        boolean removeAllSuccess = false;
        for (String str : items) {
            if(add(str)){
                removeAllSuccess = true;
            }

        }
        return removeAllSuccess;


    }

    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        for (int i = 0; i < storage_.length; i++) {
            storage_[i] = new LinkedList<>();
        }
        size_=0;

    }


    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     * otherwise, returns false
     */
    @Override
    public boolean contains(String item) {
        int hashIndex = getIndex(item);
        LinkedList<String> linkedListData = storage_[hashIndex];
        if(linkedListData.size()==0){
            return false;
        }
        if (linkedListData.contains(item)) {
            return true;
        }

        return false;

    }

    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     * in this set that is equal to it; otherwise, returns false
     */
    @Override
    public boolean containsAll(Collection<? extends String> items) {
        for (String str : items) {
            if (!contains(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        if (storage_ == null) {
            return true;
        }
        for (int i = 0; i < storage_.length; i++) {
            LinkedList<String> list = storage_[i];
            if (!list.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually removed); otherwise, returns false
     */
    @Override
    public boolean remove(String item) {

        if (storage_ == null) {
            throw new NullPointerException();
        }
        int hashIndex = getIndex(item);
        LinkedList<String> linkedListData = storage_[hashIndex];

        if (!contains(item)) {
            return false;
        }
        linkedListData.remove(item);
        size_--;
        return true;

    }

    /**
     * Ensures that this set does not contain any of the items in the specified
     * collection.
     *
     * @param items - the collection of items whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * any item in the input collection was actually removed); otherwise,
     * returns false
     */
    @Override
    public boolean removeAll(Collection<? extends String> items) {
        boolean removeAllSuccess = false;
        for (String str : items) {
            if(remove(str)){
                removeAllSuccess = true;
            }

        }
        return removeAllSuccess;



    }

    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size_;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < storage_.length; i++) {
            LinkedList<String> list = storage_[i];
            for (int j = 0; j < list.size(); j++) {
                stringBuilder.append(list.get(j));
                stringBuilder.append("->");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
