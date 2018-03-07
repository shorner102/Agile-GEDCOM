import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {

    DateHelper dateHelper = new DateHelper();

    @Test
    void parseDateSuccess() {
       LocalDate date = dateHelper.parseDate("14 APR 1992");
       assertNotNull(date);
    }

    @Test
    void parseDateFailure() {
        LocalDate date = dateHelper.parseDate("14 August 1992");
        assertNull(date);
    }

    @Test
    void calculateAgeAliveSuccess() {
        LocalDate birth = dateHelper.parseDate("16 AUG 1996");
        assertEquals(21, dateHelper.calculateAge(true, birth, null));
    }

    @Test
    void calculateAgeAliveFailure() {
        assertEquals(0, dateHelper.calculateAge(true, null, null));
    }

    @Test
    void calculateAgeDeadSuccess() {
        LocalDate birth = dateHelper.parseDate("16 AUG 1970");
        LocalDate death = dateHelper.parseDate("5 NOV 2010");
        assertEquals(40, dateHelper.calculateAge(false, birth, death));
    }

    @Test
    void calculateAgeDeadFailure() {
        LocalDate birth = dateHelper.parseDate("16 AUG 1970");
        assertEquals(0, dateHelper.calculateAge(false, birth, null));
    }

    @Test
    void dateBeforeCurrentDateTrue() {
        LocalDate date = dateHelper.parseDate("5 JUL 1988");
        assertTrue(dateHelper.dateBeforeCurrentDate(date));
    }

    @Test
    void dateBeforeCurrentDateFalse() {
        LocalDate date = dateHelper.parseDate("5 JUL 2022");
        assertFalse(dateHelper.dateBeforeCurrentDate(date));
    }

    @Test
    void marriageDateBeforeDivorceDateTrue() {
        LocalDate married = dateHelper.parseDate("5 JUL 2001");
        LocalDate divorced = dateHelper.parseDate("5 JUL 2005");
        assertTrue(dateHelper.marriageDateBeforeDivorceDate(married, divorced));
    }

    @Test
    void marriageDateBeforeDivorceDateFalse() {
        LocalDate married = dateHelper.parseDate("5 JUL 2010");
        LocalDate divorced = dateHelper.parseDate("5 JUL 2005");
        assertFalse(dateHelper.marriageDateBeforeDivorceDate(married, divorced));
    }

    @Test
    void birthDateBeforeMarriageDateTrue() {
        LocalDate birth = dateHelper.parseDate("20 SEP 1965");
        LocalDate married = dateHelper.parseDate("5 JUL 2010");
        assertTrue(dateHelper.birthDateBeforeMarriageDate(birth, married));
    }

    @Test
    void birthDateBeforeMarriageDateFalse() {
        LocalDate birth = dateHelper.parseDate("20 SEP 1965");
        LocalDate married = dateHelper.parseDate("5 JUL 1950");
        assertFalse(dateHelper.birthDateBeforeMarriageDate(birth, married));
    }

    @Test
    void birthDateBeforeDeathDateTrue() {
        LocalDate birth = dateHelper.parseDate("20 SEP 1965");
        LocalDate death = dateHelper.parseDate("9 AUG 1995");
        assertTrue(dateHelper.birthDateBeforeDeathDate(birth, death));
    }

    @Test
    void birthDateBeforeDeathDateFalse() {
        LocalDate birth = dateHelper.parseDate("20 SEP 1965");
        LocalDate death = dateHelper.parseDate("9 AUG 1900");
        assertFalse(dateHelper.birthDateBeforeDeathDate(birth, death));
    }

    @Test
    void marriageDateBeforeDeathDateAlive() {
        LocalDate married = dateHelper.parseDate("5 JUL 1950");
        assertTrue(dateHelper.marriageDateBeforeDeathDate(married, null));
    }

    @Test
    void marriageDateBeforeDeathDateDead() {
        LocalDate married = dateHelper.parseDate("5 JUL 1950");
        LocalDate death = dateHelper.parseDate("10 OCT 1999");
        assertTrue(dateHelper.marriageDateBeforeDeathDate(married, death));
    }
    
    @Test
    void marriageAfter14Success() {
        LocalDate married = dateHelper.parseDate("5 JUL 1950");
        LocalDate birthday = dateHelper.parseDate("7 AUG 1920");
        assertTrue(dateHelper.marriageAfter14(married, birthday));
    }

    @Test
    void marriageAfter14Failure() {
        LocalDate married = dateHelper.parseDate("5 JUL 1950");
        LocalDate birthday = dateHelper.parseDate("7 AUG 1945");
        assertFalse(dateHelper.marriageAfter14(married, birthday));
    }
    
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

    @Test
    void individualAgeOver150() {
        Person ind = new Person("A");
        ind.setBirthday("10 AUG 1800", 1);
        assertEquals(0, ind.getAge());
    }
    
    @Test 
    void monthDayInTheNext30Days(){
        LocalDate date = dateHelper.parseDate("15 MAR 1950");
        assertTrue(dateHelper.monthDayInTheNext30Days(date));
    }
    
    @Test 
    void monthDayNotInTheNext30Days1(){
        LocalDate date = dateHelper.parseDate("15 SEP 1950");
        assertFalse(dateHelper.monthDayInTheNext30Days(date));
    }
    
    @Test 
    void monthDayNotInTheNext30Days2(){
        LocalDate date = dateHelper.parseDate("6 MAR 1950");
        assertFalse(dateHelper.monthDayInTheNext30Days(date));
    }
    
    
    
}