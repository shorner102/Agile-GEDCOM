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
}
