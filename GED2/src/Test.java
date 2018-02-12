import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

class Test {

	@org.junit.jupiter.api.Test
	void test1() throws BadDateException {
		Family f = new Family();
		f.setMarried("01 JUN 2010");
		f.setDivorced("03 AUG 2016");
		assertTrue(f.marriageDateBeforeDivorceDate());
	}
	
	@org.junit.jupiter.api.Test
	void test2() throws BadDateException {
		Family f = new Family();
		f.setMarried("01 JUN 2010");
		f.setDivorced("02 JUN 2010");
		assertTrue(f.marriageDateBeforeDivorceDate());
	}
	
	@org.junit.jupiter.api.Test
	void test3() throws BadDateException {
		Family f = new Family();
		f.setMarried("20 OCT 2010");
		f.setDivorced("02 JUN 2009");
		assertFalse(f.marriageDateBeforeDivorceDate());
	}
	
	@org.junit.jupiter.api.Test
	void test4() throws BadDateException {
		Family f = new Family();
		f.setMarried("20 OCT 2017");
		f.setDivorced("02 JUN 2009");
		assertFalse(f.marriageDateBeforeDivorceDate());
	}
	
	@org.junit.jupiter.api.Test
	void test5() throws BadDateException {
		Family f = new Family();
		f.setMarried("02 FEB 2017");
		f.setDivorced("12 FEB 2017");
		assertTrue(f.marriageDateBeforeDivorceDate());
	}

}
