import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTest {


    @Test
    void siblingsNotMarried() {
    	Person wife = new Person();
    	wife.setChild("@F2@");
    	wife.setId("@I6");
    	Person husband = new Person();
    	husband.setChild("@F3@");
    	husband.setId("@I8");
    	Family f = new Family();
    	f.setHusbandID(husband.getId());
    	f.setWifeID(wife.getId());
    	assertTrue(f.siblingsNotMarried(wife, husband));
    }
    
    @Test
    void siblingsMarried() {
    	Person wife = new Person();
    	wife.setChild("@F2@");
    	wife.setId("@I6");
    	Person husband = new Person();
    	husband.setChild("@F2@");
    	husband.setId("@I8");
    	Family f = new Family();
    	f.setHusbandID(husband.getId());
    	f.setWifeID(wife.getId());
    	assertFalse(f.siblingsNotMarried(wife, husband));
    }
    
    
}