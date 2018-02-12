import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


public class GEDInAndOutTest {

	@Test
	void dateBeforeCurrentDateTest1() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("14 APR 1992", formatter);
		assertTrue(fam.dateBeforeCurrentDate(date));
	}
	@Test
	void dateBeforeCurrentDateTest2() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("14 APR 2025", formatter);
		assertFalse(fam.dateBeforeCurrentDate(date));
	}
	@Test
	void dateBeforeCurrentDateTest3() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("16 FEB 2018", formatter);
		assertFalse(fam.dateBeforeCurrentDate(date));
	}
	@Test
	void dateBeforeCurrentDateTest4() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("10 FEB 2018", formatter);
		assertTrue(fam.dateBeforeCurrentDate(date));
	}
	@Test
	void dateBeforeCurrentDateTest5() {
		Family fam = new Family();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("31 DEC 2018", formatter);
	
		assertFalse(fam.dateBeforeCurrentDate(date));
	}
	
	@Test
	void dateBeforeCurrentDateTest6() throws BadDateException {
		Family fam = new Family();
		assertThrows(BadDateException.class, () ->{
			fam.setMarried("04 JUN 2020");
		});
	}

	@Test
	void dateBeforeCurrentDateTest7() throws BadDateException {
		Family fam = new Family();
		assertThrows(BadDateException.class, () ->{
			fam.setDivorced("04 SEP 2052");
		});
	}
	
	@Test
	void dateBeforeCurrentDateTest8() throws BadDateException {
		Family fam = new Family();
		assertThrows(BadDateException.class, () ->{
			fam.setDivorced("25 OCT 2019");
		});
	}
	
	@Test
	void dateBeforeCurrentDateTest9() throws BadDateException {
		Family fam = new Family();
		assertThrows(BadDateException.class, () ->{
			fam.setDivorced("08 MAY 2100");
		});
	}
	
	@Test
	void createDeath() throws BadDateException {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		LocalDate date = LocalDate.parse("50 OCT 2015", formatter);
		
		Person person = new Person();
		person.setAlive(true);
		person.setBirthday("50 OCT 2015");
		assertEquals(person.getBirthday(), date);
	}

}
