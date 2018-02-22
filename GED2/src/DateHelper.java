import java.time.LocalDate;
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
		int birthYear = birthday.getYear();
		if (alive) {
			int currYear = LocalDate.now().getYear();
			return currYear - birthYear;
		} else {
			int deathYear = death.getYear();
			return deathYear - birthYear;
		}

	}

	public boolean dateBeforeCurrentDate(LocalDate date) {
		LocalDate currentDate = LocalDate.now();
		return date.isBefore(currentDate);
	}

	public boolean marriageDateBeforeDivorceDate(LocalDate married, LocalDate divorced) {

		return married.isBefore(divorced);
	}
}
