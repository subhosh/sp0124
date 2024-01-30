package com.sp.trms.service;

import com.sp.trms.domain.DayType;
import com.sp.trms.domain.RentalDay;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Rental Day service to perform day related functions calculated holidays / weekends / weekdays.
 */
@Service
public class RentalDayService {
    private static final Set<DayOfWeek> businessDays = Set.of(DayOfWeek.MONDAY,
                                                              DayOfWeek.TUESDAY,
                                                              DayOfWeek.WEDNESDAY,
                                                              DayOfWeek.THURSDAY,
                                                              DayOfWeek.FRIDAY);
    private static final Map<Integer, List<LocalDate>> holidays = new HashMap<>();

    /**
     * Method to populate rental days for a given range of dates by denoting whether its a holiday/weekday/weekend
     * @param startDate - Start Date.
     * @param endDate - End Date.
     * @return Returns list of rental holiday objects.
     */
    public List<RentalDay> getRentalDays(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .map(date -> {
                    RentalDay rentalDay = new RentalDay();
                    rentalDay.setDate(date);
                    if (isHoliday(date)) {
                        rentalDay.setDayType(DayType.HOLIDAY);
                    } else if (businessDays.contains(date.getDayOfWeek())) {
                        rentalDay.setDayType(DayType.WEEKDAY);
                    } else {
                        rentalDay.setDayType(DayType.WEEKEND);
                    }
                    return rentalDay;
                })
                .collect(Collectors.toList());
    }

    /**
     * Helper method to determine if a given date is a holiday.
     * It populates in-memory database to log holidays for a given year if its not present.
     * @param date - Provided date.
     * @return - Returns True if it's a holiday else False.
     */
    protected boolean isHoliday(LocalDate date) {
        if (holidays.get(date.getYear()) == null) {
            populateHolidays(date.getYear());
        }
        return holidays.get(date.getYear()).contains(date);
    }

    /**
     * Populate holidays for a given year
     * @param year to populate holidays.
     */
    protected void populateHolidays(int year) {
        holidays.put(year, new ArrayList<>());

        //Populate Independence Day
        holidays.get(year).add(getIndependenceDay(year));

        //Populate Labor Day
        holidays.get(year).add(getLaborDay(year));
    }

    /**
     * Helper method to determine independence day for a given year
     * @param year - Year
     * @return - Return date for independence day.
     */
    protected LocalDate getIndependenceDay(int year) {
        LocalDate july4th = LocalDate.of(year, 7, 4);
        if (july4th.getDayOfWeek() == DayOfWeek.SATURDAY) {
            july4th = july4th.minusDays(1);
        } else if (july4th.getDayOfWeek() == DayOfWeek.SUNDAY) {
            july4th = july4th.plusDays(1);
        }
        return july4th;
    }

    /**
     * Helper method to determine labor day for a given year
     * @param year - Year
     * @return - Return date for labor day.
     */
    protected LocalDate getLaborDay(int year) {
        LocalDate laborDay = LocalDate.of(year, 9, 1);

        return laborDay.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }
}
