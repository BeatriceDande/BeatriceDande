import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChainingHashTableTest {

    @org.junit.jupiter.api.Test
    void add() {
        ChainingHashTable hashTable = new ChainingHashTable(10,new GoodHashFunctor());
        assertTrue(hashTable.isEmpty());
        assertFalse(hashTable.contains("here"));
        hashTable.add("beatrice");
        hashTable.add("look");
        hashTable.add("here");
        hashTable.add("there");
        hashTable.add("ok");
        hashTable.add("bye");
        hashTable.add("dande");
        hashTable.add("kenya");
        hashTable.add("nairobi");
        hashTable.add("good");
        assertFalse(hashTable.isEmpty());
        assertTrue(hashTable.contains("bye"));


    }

    @Test
    void addAll() {
        ChainingHashTable hashTable2 = new ChainingHashTable(10,new GoodHashFunctor());
        List<String> list1 = new ArrayList<>();
        list1.add("beatrice");
        list1.add("look");
        list1.add("here");
        list1.add("there");
        list1.add("ok");
        list1.add("bye");
        list1.add("dande");
        list1.add("kenya");
        list1.add("nairobi");
        list1.add("good");
        hashTable2.addAll(list1);
        assertTrue(hashTable2.containsAll(list1));
        hashTable2.add("what");
        assertTrue(hashTable2.contains("ok"));
        hashTable2.remove("kenya");
        assertFalse(hashTable2.containsAll(list1));
    }

    @org.junit.jupiter.api.Test
    void clear() {
        ChainingHashTable hashTable = new ChainingHashTable(10,new GoodHashFunctor());
        hashTable.add("beatrice");
        hashTable.add("look");
        hashTable.add("here");
        hashTable.add("there");
        hashTable.add("ok");
        hashTable.add("bye");
        hashTable.add("dande");
        hashTable.add("kenya");
        hashTable.add("nairobi");
        hashTable.add("good");
        assertFalse(hashTable.isEmpty());
        hashTable.clear();
        assertTrue(hashTable.isEmpty());
        assertEquals(hashTable.size(),0);


    }

    @org.junit.jupiter.api.Test
    void contains() {
        ChainingHashTable hashTable = new ChainingHashTable(10,new GoodHashFunctor());
        assertTrue(hashTable.isEmpty());
        assertFalse(hashTable.contains("null"));
        assertFalse(hashTable.contains("here"));
        hashTable.add("beatrice");
        hashTable.add("look");
        hashTable.add("here");
        hashTable.add("there");
        hashTable.add("ok");
        hashTable.add("bye");
        hashTable.add("dande");
        hashTable.add("kenya");
        hashTable.add("nairobi");
        hashTable.add("good");
        assertFalse(hashTable.isEmpty());
        assertTrue(hashTable.contains("bye"));

    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        ChainingHashTable hashTable2 = new ChainingHashTable(10,new GoodHashFunctor());
        List<String> list1 = new ArrayList<>();
        list1.add("beatrice");
        list1.add("look");
        list1.add("here");
        list1.add("there");
        list1.add("ok");
        list1.add("bye");
        list1.add("dande");
        list1.add("kenya");
        list1.add("nairobi");
        list1.add("good");
        hashTable2.addAll(list1);
        assertTrue(hashTable2.containsAll(list1));
        hashTable2.add("what");
        assertTrue(hashTable2.contains("ok"));
        hashTable2.remove("kenya");
        assertFalse(hashTable2.containsAll(list1));
    }



    @org.junit.jupiter.api.Test
    void isEmpty() {
        ChainingHashTable hashTable = new ChainingHashTable(10,new GoodHashFunctor());
        assertTrue(hashTable.isEmpty());

        hashTable.add("ok");
        hashTable.add("bye");
        hashTable.add("nairobi");
        hashTable.add("good");
        assertFalse(hashTable.isEmpty());


    }

    @org.junit.jupiter.api.Test
    void remove() {
        ChainingHashTable hashTable = new ChainingHashTable(10,new GoodHashFunctor());
        assertTrue(hashTable.isEmpty());

        hashTable.add("ok");
        assertFalse(hashTable.isEmpty());
        hashTable.remove("ok");
        assertFalse(hashTable.contains("ok"));
        assertTrue(hashTable.isEmpty());
        hashTable.add("ok");
        hashTable.add("bye");
        hashTable.add("nairobi");
        hashTable.add("good");
       assertTrue (hashTable.contains("ok"));
        hashTable.remove("good");
        assertFalse(hashTable.contains("good"));
        hashTable.remove("bye");
        assertFalse(hashTable.contains("bye"));
        hashTable.remove("nairobi");
        assertFalse(hashTable.contains("nairobi"));


        assertFalse(hashTable.isEmpty());

    }

    @org.junit.jupiter.api.Test
    void removeAll() {
        ChainingHashTable hashTable2 = new ChainingHashTable(10,new GoodHashFunctor());
        List<String> list1 = new ArrayList<>();
        list1.add("beatrice");
        list1.add("look");
        list1.add("here");
        list1.add("there");
        list1.add("ok");
        list1.add("bye");
        list1.add("dande");
        list1.add("kenya");
        list1.add("nairobi");
        list1.add("good");
        hashTable2.addAll(list1);
        assertTrue(hashTable2.containsAll(list1));
        hashTable2.add("what");
        hashTable2.add("good");
        assertTrue(hashTable2.contains("ok"));
        hashTable2.removeAll(list1);
        assertFalse(hashTable2.containsAll(list1));

    }

    @org.junit.jupiter.api.Test
    void size() {
        ChainingHashTable hashTable2 = new ChainingHashTable(10,new GoodHashFunctor());
        List<String> list1 = new ArrayList<>();
        list1.add("beatrice");
        list1.add("look");
        list1.add("here");
        list1.add("there");
        list1.add("ok");
        list1.add("bye");
        list1.add("dande");
        list1.add("kenya");
        list1.add("nairobi");
        list1.add("good");
        hashTable2.addAll(list1);
        assertEquals(hashTable2.size(),10);
        assertTrue(hashTable2.containsAll(list1));
        hashTable2.add("what");
        hashTable2.add("good");
        assertEquals(hashTable2.size(),11);

        hashTable2.removeAll(list1);
        assertEquals(hashTable2.size(),1);
        hashTable2.addAll(list1);
        hashTable2.add("what");
        assertEquals(hashTable2.size(),11);




    }
}