import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

class Test {

	DateHelper dateHelper = new DateHelper();
	@org.junit.jupiter.api.Test
	void test1()  {
		Family f = new Family();
		f.setMarried("01 JUN 2010");
		f.setDivorced("03 AUG 2016");
		assertTrue(dateHelper.marriageDateBeforeDivorceDate(f.getMarried(), f.getDivorced()));
	}
	
	@org.junit.jupiter.api.Test
	void test2() {
		Family f = new Family();
		f.setMarried("01 JUN 2010");
		f.setDivorced("02 JUN 2010");
		assertTrue(dateHelper.marriageDateBeforeDivorceDate(f.getMarried(), f.getDivorced()));
	}
	
	@org.junit.jupiter.api.Test
	void test3()  {
		Family f = new Family();
		f.setMarried("20 OCT 2010");
		f.setDivorced("02 JUN 2009");
		assertFalse(dateHelper.marriageDateBeforeDivorceDate(f.getMarried(), f.getDivorced()));
	}
	
	@org.junit.jupiter.api.Test
	void test4() {
		Family f = new Family();
		f.setMarried("20 OCT 2017");
		f.setDivorced("02 JUN 2009");
		assertFalse(dateHelper.marriageDateBeforeDivorceDate(f.getMarried(), f.getDivorced()));
	}
	
	@org.junit.jupiter.api.Test
	void test5()  {
		Family f = new Family();
		f.setMarried("02 FEB 2017");
		f.setDivorced("12 FEB 2017");
		assertTrue(dateHelper.marriageDateBeforeDivorceDate(f.getMarried(), f.getDivorced()));
	}

}
