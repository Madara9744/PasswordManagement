import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentMap;

public class Manager {
  public static void main(String[] args) {

    Instant start = Instant.now();
    int iterations = 100;
    System.out.println("-----------------------------------------------------");
    for (int i = 0; i < iterations; i++) {
      test(i);
    }
    Instant end = Instant.now();
    Duration timeElapsed = Duration.between(start, end);
    System.out.println("-----------------------------------------------------");
    System.out.println("Time taken for entire operation : "+ timeElapsed.toMillis() +" milliseconds "+" for iterations "+iterations);
  }

  public static void test(int i){
      String name=getAlphaNumericString(i);
      Instant start = Instant.now();
      DB db = DBMaker.fileDB("file.db").make();
      ConcurrentMap<String,String> map = (ConcurrentMap<String, String>) db.hashMap(name).createOrOpen();
      map.put(name, name);
      map.get(name);
      db.close();
      Instant end = Instant.now();
      Duration timeElapsed = Duration.between(start, end);
      System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");

      String name1=getAlphaNumericString(i);
      Instant start1 = Instant.now();
      DB db1 = DBMaker
              .fileDB("file.db")
              .fileMmapEnable()
              .make();
      ConcurrentMap<String,String> map1 = db1
              .hashMap(name1, Serializer.STRING, Serializer.STRING)
              .createOrOpen();
      map1.put(name1,name1);
      map1.get(name1);
      db1.close();
      Instant end1 = Instant.now();
      Duration timeElapsed1 = Duration.between(start1, end1);
      System.out.println("Time taken efficient: "+ timeElapsed1.toMillis() +" milliseconds");

      String name2=getAlphaNumericString(i);
      Instant start2 = Instant.now();
      DB db2 = DBMaker.fileDB("file.db").make();
      ConcurrentMap map2 = db2.hashMap(name2).createOrOpen();
      map2.put(name2, name2);
      map2.get(name2);
      db2.close();
      Instant end2 = Instant.now();
      Duration timeElapsed2 = Duration.between(start2, end2);
      System.out.println("Time taken efficient: "+ timeElapsed2.toMillis() +" milliseconds");

      System.out.println("===================================================");
  }


    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }



}
