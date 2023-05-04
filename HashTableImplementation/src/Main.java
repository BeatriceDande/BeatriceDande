import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        File file = new File("dictionary.txt");
        ChainingHashTable hashTable = new ChainingHashTable(2900,new BadHashFunctor());
        ChainingHashTable hashTable2 = new ChainingHashTable(2900,new MediocreHashFunctor());
        ChainingHashTable hashTable3 = new ChainingHashTable(2900,new GoodHashFunctor());
        List<String> list2=BadHashFunctor.listOfWords(file);
        List<String> list1 = new ArrayList<>();
        list1.add("beatrice");
        list1.add("look");
        list1.add("here");
        list1.add("there");
        list1.add("salesperson");

        System.out.println(list2);
        hashTable.addAll(list2);
        hashTable2.addAll(list2);
        hashTable3.addAll(list2);
        hashTable.add("look");
        //System.out.println(list2.size());
        System.out.println(hashTable);
        System.out.println(list2.contains("look"));
        System.out.println(hashTable.addAll(list1));
        System.out.println(hashTable.getCollision());
        System.out.println(hashTable2.getCollision());
        System.out.println(hashTable3.getCollision());





//        hashTable.add("beatrice");
//        hashTable.add("look");
//        hashTable.add("here");
//        hashTable.add("there");
//        hashTable.add("ok");
//        hashTable.add("bye");
//        hashTable.add("dande");
//        hashTable.add("kenya");
//        hashTable.add("nairobi");
//        hashTable.add("good");
//        hashTable2.add("beatrice");
//        hashTable2.add("look");
//        hashTable2.add("here");
//        hashTable2.add("there");
//        hashTable2.add("ok");
//        hashTable2.add("bye");
//        hashTable2.add("dande");
//        hashTable2.add("kenya");
//        hashTable2.add("nairobi");
//        hashTable2.add("good");
//
//        hashTable3.add("beatrice");
//        hashTable3.add("look");
//        hashTable3.add("here");
//        hashTable3.add("there");
//        hashTable3.add("ok");
//        hashTable3.add("bye");
//        hashTable3.add("dande");
//        hashTable3.add("kenya");
//        hashTable3.add("nairobi");
//        hashTable3.add("good");

//      ArrayList<String> strings = BadHashFunctor.buildStringList(2);

//        hashTable2.addAll(strings);
//        hashTable3.addAll(strings);

//        System.out.println(hashTable.toString());

//        System.out.println(hashTable.toString());
//        hashTable.remove("dande");
//        hashTable.remove("bye");
//        hashTable.remove("beatrice");
//        System.out.println(hashTable.contains("dande"));
//
//        System.out.println(hashTable.contains("nairobi"));
        //System.out.println(hashTable.toString());
       // System.out.println(hashTable.size_);
//        System.out.println(hashTable.toString());
//        System.out.println("Collision Bad for functor: " + hashTable.getCollision());
//        System.out.println(hashTable2.toString());
//        System.out.println("Collision Mediocre for functor: " + hashTable2.getCollision());
//        System.out.println(hashTable3.toString());
//        System.out.println("Collision Good for functor: " + hashTable3.getCollision());
    }
}