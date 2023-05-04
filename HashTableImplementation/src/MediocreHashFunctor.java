public class MediocreHashFunctor implements  HashFunctor{
    @Override
    public int hash(String item) { //sum of ASCII values of each character in the item
        int hash = 0;
        for (int i = 0; i < item.length(); i++) {
            hash = (hash + item.charAt(i));
        }
        return Math.abs(hash);
    }
}
