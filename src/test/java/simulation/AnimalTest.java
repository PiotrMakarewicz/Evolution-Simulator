package simulation;

import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void getAncestorsNumber() {
        Animal a0 = new Animal (10,1,Location.getRandom(1,1));
        Animal a1 = new Animal(10,1,Location.getRandom(1,1));
        Animal a2 = new Animal(10,1,Location.getRandom(1,1));
        int ancestors = a1.getAncestorsNumber();
        assertEquals(ancestors,0);
        Animal a3 = new Animal(a1,a2,2,Location.getRandom(1,1));
        ancestors = a1.getAncestorsNumber();
        assertEquals(ancestors,1);
        Animal a4 = new Animal(a1,a3,3,Location.getRandom(1,1));
        ancestors = a1.getAncestorsNumber();
        assertEquals(ancestors,2);
        Animal a5 = new Animal(a3,a4,4,Location.getRandom(1,1));
        ancestors = a1.getAncestorsNumber();
        assertEquals(ancestors,3);
        Animal a6 = new Animal(a2,a4,5,Location.getRandom(1,1));
        ancestors = a1.getAncestorsNumber();
        assertEquals(ancestors,4);
        Animal a7 = new Animal(a2,a0,5,Location.getRandom(1,1));
        ancestors = a1.getAncestorsNumber();
        assertEquals(ancestors,4);
    }
}