package datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTimeTest {

	// don't use YYYY which is "week of years"
	final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/*
	 * Manipulate LocalDateTime.
	 */
	@Test
	void addDays() {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime tomorrow = today.plusDays(1);
		/*Date d =*/ Date.from(tomorrow.atZone(ZoneId.systemDefault()).toInstant()); // convert to 'Date'
	}

	@Test
	void addHoursAndMinutes() {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime later = today.minusHours(5).minusMinutes(30);
	}

	@Test
	void useTemporalAdjusters() {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 22); // 22.02.2018
		// first day of february - 01.02.2018
		LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
		assertEquals(1, firstDayOfMonth.getDayOfMonth());
		assertTrue(Month.FEBRUARY == firstDayOfMonth.getMonth());
		assertEquals(2018, firstDayOfMonth.getYear());
		// last day of february - 28.02.2018
		LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
		assertEquals(28, lastDayOfMonth.getDayOfMonth());
		assertTrue(Month.FEBRUARY == lastDayOfMonth.getMonth());
		assertEquals(2018, lastDayOfMonth.getYear());
	}

	/*
	 * Parse String to LocalDate, LocalTime and LocalDateTime.
	 */

	@Test
	void parseDate() {
		String dateString = "19/02/2018";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dateString, formatter);
		assertEquals(19, date.getDayOfMonth());
		assertTrue(Month.FEBRUARY == date.getMonth());
		assertEquals(2018, date.getYear());
	}

	@Test
	void parseTime() {
		String timeString = "09:40:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse(timeString, formatter);
		assertEquals(9, time.getHour());
		assertEquals(40, time.getMinute());
		assertEquals(59, time.getSecond());
	}

	@Test
	void parseDateTime() {
		String dateString = "19/02/2018 09:40:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
		assertEquals(19, dateTime.getDayOfMonth());
		assertTrue(Month.FEBRUARY == dateTime.getMonth());
		assertEquals(2018, dateTime.getYear());
		assertEquals(9, dateTime.getHour());
		assertEquals(40, dateTime.getMinute());
		assertEquals(59, dateTime.getSecond());
	}

	@Test
	void isAfter() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		assertTrue(tomorrow.isAfter(today));
	}

	@Test
	void isBefore() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		assertTrue(today.isBefore(tomorrow));
	}

	@Test
	void isEqual() {
		LocalDate today = LocalDate.now();
		assertTrue(today.isEqual(today));
	}

	@Test
	void firstDayOfMonth() {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 22);
		int dayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth()).getDayOfMonth();
		assertEquals(1, dayOfMonth);
	}

	@Test
	void lastDayOfMonth() {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 22);
		int dayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		assertEquals(28, dayOfMonth);
	}

	/**
	 * Calculations with time periods. 
	 */

	@DisplayName("1 // Current year")
	@Test
	void currentYear() {
		LocalDate startDate = LocalDate.now().withDayOfYear(1);
		LocalDate endDate = LocalDate.now();
		assertEquals("01/01/2018", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("2 // Last year")
	@Test
	void lastYear() {
		LocalDate startDate = LocalDate.now().minusYears(1).withDayOfYear(1);
		LocalDate endDate = LocalDate.now().minusYears(1).withDayOfYear(365);
		assertEquals("01/01/2017", startDate.format(FORMAT));
		assertEquals("31/12/2017", endDate.format(FORMAT));
	}

	@DisplayName("3 // Last 12 months")
	@Test
	void last12Months() {
		LocalDate startDate = LocalDate.now().minusMonths(12);
		LocalDate endDate = LocalDate.now();
		assertEquals("15/03/2017", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("4 // Last 3 years")
	@Test
	void last3Years() {
		LocalDate startDate = LocalDate.now().minusYears(3).withDayOfYear(1);
		LocalDate endDate = LocalDate.now().minusYears(1).withDayOfYear(365);
		assertEquals("01/01/2015", startDate.format(FORMAT));
		assertEquals("31/12/2017", endDate.format(FORMAT));
	}

	@DisplayName("5 // Current month")
	@Test
	void currentMonth() {
		LocalDate startDate = LocalDate.now().withDayOfMonth(1);
		LocalDate endDate = LocalDate.now();
		assertEquals("01/03/2018", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("6 // Last month")
	@Test
	void lastMonth() {
		LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
		LocalDate endDate = LocalDate.now().minusMonths(1).withDayOfMonth(28);
		assertEquals("01/02/2018", startDate.format(FORMAT));
		assertEquals("28/02/2018", endDate.format(FORMAT));
	}

	@DisplayName("7 // Last 15 days")
	@Test
	void last15Days() {
		LocalDate startDate = LocalDate.now().minusDays(15);
		LocalDate endDate = LocalDate.now();
		assertEquals("28/02/2018", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("8 // Current + last year")
	@Test
	void currentAndLastYear() {
		LocalDate startDate = LocalDate.now().minusYears(1).withDayOfYear(1);
		LocalDate endDate = LocalDate.now();
		assertEquals("01/01/2017", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("9 // Current + last 11 months")
	@Test
	void currentAndLast11Months() {
		LocalDate startDate = LocalDate.now().minusMonths(11);
		LocalDate endDate = LocalDate.now();
		assertEquals("15/04/2017", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("10 // Current + last 3 years")
	@Test
	void currentAndLast3Years() {
		LocalDate startDate = LocalDate.now().minusYears(3).withDayOfYear(1);
		LocalDate endDate = LocalDate.now();
		assertEquals("01/01/2015", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("11 // Current + last month")
	@Test
	void currentAndLast() {
		LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
		LocalDate endDate = LocalDate.now();
		assertEquals("01/02/2018", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("12 // Current + last 15 days")
	@Test
	void currentAndLAst15Days() {
		LocalDate startDate = LocalDate.now().minusDays(15);
		LocalDate endDate = LocalDate.now();
		assertEquals("28/02/2018", startDate.format(FORMAT));
		assertEquals("15/03/2018", endDate.format(FORMAT));
	}
}
