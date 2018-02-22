import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class FamilyTest {

    @Test
    public void setMarriedHusbandWifeBornBefore() {
        Family f = new Family();
        f.married = f.parseDate("5 MAR 1988");
        LocalDate husbBirth = f.parseDate("7 MAY 1967");
        LocalDate wifeBirth = f.parseDate("10 AUG 1972");
        assertTrue(f.birthDateBeforeMarriageDate(husbBirth));
        assertTrue(f.birthDateBeforeMarriageDate(wifeBirth));
    }

    @Test
    public void setMarriedHusbandBornAfter() {
        Family f = new Family();
        f.married = f.parseDate("5 MAR 1988");
        LocalDate husbBirth = f.parseDate("7 MAY 1991");
        LocalDate wifeBirth = f.parseDate("10 AUG 1972");
        assertFalse(f.birthDateBeforeMarriageDate(husbBirth));
        assertTrue(f.birthDateBeforeMarriageDate(wifeBirth));
    }

    @Test
    public void setMarriedWifeBornAfter() {
        Family f = new Family();
        f.married = f.parseDate("5 MAR 1988");
        LocalDate husbBirth = f.parseDate("7 MAY 1967");
        LocalDate wifeBirth = f.parseDate("10 AUG 1988");
        assertTrue(f.birthDateBeforeMarriageDate(husbBirth));
        assertFalse(f.birthDateBeforeMarriageDate(wifeBirth));
    }

    @Test(expected = BadDateException.class)
    public void setMarriedBothBornAfter() throws BadDateException {
        Family f = new Family();
        LocalDate husbBirth = f.parseDate("7 MAY 1967");
        LocalDate wifeBirth = f.parseDate("10 AUG 1988");
        f.setMarried("1 JAN 1950", husbBirth, wifeBirth);
    }

    @Test(expected = BadDateException.class)
    public void setMarriedOneBornAfter() throws BadDateException {
        Family f = new Family();
        LocalDate husbBirth = f.parseDate("7 MAY 1967");
        LocalDate wifeBirth = f.parseDate("10 AUG 1949");
        f.setMarried("1 JAN 1950", husbBirth, wifeBirth);
    }
}