import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class DateHelper {

	public DateHelper() {

	}

	public LocalDate parseDate(String input) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy")
				.toFormatter();
		try {
			LocalDate date = LocalDate.parse(input, formatter);
			return date;
		} catch (DateTimeParseException exc) {
			// System.out.printf("%s is not parsable!%n", input);
			return null;
		}
	}

	public int calculateAge(boolean alive, LocalDate birthday, LocalDate death) {
		if (alive && birthday != null) {
			return Period.between(birthday, LocalDate.now()).getYears();
		} else if (!alive && birthday != null && death != null) {
			return Period.between(birthday, death).getYears();
		} else {
			return 0;
		}
	}

	public boolean dateBeforeCurrentDate(LocalDate date) {
		LocalDate currentDate = LocalDate.now();
		return date.isBefore(currentDate);
	}

	public boolean marriageDateBeforeDivorceDate(LocalDate marriageDate, LocalDate divorceDate) {
		return marriageDate.isBefore(divorceDate);
	}


	public boolean birthDateBeforeMarriageDate(LocalDate birthday, LocalDate marriageDate) {
		if(birthday == null || marriageDate == null) { return true; }
		return birthday.isBefore(marriageDate);
	}

	public boolean birthDateBeforeDeathDate(LocalDate birthday, LocalDate deathDate) {
		return birthday.isBefore(deathDate);
	}

	public boolean marriageDateBeforeDeathDate(LocalDate marriageDate, LocalDate deathDate) {
		if (deathDate == null) {
			return true;
		} else {
			return marriageDate.isBefore(deathDate);
		}
	}
	
	public boolean divorceDateBeforeDeathDate(LocalDate divorceDate, LocalDate deathDate) {
		if (deathDate == null) {
			return true;
		} else {
			return divorceDate.isBefore(deathDate);
		}
	}
	
	public boolean marriageAfter14(LocalDate marriageDate, LocalDate birthday) {
		LocalDate dateof14thBday = birthday.plusYears(14);
		return marriageDate.isAfter(dateof14thBday);
		
	}
	
	public boolean monthDayInTheNext30Days(LocalDate date) {
		LocalDate thirtyDaysFromToday = LocalDate.now().plusDays(30);
		LocalDate dateToThisYear = date.withYear(LocalDate.now().getYear());
		return dateToThisYear.isBefore(thirtyDaysFromToday) && dateToThisYear.isAfter(LocalDate.now());

	}

	public boolean birthBeforeDivorce(LocalDate birth, LocalDate divorce) {
		if(divorce == null) {
			return true;
		}
		LocalDate divorceBuffer = divorce.plusMonths(9);
		return birth.isBefore(divorceBuffer);
	}

	public boolean birthBeforeParentsDeath(LocalDate birth, LocalDate fatherDeath, LocalDate motherDeath) {
		if(fatherDeath == null || motherDeath == null) {
			return true;
		}
		LocalDate fatherDeathExtended = fatherDeath.plusMonths(9);
		if(birth.isBefore(fatherDeathExtended) && birth.isBefore(motherDeath)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int ageAtDate(LocalDate birthday, LocalDate date) {
		if(birthday == null  || date == null)
			return 0;
		return Period.between(birthday, date).getYears();
		
	}

	public boolean dateIsInPast30Days(LocalDate date) {
		LocalDate todayMinus30 = LocalDate.now().minusDays(30);
		if (date == null)
			return false;
		if (date.isBefore(LocalDate.now()) && date.isAfter(todayMinus30)) {
			return true;
		} else {
			return false;
		}
	}

}
