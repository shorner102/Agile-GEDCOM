import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


public class GEDInAndOutTest {

	DateHelper dateHelper = new DateHelper();
	
    public boolean dateBeforeCurrentDate(LocalDate date) {
		LocalDate currentDate = LocalDate.now();
		return date.isBefore(currentDate);
    }
    
	@Test
	void dateBeforeCurrentDateTest1() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("14 APR 1992", formatter);
		assertTrue(dateHelper.dateBeforeCurrentDate(date));
	}
	@Test
	void dateBeforeCurrentDateTest2() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("14 APR 2025", formatter);
		assertFalse(dateHelper.dateBeforeCurrentDate(date));
	}
	@Test
	void dateBeforeCurrentDateTest3() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("16 FEB 2018", formatter);
		assertFalse(dateHelper.dateBeforeCurrentDate(date));
	}
	@Test
	void dateBeforeCurrentDateTest4() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("10 FEB 2018", formatter);
		assertTrue(dateHelper.dateBeforeCurrentDate(date));
	}
	
	@Test
	void dateBeforeCurrentDateTest5() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("31 DEC 2018", formatter);
	
		assertFalse(dateHelper.dateBeforeCurrentDate(date));
	}
	
	
	


}
