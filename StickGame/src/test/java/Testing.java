import com.example.stickgame.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Testing {


    @Test
    void TestUserWorking(){
//        Checking user current berries working or not
        User user1 = new User();
        user1.setCurrentBerries(20);
        int berries = user1.getCurrentBerries();
        assertEquals(20,berries);

    }

    @Test
    void TestNumberOfBerries(){
//        Checking whether serialization working or not
        Serialization sg = new Serialization();
        assertTrue(sg.check());
    }

    @Test
    void TestUserTotalBerries(){

//        checking user total berries working or not
        User user2 = new User();
        user2.setHighestberries(10);
        int totalbrerries = user2.getHighestberries();
        assertEquals(10,totalbrerries);

    }
}
