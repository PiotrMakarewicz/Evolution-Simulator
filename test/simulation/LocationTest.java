package simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Location l1 = new Location(1,4);
    Location l2 = new Location(1,2);
    Location l3 = new Location(3,5);
    Location l4 = new Location(3,0);
    @Test
    void compareTo() {
        assertTrue(l1.compareTo(l2) > 0);
        assertTrue(l1.compareTo(l3) < 0);
        assertTrue(l1.compareTo(l4) < 0);
        assertTrue(l2.compareTo(l1) < 0);
        assertTrue(l2.compareTo(l3) < 0);
        assertTrue(l2.compareTo(l4) < 0);
        assertTrue(l3.compareTo(l4) > 0);
        assertTrue(l2.compareTo(l1) < 0);
        assertTrue(l3.compareTo(l1) > 0);
        assertTrue(l4.compareTo(l1) > 0);
        assertTrue(l1.compareTo(l2) > 0);
        assertTrue(l3.compareTo(l2) > 0);
        assertTrue(l4.compareTo(l2) > 0);
        assertTrue(l4.compareTo(l3) < 0);
        assertTrue(l1.compareTo(l1) == 0);
        assertTrue(l2.compareTo(l2) == 0);
        assertTrue(l3.compareTo(l3) == 0);
        assertTrue(l4.compareTo(l4) == 0);
    }

    @Test
    public void getRandom(){
        assertTrue(Location.getRandom(3,8).getX()<3);
        assertTrue(Location.getRandom(1,4).getY()<4);
        try {
            assertTrue(Location.getRandom(3, 4, 8, 12).getX() >= 3);
            assertTrue(Location.getRandom(6, 5, 4, 16).getY() < 16);
        }
        catch (InvalidRectangleException e){
            System.out.println(e.toString());
        }
    }
}