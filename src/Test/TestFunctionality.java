package Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import Source2048.Play;
import Source2048.Position;

public class TestFunctionality {

  @Test
  public void TestNextPosMethod() {
	  
	  	Play play=new Play();
	  	Position next=play.nextPos(new Position(0,0), "right");
	  	Assert.assertEquals(next.getR(),1);
	  	Assert.assertEquals(next.getC(),3);
	  	
	  	next=play.nextPos(new Position(3,0), "up");
	  	Assert.assertEquals(next.getR(),0);
	  	Assert.assertEquals(next.getC(),1);
	  	
  }
  /*
   * Time permitted, I would have added more test class and methods
   */
  
  
  
}
