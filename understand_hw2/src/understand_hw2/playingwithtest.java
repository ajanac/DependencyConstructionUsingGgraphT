package understand_hw2;

import org.junit.runner.JUnitCore;
import junit.framework.*;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class playingwithtest {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(playingwithtestTest.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
}  