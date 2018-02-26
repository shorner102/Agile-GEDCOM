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
}