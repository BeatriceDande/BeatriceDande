import java.util.Random;

public class GoodHashFunctor implements HashFunctor{
    @Override
    public int hash(String item) { //prime number (31) * sum of ASCII values of all characters in an item

        int hash = 0;
        for (int i = 0; i < item.length(); i++) {
            hash = (hash + item.charAt(i))*31;
        }
        return Math.abs(hash);
    }

}
