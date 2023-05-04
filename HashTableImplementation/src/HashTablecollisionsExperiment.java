import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashTablecollisionsExperiment {

  private static final int ITER_COUNT = 100;

  public static void main(String[] args) {
    // you spin me round baby, right round
    long startTime = System.nanoTime();
    while (System.nanoTime() - startTime < 1_000_000_000)
      ;

    //try { // open up a file writer so we can write

    ChainingHashTable hashTableBad;
    ChainingHashTable hashTableMid;
    ChainingHashTable hashTableGood;
    int collision=0;
    File file = new File("dictionary.txt");
      List<String> list;
      list = BadHashFunctor.listOfWords(file);
      Random random = new Random();
      String str = "";
      StringBuilder stringBuilder = new StringBuilder();
      int strLength=0;
      for (int i  = 0; i<10; i++) {
        stringBuilder.append(list.get(i));

        str = stringBuilder.toString();
        BadHashFunctor badHashFunctor = new BadHashFunctor();
        MediocreHashFunctor mediocreHashFunctor = new MediocreHashFunctor();
        GoodHashFunctor goodHashFunctor = new GoodHashFunctor();


        long totalTime = 0;
        for (int iter = 0; iter < ITER_COUNT; iter++) {
          long start = System.nanoTime();

          goodHashFunctor.hash(str);

          //hashTableGood.addAll(list);

          long stop = System.nanoTime();
          totalTime += stop - start;
          strLength =  str.length();




        }
        double averageTime = totalTime / (double) 100;
        System.out.println(strLength + "\t" +  averageTime );
      }






//      for (int exp = 2; exp <= 12; exp++) { // This is used as the exponent to calculate the size of the set.
//        int size = (int) Math.pow(2, exp); // or ..
//        int findElement = random.nextInt(list.size());
//        String stringTofind = list.get(findElement);
//
//        hashTableBad = new ChainingHashTable(size,new BadHashFunctor());
//        hashTableMid= new ChainingHashTable(size,new MediocreHashFunctor());
//        hashTableGood = new ChainingHashTable(size,new GoodHashFunctor());
//
//
//        long totalTime = 0;
//        for (int iter = 0; iter < ITER_COUNT; iter++) {
//          long start = System.nanoTime();
//          hashTableBad.remove(stringTofind);
//
//
//
//        //hashTableGood.addAll(list);
//
//          long stop = System.nanoTime();
//          totalTime += stop - start;
//
//          collision = hashTableBad.getCollision();
//          hashTableBad.add(stringTofind);
//
//
//
//        }
//
//        //double averageCollision = collision / (double) 100;
//        double averageTime = totalTime / (double) 100;
//        System.out.println(size + "\t" +  averageTime );
//        //System.out.println(size + "\t" + averageCollision); // print to console
//      }
//
//
  }
}
