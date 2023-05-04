import java.time.Instant;
import java.util.HashMap;

public class DNSCache {

   //local cache. stores all the answers from google with the corresponding request
    //provides a method to query the cache (hash map of DNSQuestion and DNSRecord (answer) )
    //method to insert a new DNSQuestion and DNSRecord
    public static HashMap<DNSQuestion, DNSRecord> dnsCache=new HashMap<>();

    //returns a DNSRecord associated with the DNSQuestion
    public static DNSRecord queryDNSCache(DNSQuestion question){

     return dnsCache.get(question);

    }

    //adds to the cache a new request and time stamp it with a cache time.
    public static void insertDNSCache(DNSQuestion question, DNSRecord record){

            record.timeCached = Instant.now().getEpochSecond();
            dnsCache.put(question,record);

    }

    //checks if a DNSRecord to the DNSQuestion is in the cache and returns true if
    // it's in cache, and it's TTL is still valid. Removes from cache if in cache and TTL not valid and returns
    //  false. returns false if not in cache.
    public static boolean contains(DNSQuestion question){


        if(!dnsCache.containsKey(question)){
            return false;
        }
        else {
            if(!dnsCache.get(question).isExpired()) {
                return true;
            }
            dnsCache.remove(question,dnsCache.get(question));
            return false;
        }

    }

}
