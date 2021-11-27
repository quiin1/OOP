import java.util.*;

public class Test {
    public static List<String> getAllFunctions2(String fileContent) {
        List<String> list = new ArrayList<>();
        HashMap<String, Integer> dataTypes = new HashMap<>();
        StringBuilder func = new StringBuilder();
        int staticIndex = 0;
        int hasNextStatic = fileContent.indexOf("static", staticIndex);
        while (hasNextStatic != -1) {
            staticIndex = fileContent.indexOf(" ", hasNextStatic) + 1;
            if (isFunction(fileContent, staticIndex)) {
                String nextFunc =
                        fileContent.substring(
                                fileContent.lastIndexOf(" ", fileContent.indexOf("(", staticIndex)) + 1,
                                fileContent.indexOf(")", staticIndex) + 1);
                // remove break line, multiple space -> 1 space,...
                nextFunc = nextFunc.replaceAll("\n", " ");
                nextFunc = nextFunc.replaceAll("( )+", " ");
                nextFunc = nextFunc.replaceAll("\\( ", "(");
                nextFunc = nextFunc.replaceAll(", ", ",");

                // delete variable in () if need
                if (nextFunc.indexOf("(") + 1 != nextFunc.indexOf(")")) {
                    // get dataTypes
                    int spaceIndex1 = nextFunc.indexOf("(");
                    int spaceIndex2 = nextFunc.indexOf(" ", spaceIndex1 + 1);
                    if (spaceIndex1 != -1) {
                        dataTypes.put(nextFunc.substring(spaceIndex1 + 1, spaceIndex2), 0);
                    }
                    spaceIndex1 = nextFunc.indexOf(",");
                    while (spaceIndex1 != -1) {
                        spaceIndex2 = nextFunc.indexOf(" ", spaceIndex1 + 1);
                        dataTypes.put(nextFunc.substring(spaceIndex1 + 1, spaceIndex2), 0);
                        spaceIndex1 = nextFunc.indexOf(",", spaceIndex1 + 1);
                    }

                    int comma = nextFunc.indexOf(",");
                    String nextVar = "";
                    while (comma != -1) {
                        nextVar = nextFunc.substring(nextFunc.indexOf(" "), comma);
                        nextFunc = nextFunc.replace(nextVar, "");
                        comma = nextFunc.indexOf(",", comma + 1);
                    }
                    // delete the last variable if it has
                    if (nextFunc.contains(" ")) {
                        nextVar = nextFunc.substring(nextFunc.indexOf(" "), nextFunc.indexOf(")"));
                        nextFunc = nextFunc.replace(nextVar, "");
                    }
                }
                func.append(nextFunc).append("\n");
            }
            hasNextStatic = fileContent.indexOf("static", staticIndex);
        }
        // delete last ", "
        String result = func.substring(0, func.length() - 1);

        // add import
        // Set<String> dataTypeSet = dataTypes.keySet();
        // dataTypeSet.removeIf(dataType -> 'a' <= dataType.charAt(0) && dataType.charAt(0) <= 'z');
        // :( sort by set -> no use
        // dataTypeSet.stream().sorted(Comparator.comparing(String::length).reversed());
        List<String> listDataTypes = new ArrayList<>(dataTypes.keySet());
        listDataTypes.removeIf(dataType -> 'a' <= dataType.charAt(0) && dataType.charAt(0) <= 'z');
        Collections.sort(listDataTypes, Comparator.comparing(String::length));

        for (String dataType : listDataTypes) {
            int i = fileContent.indexOf("." + dataType + ";");
            if (i == -1) continue;
            int imp1 = fileContent.lastIndexOf(" ", i) + 1;
            int imp2 = fileContent.indexOf(";", i);
            String importDataType = fileContent.substring(imp1, imp2);
            result = result.replaceAll(dataType + ",", importDataType + ",");
            result = result.replaceAll(dataType + "\\)", importDataType + ")");
        }

        Scanner scanner = new Scanner(result);
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        return list;
    }

    private static boolean isFunction(String fileContent, int staticIndex) {
        int nextStaticIndex = fileContent.indexOf("static", staticIndex);
        int openBracketIndex = fileContent.indexOf("(", staticIndex);
        int closeBracketIndex = fileContent.indexOf(")", staticIndex);
        int openFunctionBracketIndex = fileContent.indexOf("{", staticIndex);
        int finalIndex = fileContent.indexOf("final", staticIndex);
        if (staticIndex <= finalIndex && (finalIndex <= nextStaticIndex || nextStaticIndex == -1)) {
            return false;
        }
        return openBracketIndex < closeBracketIndex
                && closeBracketIndex < openFunctionBracketIndex
                && (openFunctionBracketIndex < nextStaticIndex || nextStaticIndex == -1);
    }

    public static void main(String[] args) {
        String test_RandomDateUtils = """
                package com.github.rkumsher.date;

                import static com.github.rkumsher.date.DateUtils.LEAP_DAY;
                import static com.github.rkumsher.number.RandomNumberUtils.randomInt;
                import static com.github.rkumsher.number.RandomNumberUtils.randomLong;
                import static com.github.rkumsher.number.RandomNumberUtils.randomNegativeInt;
                import static com.github.rkumsher.number.RandomNumberUtils.randomNegativeLong;
                import static com.github.rkumsher.number.RandomNumberUtils.randomPositiveInt;
                import static com.github.rkumsher.number.RandomNumberUtils.randomPositiveLong;
                import static com.google.common.base.Preconditions.*;
                import static java.time.Month.DECEMBER;
                import static java.time.Month.JANUARY;
                import static java.time.temporal.ChronoField.NANO_OF_DAY;
                import static java.time.temporal.ChronoUnit.DAYS;
                import static java.time.temporal.ChronoUnit.MILLIS;
                import static java.time.temporal.ChronoUnit.MONTHS;

                import java.time.Clock;
                import java.time.DayOfWeek;
                import java.time.Duration;
                import java.time.Instant;
                import java.time.LocalDate;
                import java.time.LocalDateTime;
                import java.time.LocalTime;
                import java.time.Month;
                import java.time.MonthDay;
                import java.time.OffsetDateTime;
                import java.time.Period;
                import java.time.Year;
                import java.time.YearMonth;
                import java.time.ZoneId;
                import java.time.ZoneOffset;
                import java.time.ZonedDateTime;
                import java.time.temporal.ChronoField;
                import java.time.temporal.TemporalField;
                import java.util.Date;

                import org.apache.commons.lang3.RandomUtils;

                import com.github.rkumsher.collection.IterableUtils;
                import com.github.rkumsher.enums.RandomEnumUtils;

                /**
                 * Utility library to return random dates, e.g., {@link Instant}s, {@link ZonedDateTime}s, {@link
                 * LocalDate}s, {@link Date}s, etc.
                 *
                 * <p>Note: All returned dates will be between 1970 and 9999.
                 */
                public final class RandomDateUtils {

                  private static final ZoneId UTC = ZoneId.of("UTC").normalized();
                  private static final ZoneOffset UTC_OFFSET = ZoneOffset.UTC;
                  private static final int LEAP_YEAR = 2004;
                  private static final int MAX_ZONE_OFFSET_SECONDS = 64800;
                  private static final int MIN_YEAR = 1970;
                  private static final int MAX_YEAR = 9999;
                  /** 1970-01-01T00:00:00Z. */
                  static final Instant MIN_INSTANT = Instant.ofEpochMilli(0);
                  /** December 31st, 9999. */
                  static final Instant MAX_INSTANT =
                      Instant.ofEpochMilli(
                          LocalDate.of(MAX_YEAR, 12, 31).atStartOfDay(UTC).toInstant().toEpochMilli());

                  private RandomDateUtils() {}

                  /**
                   * Returns a random {@link ZonedDateTime} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link ZonedDateTime}
                   */
                  public static ZonedDateTime randomZonedDateTime() {
                    return ZonedDateTime.ofInstant(randomInstant(), UTC);
                  }

                  /**
                   * Returns a random {@link ZonedDateTime} within the specified range.
                   *
                   * @param startInclusive the earliest {@link ZonedDateTime} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link ZonedDateTime}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static ZonedDateTime randomZonedDateTime(
                      ZonedDateTime startInclusive, ZonedDateTime endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    Instant instant = randomInstant(startInclusive.toInstant(), endExclusive.toInstant());
                    return ZonedDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link ZonedDateTime} that is after the current system clock.
                   *
                   * @return the random {@link ZonedDateTime}
                   */
                  public static ZonedDateTime randomFutureZonedDateTime() {
                    return randomZonedDateTimeAfter(ZonedDateTime.now());
                  }

                  /**
                   * Returns a random {@link ZonedDateTime} that is after the given {@link ZonedDateTime}.
                   *
                   * @param after the value that returned {@link ZonedDateTime} must be after
                   * @return the random {@link ZonedDateTime}
                   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static ZonedDateTime randomZonedDateTimeAfter(ZonedDateTime after) {
                    checkArgument(after != null, "After must be non-null");
                    Instant instant = randomInstantAfter(after.toInstant());
                    return ZonedDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link ZonedDateTime} that is before the current system clock.
                   *
                   * @return the random {@link ZonedDateTime}
                   */
                  public static ZonedDateTime randomPastZonedDateTime() {
                    return randomZonedDateTimeBefore(ZonedDateTime.now());
                  }

                  /**
                   * Returns a random {@link ZonedDateTime} that is before the given {@link ZonedDateTime}.
                   *
                   * @param before the value that returned {@link ZonedDateTime} must be before
                   * @return the random {@link ZonedDateTime}
                   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static ZonedDateTime randomZonedDateTimeBefore(ZonedDateTime before) {
                    checkArgument(before != null, "Before must be non-null");
                    Instant instant = randomInstantBefore(before.toInstant());
                    return ZonedDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link OffsetDateTime} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link OffsetDateTime}
                   */
                  public static OffsetDateTime randomOffsetDateTime() {
                    return OffsetDateTime.ofInstant(randomInstant(), UTC);
                  }

                  /**
                   * Returns a random {@link OffsetDateTime} within the specified range.
                   *
                   * @param startInclusive the earliest {@link OffsetDateTime} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link OffsetDateTime}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static OffsetDateTime randomOffsetDateTime(
                      OffsetDateTime startInclusive, OffsetDateTime endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    Instant instant = randomInstant(startInclusive.toInstant(), endExclusive.toInstant());
                    return OffsetDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link OffsetDateTime} that is after the current system clock.
                   *
                   * @return the random {@link OffsetDateTime}
                   */
                  public static OffsetDateTime randomFutureOffsetDateTime() {
                    return randomOffsetDateTimeAfter(OffsetDateTime.now());
                  }

                  /**
                   * Returns a random {@link OffsetDateTime} that is after the given {@link OffsetDateTime}.
                   *
                   * @param after the value that returned {@link OffsetDateTime} must be after
                   * @return the random {@link OffsetDateTime}
                   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static OffsetDateTime randomOffsetDateTimeAfter(OffsetDateTime after) {
                    checkArgument(after != null, "After must be non-null");
                    Instant instant = randomInstantAfter(after.toInstant());
                    return OffsetDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link OffsetDateTime} that is before the current system clock.
                   *
                   * @return the random {@link OffsetDateTime}
                   */
                  public static OffsetDateTime randomPastOffsetDateTime() {
                    return randomOffsetDateTimeBefore(OffsetDateTime.now());
                  }

                  /**
                   * Returns a random {@link OffsetDateTime} that is before the given {@link OffsetDateTime}.
                   *
                   * @param before the value that returned {@link OffsetDateTime} must be before
                   * @return the random {@link OffsetDateTime}
                   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static OffsetDateTime randomOffsetDateTimeBefore(OffsetDateTime before) {
                    checkArgument(before != null, "Before must be non-null");
                    Instant instant = randomInstantBefore(before.toInstant());
                    return OffsetDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link LocalDateTime} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link LocalDateTime}
                   */
                  public static LocalDateTime randomLocalDateTime() {
                    return LocalDateTime.ofInstant(randomInstant(), UTC);
                  }

                  /**
                   * Returns a random {@link LocalDateTime} within the specified range.
                   *
                   * @param startInclusive the earliest {@link LocalDateTime} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link LocalDateTime}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static LocalDateTime randomLocalDateTime(
                      LocalDateTime startInclusive, LocalDateTime endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    Instant startInstant = startInclusive.toInstant(UTC_OFFSET);
                    Instant endInstant = endExclusive.toInstant(UTC_OFFSET);
                    Instant instant = randomInstant(startInstant, endInstant);
                    return LocalDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link LocalDateTime} that is after the current system clock.
                   *
                   * @return the random {@link LocalDateTime}
                   */
                  public static LocalDateTime randomFutureLocalDateTime() {
                    return randomLocalDateTimeAfter(LocalDateTime.now());
                  }

                  /**
                   * Returns a random {@link LocalDateTime} that is after the given {@link LocalDateTime}.
                   *
                   * @param after the value that returned {@link LocalDateTime} must be after
                   * @return the random {@link LocalDateTime}
                   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static LocalDateTime randomLocalDateTimeAfter(LocalDateTime after) {
                    checkArgument(after != null, "After must be non-null");
                    Instant instant = randomInstantAfter(after.toInstant(UTC_OFFSET));
                    return LocalDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link LocalDateTime} that is before the current system clock.
                   *
                   * @return the random {@link LocalDateTime}
                   */
                  public static LocalDateTime randomPastLocalDateTime() {
                    return randomLocalDateTimeBefore(LocalDateTime.now());
                  }

                  /**
                   * Returns a random {@link LocalDateTime} that is before the given {@link LocalDateTime}.
                   *
                   * @param before the value that returned {@link LocalDateTime} must be before
                   * @return the random {@link LocalDateTime}
                   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static LocalDateTime randomLocalDateTimeBefore(LocalDateTime before) {
                    checkArgument(before != null, "Before must be non-null");
                    Instant instant = randomInstantBefore(before.toInstant(UTC_OFFSET));
                    return LocalDateTime.ofInstant(instant, UTC);
                  }

                  /**
                   * Returns a random {@link LocalDate} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link LocalDate}
                   */
                  public static LocalDate randomLocalDate() {
                    return randomInstant().atZone(UTC).toLocalDate();
                  }

                  /**
                   * Returns a random {@link LocalDate} within the specified range.
                   *
                   * @param startInclusive the earliest {@link LocalDate} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link LocalDate}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static LocalDate randomLocalDate(LocalDate startInclusive, LocalDate endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    Instant startInstant = startInclusive.atStartOfDay().toInstant(UTC_OFFSET);
                    Instant endInstant = endExclusive.atStartOfDay().toInstant(UTC_OFFSET);
                    Instant instant = randomInstant(startInstant, endInstant);
                    return instant.atZone(UTC).toLocalDate();
                  }

                  /**
                   * Returns a random {@link LocalDate} that is after the current system clock.
                   *
                   * @return the random {@link LocalDate}
                   */
                  public static LocalDate randomFutureLocalDate() {
                    return randomLocalDateAfter(LocalDate.now());
                  }

                  /**
                   * Returns a random {@link LocalDate} that is after the given {@link LocalDate}.
                   *
                   * @param after the value that returned {@link LocalDate} must be after
                   * @return the random {@link LocalDate}
                   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static LocalDate randomLocalDateAfter(LocalDate after) {
                    checkArgument(after != null, "After must be non-null");
                    Instant instant = randomInstantAfter(after.atStartOfDay(UTC).plus(1, DAYS).toInstant());
                    return instant.atZone(UTC).toLocalDate();
                  }

                  /**
                   * Returns a random {@link LocalDate} that is before the current system clock.
                   *
                   * @return the random {@link LocalDate}
                   */
                  public static LocalDate randomPastLocalDate() {
                    return randomLocalDateBefore(LocalDate.now());
                  }

                  /**
                   * Returns a random {@link LocalDate} that is before the given {@link LocalDate}.
                   *
                   * @param before the value that returned {@link LocalDate} must be before
                   * @return the random {@link LocalDate}
                   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static LocalDate randomLocalDateBefore(LocalDate before) {
                    checkArgument(before != null, "Before must be non-null");
                    Instant instant = randomInstantBefore(before.atStartOfDay(UTC).toInstant());
                    return instant.atZone(UTC).toLocalDate();
                  }

                  /**
                   * Returns a random {@link Date} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link Date}
                   */
                  public static Date randomDate() {
                    return Date.from(randomInstant());
                  }

                  /**
                   * Returns a random {@link Date} within the specified range.
                   *
                   * @param startInclusive the earliest {@link Date} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link Date}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static Date randomDate(Date startInclusive, Date endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    Instant startInstant = startInclusive.toInstant();
                    Instant endInstant = endExclusive.toInstant();
                    Instant instant = randomInstant(startInstant, endInstant);
                    return Date.from(instant);
                  }

                  /**
                   * Returns a random {@link Date} that is after the current system clock.
                   *
                   * @return the random {@link Date}
                   */
                  public static Date randomFutureDate() {
                    return randomDateAfter(new Date());
                  }

                  /**
                   * Returns a random {@link Date} that is after the given {@link Date}.
                   *
                   * @param after the value that returned {@link Date} must be after
                   * @return the random {@link Date}
                   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static Date randomDateAfter(Date after) {
                    checkArgument(after != null, "After must be non-null");
                    Instant instant = randomInstantAfter(after.toInstant());
                    return Date.from(instant);
                  }

                  /**
                   * Returns a random {@link Date} that is before the current system clock.
                   *
                   * @return the random {@link Date}
                   */
                  public static Date randomPastDate() {
                    return randomDateBefore(new Date());
                  }

                  /**
                   * Returns a random {@link Date} that is before the given {@link Date}.
                   *
                   * @param before the value that returned {@link Date} must be before
                   * @return the random {@link Date}
                   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static Date randomDateBefore(Date before) {
                    checkArgument(before != null, "Before must be non-null");
                    Instant instant = randomInstantBefore(before.toInstant());
                    return Date.from(instant);
                  }

                  /**
                   * Returns a random {@link Instant} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link Instant}
                   */
                  public static Instant randomInstant() {
                    return randomInstant(MIN_INSTANT, MAX_INSTANT);
                  }

                  /**
                   * Returns a random {@link Instant} within the specified range.
                   *
                   * @param startInclusive the earliest {@link Instant} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link Instant}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static Instant randomInstant(Instant startInclusive, Instant endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    checkArgument(!startInclusive.isAfter(endExclusive), "End must be on or after start");
                    checkArgument(
                        startInclusive.equals(MIN_INSTANT) || startInclusive.isAfter(MIN_INSTANT),
                        "Start must be on or after %s",
                        MIN_INSTANT);
                    checkArgument(
                        endExclusive.equals(MAX_INSTANT) || endExclusive.isBefore(MAX_INSTANT),
                        "End must be on or before %s",
                        MAX_INSTANT);
                    return Instant.ofEpochMilli(
                        RandomUtils.nextLong(startInclusive.toEpochMilli(), endExclusive.toEpochMilli()));
                  }

                  /**
                   * Returns a random {@link Instant} that is after the current system clock.
                   *
                   * @return the random {@link Instant}
                   */
                  public static Instant randomFutureInstant() {
                    return randomInstantAfter(Instant.now());
                  }

                  /**
                   * Returns a random {@link Instant} that is after the given {@link Instant}.
                   *
                   * @param after the value that returned {@link Instant} must be after
                   * @return the random {@link Instant}
                   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static Instant randomInstantAfter(Instant after) {
                    checkArgument(after != null, "After must be non-null");
                    checkArgument(after.isBefore(MAX_INSTANT), "Cannot produce date after %s", MAX_INSTANT);
                    return randomInstant(after.plus(1, MILLIS), MAX_INSTANT);
                  }

                  /**
                   * Returns a random {@link Instant} that is before the current system clock.
                   *
                   * @return the random {@link Instant}
                   */
                  public static Instant randomPastInstant() {
                    return randomInstantBefore(Instant.now());
                  }

                  /**
                   * Returns a random {@link Instant} that is before the given {@link Instant}.
                   *
                   * @param before the value that returned {@link Instant} must be before
                   * @return the random {@link Instant}
                   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static Instant randomInstantBefore(Instant before) {
                    checkArgument(before != null, "Before must be non-null");
                    checkArgument(before.isAfter(MIN_INSTANT), "Cannot produce date before %s", MIN_INSTANT);
                    return randomInstant(MIN_INSTANT, before);
                  }

                  /**
                   * Returns a random {@link LocalTime}.
                   *
                   * @return the random {@link LocalTime}
                   */
                  public static LocalTime randomLocalTime() {
                    return LocalTime.ofNanoOfDay(random(NANO_OF_DAY));
                  }

                  /**
                   * Returns a random {@link LocalTime} within the specified range.
                   *
                   * @param startInclusive the earliest {@link LocalTime} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link LocalTime}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static LocalTime randomLocalTime(LocalTime startInclusive, LocalTime endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    long nanoOfDay = random(NANO_OF_DAY, startInclusive.toNanoOfDay(), endExclusive.toNanoOfDay());
                    return LocalTime.ofNanoOfDay(nanoOfDay);
                  }

                  /**
                   * Returns a random {@link LocalTime} that is after the given {@link LocalTime}.
                   *
                   * @param after the value that the returned {@link LocalTime} must be after
                   * @return the random {@link LocalTime}
                   * @throws IllegalArgumentException if after is null or if after is before {@link LocalTime#MAX}
                   */
                  public static LocalTime randomLocalTimeAfter(LocalTime after) {
                    checkArgument(after != null, "After must be non-null");
                    checkArgument(after.isBefore(LocalTime.MAX), "After must be before %s", LocalTime.MAX);
                    long nanoOfDay = randomAfter(NANO_OF_DAY, after.toNanoOfDay() + 1);
                    return LocalTime.ofNanoOfDay(nanoOfDay);
                  }

                  /**
                   * Returns a random {@link LocalTime} that is before the given {@link LocalTime}.
                   *
                   * @param before the value that returned {@link LocalTime} must be before
                   * @return the random {@link LocalTime}
                   * @throws IllegalArgumentException if before is null or if before is after {@link LocalTime#MIN}
                   */
                  public static LocalTime randomLocalTimeBefore(LocalTime before) {
                    checkArgument(before != null, "Before must be non-null");
                    checkArgument(before.isAfter(LocalTime.MIN), "Before must be after %s", LocalTime.MIN);
                    long nanoOfDay = randomBefore(NANO_OF_DAY, before.toNanoOfDay());
                    return LocalTime.ofNanoOfDay(nanoOfDay);
                  }

                  /**
                   * Returns a random valid value for the given {@link TemporalField} between <code>
                   * TemporalField.range().min()</code> and <code>TemporalField.range().max()</code>. For example,
                   * <code>random({@link ChronoField#HOUR_OF_DAY})</code> will return a random value between 0-23.
                   *
                   * <p>Note: This will never return {@link Long#MAX_VALUE}. Even if it's a valid value for the
                   * given {@link TemporalField}.
                   *
                   * @param field the {@link TemporalField} to return a valid value for
                   * @return the random value
                   */
                  public static long random(TemporalField field) {
                    long max = Math.min(field.range().getMaximum(), Long.MAX_VALUE - 1);
                    return randomLong(field.range().getMinimum(), max + 1);
                  }

                  /**
                   * Returns a random valid value for the given {@link TemporalField} between <code>
                   * startInclusive</code> and <code>endExclusive</code>.
                   *
                   * @param field the {@link TemporalField} to return a valid value for
                   * @param startInclusive the smallest value that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random value
                   * @throws IllegalArgumentException if startInclusive is on or before <code>
                   *     TemporalField.range().min()
                   *     </code>, if endExclusive is on or after <code>TemporalField.range().max()</code>, or if
                   *     startInclusive is after after endExclusive
                   */
                  public static long random(TemporalField field, long startInclusive, long endExclusive) {
                    long min = field.range().getMinimum();
                    long max = field.range().getMaximum();
                    checkArgument(startInclusive >= min, "Start must be on or after %s", min);
                    checkArgument(endExclusive <= max, "End must be on or before %s", max);
                    checkArgument(startInclusive <= endExclusive, "End must be on or after start");
                    min = Math.max(startInclusive, field.range().getMinimum());
                    max = Math.min(endExclusive, field.range().getMaximum());
                    return randomLong(min, max);
                  }

                  /**
                   * Returns a random valid value for the given {@link TemporalField} between <code>
                   * after</code> and <code>TemporalField.range().max()</code>. For example, <code>
                   * randomAfter({@link ChronoField#HOUR_OF_DAY}, 12)</code> will return a random value between
                   * 13-23.
                   *
                   * <p>Note: This will never return {@link Long#MAX_VALUE}. Even if it's a valid value for the
                   * given {@link TemporalField}.
                   *
                   * @param field the {@link TemporalField} to return a valid value for
                   * @param after the value that the returned value must be after
                   * @return the random value
                   * @throws IllegalArgumentException if after is before <code>
                   *     TemporalField.range().min()
                   *     </code> or if after is on or after <code>TemporalField.range().max()</code>
                   */
                  public static long randomAfter(TemporalField field, long after) {
                    Long min = field.range().getMinimum();
                    Long max = field.range().getMaximum();
                    checkArgument(after < max, "After must be before %s", max);
                    checkArgument(after >= min, "After must be on or after %s", min);
                    return randomLong(after + 1, Math.min(max, Long.MAX_VALUE - 1) + 1);
                  }

                  /**
                   * Returns a random valid value for the given {@link TemporalField} between <code>
                   * TemporalField.range().min()</code> and <code>before</code>. For example, <code>
                   * randomBefore({@link ChronoField#HOUR_OF_DAY}, 13)</code> will return a random value between
                   * 0-12.
                   *
                   * @param field the {@link TemporalField} to return a valid value for
                   * @param before the value that the returned value must be before
                   * @return the random value
                   * @throws IllegalArgumentException if before is after <code>
                   *     TemporalField.range().max()
                   *     </code> or if before is on or before <code>TemporalField.range().min()</code>
                   */
                  public static long randomBefore(TemporalField field, long before) {
                    long min = field.range().getMinimum();
                    long max = field.range().getMaximum();
                    checkArgument(before > min, "Before must be after %s", min);
                    checkArgument(before <= max, "Before must be on or before %s", max);
                    return randomLong(min, before);
                  }

                  /**
                   * Returns a random {@link MonthDay} between January 1st and December 31st. Includes leap day if
                   * the current year is a leap year.
                   *
                   * @return the random {@link MonthDay}
                   */
                  public static MonthDay randomMonthDay() {
                    return randomMonthDay(Year.now().isLeap());
                  }

                  /**
                   * Returns a random {@link MonthDay} between January 1st and December 31st.
                   *
                   * @param includeLeapDay whether or not to include leap day
                   * @return the random {@link MonthDay}
                   */
                  public static MonthDay randomMonthDay(boolean includeLeapDay) {
                    Month month = randomMonth();
                    int dayOfMonth = RandomUtils.nextInt(1, month.maxLength() + 1);
                    MonthDay monthDay = MonthDay.of(month, dayOfMonth);
                    if (!includeLeapDay && DateUtils.isLeapDay(monthDay)) {
                      return randomMonthDay(false);
                    }
                    return monthDay;
                  }

                  /**
                   * Returns a random {@link MonthDay} within the specified range. Includes leap day if the current
                   * year is a leap year.
                   *
                   * @param startInclusive the earliest {@link MonthDay} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link MonthDay}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static MonthDay randomMonthDay(MonthDay startInclusive, MonthDay endExclusive) {
                    return randomMonthDay(startInclusive, endExclusive, Year.now().isLeap());
                  }

                  /**
                   * Returns a random {@link MonthDay} within the specified range.
                   *
                   * @param startInclusive the earliest {@link MonthDay} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @param includeLeapDay whether or not to include leap day
                   * @return the random {@link MonthDay}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null, if endExclusive is
                   *     earlier than startInclusive, or if startInclusive or endExclusive are leap day and
                   *     includeLeapDay is false
                   */
                  public static MonthDay randomMonthDay(
                      MonthDay startInclusive, MonthDay endExclusive, boolean includeLeapDay) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    checkArgument(
                        includeLeapDay || !startInclusive.equals(LEAP_DAY) || !endExclusive.equals(LEAP_DAY),
                        "Start and End can't both be leap day");
                    int year = includeLeapDay ? LEAP_YEAR : LEAP_YEAR - 1;
                    LocalDate start = startInclusive.atYear(year);
                    LocalDate end = endExclusive.atYear(year);
                    LocalDate localDate = randomLocalDate(start, end);
                    return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
                  }

                  /**
                   * Returns a random {@link MonthDay} that is after the given {@link MonthDay}. Includes leap day
                   * if the current year is a leap year.
                   *
                   * @param after the value that returned {@link MonthDay} must be after
                   * @return the random {@link MonthDay}
                   * @throws IllegalArgumentException if after is null or if after is last day of year (December
                   *     31st)
                   */
                  public static MonthDay randomMonthDayAfter(MonthDay after) {
                    return randomMonthDayAfter(after, Year.now().isLeap());
                  }

                  /**
                   * Returns a random {@link MonthDay} that is after the given {@link MonthDay}.
                   *
                   * @param after the value that returned {@link MonthDay} must be after
                   * @param includeLeapDay whether or not to include leap day
                   * @return the random {@link MonthDay}
                   * @throws IllegalArgumentException if after is null or if after is last day of year (December
                   *     31st)
                   */
                  public static MonthDay randomMonthDayAfter(MonthDay after, boolean includeLeapDay) {
                    checkArgument(after != null, "After must be non-null");
                    checkArgument(after.isBefore(MonthDay.of(DECEMBER, 31)), "After must be before December 31st");
                    int year = includeLeapDay ? LEAP_YEAR : LEAP_YEAR - 1;
                    LocalDate start = after.atYear(year).plus(1, DAYS);
                    LocalDate end = Year.of(year + 1).atDay(1);
                    LocalDate localDate = randomLocalDate(start, end);
                    return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
                  }

                  /**
                   * Returns a random {@link MonthDay} that is before the given {@link MonthDay}. Includes leap day
                   * if the current year is a leap year.
                   *
                   * @param before the value that returned {@link MonthDay} must be before
                   * @return the random {@link MonthDay}
                   * @throws IllegalArgumentException if before is null or if before is first day of year (January
                   *     1st)
                   */
                  public static MonthDay randomMonthDayBefore(MonthDay before) {
                    return randomMonthDayBefore(before, Year.now().isLeap());
                  }

                  /**
                   * Returns a random {@link MonthDay} that is before the given {@link MonthDay}.
                   *
                   * @param before the value that returned {@link MonthDay} must be before
                   * @param includeLeapDay whether or not to include leap day
                   * @return the random {@link MonthDay}
                   * @throws IllegalArgumentException if before is null or if before is first day of year (January
                   *     1st)
                   */
                  public static MonthDay randomMonthDayBefore(MonthDay before, boolean includeLeapDay) {
                    checkArgument(before != null, "Before must be non-null");
                    checkArgument(before.isAfter(MonthDay.of(JANUARY, 1)), "Before must be after January 1st");
                    int year = includeLeapDay ? LEAP_YEAR : LEAP_YEAR - 1;
                    LocalDate startOfYear = Year.of(year).atDay(1);
                    LocalDate end = before.atYear(year);
                    LocalDate localDate = randomLocalDate(startOfYear, end);
                    return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
                  }

                  /**
                   * Returns a random {@link YearMonth} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link YearMonth}
                   */
                  public static YearMonth randomYearMonth() {
                    return YearMonth.of(randomYear().getValue(), randomMonth());
                  }

                  /**
                   * Returns a random {@link YearMonth} within the specified range.
                   *
                   * @param startInclusive the earliest {@link YearMonth} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link YearMonth}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
                   *     is earlier than startInclusive
                   */
                  public static YearMonth randomYearMonth(YearMonth startInclusive, YearMonth endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    LocalDate start = startInclusive.atDay(1);
                    LocalDate end = endExclusive.atDay(1);
                    LocalDate localDate = randomLocalDate(start, end);
                    return YearMonth.of(localDate.getYear(), localDate.getMonth());
                  }

                  /**
                   * Returns a random {@link YearMonth} that is after the current system clock.
                   *
                   * @return the random {@link YearMonth}
                   */
                  public static YearMonth randomFutureYearMonth() {
                    return randomYearMonthAfter(YearMonth.now());
                  }

                  /**
                   * Returns a random {@link YearMonth} that is after the given {@link YearMonth}.
                   *
                   * @param after the value that returned {@link YearMonth} must be after
                   * @return the random {@link YearMonth}
                   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static YearMonth randomYearMonthAfter(YearMonth after) {
                    checkArgument(after != null, "After must be non-null");
                    LocalDate start = after.plus(1, MONTHS).atDay(1);
                    LocalDate localDate = randomLocalDateAfter(start);
                    return YearMonth.of(localDate.getYear(), localDate.getMonth());
                  }

                  /**
                   * Returns a random {@link YearMonth} that is before the current system clock.
                   *
                   * @return the random {@link YearMonth}
                   */
                  public static YearMonth randomPastYearMonth() {
                    return randomYearMonthBefore(YearMonth.now());
                  }

                  /**
                   * Returns a random {@link YearMonth} that is before the given {@link YearMonth}.
                   *
                   * @param before the value that returned {@link YearMonth} must be before
                   * @return the random {@link YearMonth}
                   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static YearMonth randomYearMonthBefore(YearMonth before) {
                    checkArgument(before != null, "Before must be non-null");
                    LocalDate start = before.atDay(1);
                    LocalDate localDate = randomLocalDateBefore(start);
                    return YearMonth.of(localDate.getYear(), localDate.getMonth());
                  }

                  /**
                   * Returns a random {@link Year} between {@link RandomDateUtils#MIN_INSTANT} and {@link
                   * RandomDateUtils#MAX_INSTANT}.
                   *
                   * @return the random {@link Year}
                   */
                  public static Year randomYear() {
                    return Year.of(RandomUtils.nextInt(MIN_YEAR, MAX_YEAR));
                  }

                  /**
                   * Returns a random {@link Year} within the specified range.
                   *
                   * @param startInclusive the earliest {@link Year} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link Year}
                   * @throws IllegalArgumentException if startInclusive or endExclusive are null, if endExclusive is
                   *     earlier than startInclusive, if either are before {@link RandomDateUtils#MIN_INSTANT}, or
                   *     if either are after {@link RandomDateUtils#MAX_INSTANT}
                   */
                  public static Year randomYear(Year startInclusive, Year endExclusive) {
                    checkArgument(startInclusive != null, "Start must be non-null");
                    checkArgument(endExclusive != null, "End must be non-null");
                    return randomYear(startInclusive.getValue(), endExclusive.getValue());
                  }

                  /**
                   * Returns a random {@link Year} within the specified range.
                   *
                   * @param startInclusive the earliest {@link Year} that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random {@link Year}
                   * @throws IllegalArgumentException if endExclusive is earlier than startInclusive, if end is
                   *     before {@link RandomDateUtils#MIN_INSTANT}, or if start is after {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static Year randomYear(int startInclusive, int endExclusive) {
                    checkArgument(startInclusive < MAX_YEAR, "Start must be before %s", MAX_YEAR);
                    checkArgument(startInclusive >= MIN_YEAR, "Start must be on or after %s", MIN_YEAR);
                    checkArgument(endExclusive > MIN_YEAR, "End must be after %s", MIN_YEAR);
                    checkArgument(endExclusive <= MAX_YEAR, "End must be on or before %s", MAX_YEAR);
                    checkArgument(startInclusive <= endExclusive, "End must be on or after start");
                    return Year.of(RandomUtils.nextInt(startInclusive, endExclusive));
                  }

                  /**
                   * Returns a random {@link Year} that is after the current system clock.
                   *
                   * @return the random {@link Year}
                   */
                  public static Year randomFutureYear() {
                    return randomYearAfter(Year.now());
                  }

                  /**
                   * Returns a random {@link Year} that is after the given {@link Year}.
                   *
                   * @param after the value that returned {@link Year} must be after
                   * @return the random {@link Year}
                   * @throws IllegalArgumentException if after is null or if after greater than or equal to {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static Year randomYearAfter(Year after) {
                    checkArgument(after != null, "After must be non-null");
                    return randomYearAfter(after.getValue());
                  }

                  /**
                   * Returns a random {@link Year} that is after the given {@link Year}.
                   *
                   * @param after the value that returned {@link Year} must be after
                   * @return the random {@link Year}
                   * @throws IllegalArgumentException if after greater than or equal to {@link
                   *     RandomDateUtils#MAX_INSTANT}
                   */
                  public static Year randomYearAfter(int after) {
                    checkArgument(after < MAX_YEAR, "After must be before %s", MAX_YEAR);
                    return Year.of(RandomUtils.nextInt(after + 1, MAX_YEAR));
                  }

                  /**
                   * Returns a random {@link Year} that is before the current system clock.
                   *
                   * @return the random {@link Year}
                   */
                  public static Year randomPastYear() {
                    return randomYearBefore(Year.now());
                  }

                  /**
                   * Returns a random {@link Year} that is before the given {@link Year}.
                   *
                   * @param before the value that returned {@link Year} must be before
                   * @return the random {@link Year}
                   * @throws IllegalArgumentException if before is null or if before is less than or equal to {@link
                   *     RandomDateUtils#MIN_INSTANT}
                   */
                  public static Year randomYearBefore(Year before) {
                    checkArgument(before != null, "Before must be non-null");
                    return randomYearBefore(before.getValue());
                  }

                  /**
                   * Returns a random {@link Year} that is before the given {@link RandomDateUtils#MIN_INSTANT}.
                   *
                   * @param before the value that returned {@link Year} must be before
                   * @return the random {@link Year}
                   * @throws IllegalArgumentException if before is less than or equal to 1970
                   */
                  public static Year randomYearBefore(int before) {
                    checkArgument(before > MIN_YEAR, "Before must be after %s", MIN_YEAR);
                    return Year.of(RandomUtils.nextInt(MIN_YEAR, before));
                  }

                  /**
                   * Returns a random {@link Clock} in the UTC {@link ZoneId} with a random instant in time.
                   *
                   * @return the random {@link Clock}
                   */
                  public static Clock randomFixedUtcClock() {
                    return Clock.fixed(randomInstant(), UTC);
                  }

                  /**
                   * Returns a random {@link Clock} in a random {@link ZoneId} with a random instant in time.
                   *
                   * @return the random {@link Clock}
                   */
                  public static Clock randomFixedClock() {
                    return Clock.fixed(randomInstant(), randomZoneId());
                  }

                  /**
                   * Returns a random {@link ZoneId} from {@link ZoneOffset#getAvailableZoneIds()}.
                   *
                   * @return the random {@link ZoneId}
                   */
                  public static ZoneId randomZoneId() {
                    return ZoneId.of(IterableUtils.randomFrom(ZoneOffset.getAvailableZoneIds()));
                  }

                  /**
                   * Returns a random {@link DayOfWeek}.
                   *
                   * @return the random {@link DayOfWeek}
                   */
                  public static DayOfWeek randomDayOfWeek() {
                    return RandomEnumUtils.random(DayOfWeek.class);
                  }

                  /**
                   * Returns a random {@link Month}.
                   *
                   * @return the random {@link Month}
                   */
                  public static Month randomMonth() {
                    return RandomEnumUtils.random(Month.class);
                  }

                  /**
                   * Returns a random {@link ZoneOffset} (-18:00 to +18:00).
                   *
                   * @return the random {@link ZoneOffset}
                   */
                  public static ZoneOffset randomZoneOffset() {
                    int totalSeconds =
                        MAX_ZONE_OFFSET_SECONDS - RandomUtils.nextInt(0, MAX_ZONE_OFFSET_SECONDS * 2 + 1);
                    return ZoneOffset.ofTotalSeconds(totalSeconds);
                  }

                  /**
                   * Returns a random {@link Period} which may be positive, negative, or {@link Period#ZERO}.
                   *
                   * @return the random {@link Period}
                   */
                  public static Period randomPeriod() {
                    return Period.of(randomInt(), randomInt(), randomInt());
                  }

                  /**
                   * Returns a random {@link Period} which will be positive.
                   *
                   * @return the random {@link Period}
                   */
                  public static Period randomPositivePeriod() {
                    return Period.of(randomPositiveInt(), randomPositiveInt(), randomPositiveInt());
                  }

                  /**
                   * Returns a random {@link Period} which will be negative.
                   *
                   * @return the random {@link Period}
                   */
                  public static Period randomNegativePeriod() {
                    return Period.of(randomNegativeInt(), randomInt(), randomInt());
                  }

                  /**
                   * Returns a random {@link Duration} which may be positive, negative, or {@link Duration#ZERO}.
                   *
                   * @return the random {@link Duration}
                   */
                  public static Duration randomDuration() {
                    return Duration.ofNanos(randomLong());
                  }

                  /**
                   * Returns a random {@link Duration} which will be positive.
                   *
                   * @return the random {@link Duration}
                   */
                  public static Duration randomPositiveDuration() {
                    return Duration.ofNanos(randomPositiveLong());
                  }

                  /**
                   * Returns a random {@link Duration} which will be negative.
                   *
                   * @return the random {@link Duration}
                   */
                  public static Duration randomNegativeDuration() {
                    return Duration.ofNanos(randomNegativeLong());
                  }
                }""";
        String test_DateUtils = """
                // package com.github.rkumsher.date;
                                
                import static com.google.common.base.Preconditions.*;
                import static java.time.Month.FEBRUARY;
                import static java.time.temporal.ChronoField.DAY_OF_MONTH;
                import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
                                
                import java.time.LocalDateTime;
                import java.time.LocalTime;
                import java.time.MonthDay;
                import java.time.ZoneId;
                import java.time.temporal.ChronoField;
                import java.time.temporal.TemporalAccessor;
                import java.util.Date;
                                
                /** Utility library for working with {@link Date}s. */
                public final class DateUtils {
                                
                  /** February 29th. */
                  static final MonthDay LEAP_DAY = MonthDay.of(FEBRUARY, 29);
                                
                  private DateUtils() {}
                                
                  /**
                   * Returns whether or not the given {@link TemporalAccessor} is leap day (February 29th).
                   *
                   * @return whether or not the given {@link TemporalAccessor} is leap day
                   * @throws IllegalArgumentException if the given {@link TemporalAccessor} does not support {@link
                   *     ChronoField#MONTH_OF_YEAR} or {@link ChronoField#DAY_OF_MONTH}
                   */
                  public static boolean isLeapDay(TemporalAccessor temporal) {
                    checkArgument(
                        temporal.isSupported(MONTH_OF_YEAR), "%s does not support MONTH_OF_YEAR", temporal);
                    checkArgument(temporal.isSupported(DAY_OF_MONTH), "%s does not support DAY_OF_MONTH", temporal);
                    MonthDay monthDay = MonthDay.from(temporal);
                    return monthDay.equals(LEAP_DAY);
                  }
                                
                  /**
                   * Returns the {@link Date} of midnight at the start of the given {@link Date}.
                   *
                   * <p>This returns a {@link Date} formed from the given {@link Date} at the time of midnight,
                   * 00:00, at the start of this {@link Date}.
                   *
                   * @return the {@link Date} of midnight at the start of the given {@link Date}
                   */
                  public static Date atStartOfDay(Date date) {
                    LocalDateTime localDateTime = dateToLocalDateTime(date);
                    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
                    return localDateTimeToDate(startOfDay);
                  }
                                
                  /**
                   * Returns the {@link Date} at the end of day of the given {@link Date}.
                   *
                   * <p>This returns a {@link Date} formed from the given {@link Date} at the time of 1 millisecond
                   * prior to midnight the next day.
                   *
                   * @return the {@link Date} at the end of day of the given {@link Date}j
                   */
                  public static Date atEndOfDay(Date date) {
                    LocalDateTime localDateTime = dateToLocalDateTime(date);
                    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
                    return localDateTimeToDate(endOfDay);
                  }
                                
                  private static LocalDateTime dateToLocalDateTime(Date date) {
                    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                  }
                                
                  static Date localDateTimeToDate(LocalDateTime localDateTime) {
                    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                  }
                }
                """;
        String s1 = """
                /**\n" +
                "   * Returns a random valid value for the given {@link TemporalField} between <code>\n" +
                "   * startInclusive</code> and <code>endExclusive</code>.\n" +
                "   *\n" +
                "   * @param field the {@link TemporalField} to return a valid value for\n" +
                "   * @param startInclusive the smallest value that can be returned\n" +
                "   * @param endExclusive the upper bound (not included)\n" +
                "   * @return the random value\n" +
                "   * @throws IllegalArgumentException if startInclusive is on or before <code>\n" +
                "   *     TemporalField.range().min()\n" +
                "   *     </code>, if endExclusive is on or after <code>TemporalField.range().max()</code>, or if\n" +
                "   *     startInclusive is after after endExclusive\n" +
                "   */\n" +
                "  public static long random(TemporalField field, long startInclusive, long endExclusive) {\n" +
                "    long min = field.range().getMinimum();\n" +
                "    long max = field.range().getMaximum();\n" +
                "    checkArgument(startInclusive >= min, \"Start must be on or after %s\", min);\n" +
                "    checkArgument(endExclusive <= max, \"End must be on or before %s\", max);\n" +
                "    checkArgument(startInclusive <= endExclusive, \"End must be on or after start\");\n" +
                "    min = Math.max(startInclusive, field.range().getMinimum());\n" +
                "    max = Math.min(endExclusive, field.range().getMaximum());\n" +
                "    return randomLong(min, max);\n" +
                "  }" +
                """;
        String test_PathUtils = """
                // package com.nordstrom.common.file;
                                
                import java.io.File;
                import java.io.IOException;
                import java.nio.file.FileSystems;
                import java.nio.file.FileVisitOption;
                import java.nio.file.FileVisitResult;
                import java.nio.file.FileVisitor;
                import java.nio.file.Files;
                import java.nio.file.Path;
                import java.nio.file.PathMatcher;
                import java.nio.file.Paths;
                import java.nio.file.attribute.BasicFileAttributes;
                import java.util.ArrayList;
                import java.util.Collections;
                import java.util.EnumSet;
                import java.util.List;
                import java.util.Objects;
                                
                /**
                 * This utility class provides a {@link #getNextPath(Path, String, String) getNextPath} method to acquire the next file
                 * path in sequence for the specified base name and extension in the indicated target folder.  If the target folder
                 * already contains at least one file that matches the specified base name and extension, the algorithm used to select
                 * the next path will always return a path whose index is one more than the highest index that currently exists. (If a
                 * single file with no index is found, its implied index is 0.)
                 * <br><br>
                 * <b>Example usage of {@code getNextPath}</b>
                 * <pre>
                 *     ...
                 *    \s
                 *     /*
                 *      * This example gets the next path in sequence for base name `artifact`
                 *      * and extension `txt` in the TestNG output directory.
                 *      *\s
                 *      * For purposes of this example, the output directory already contains\s
                 *      * the following files: `artifact.txt`, `artifact-3.txt`
                 *      */
                 *\s
                 *     Path collectionPath = Paths.get(testContext.getOutputDirectory());
                 *     // => C:\\git\\my-project\\test-output\\Default suite
                 *\s
                 *     Path artifactPath;
                 *     try {
                 *         artifactPath = PathUtils.getNextPath(collectionPath, "artifact", "txt");
                 *         // => C:\\git\\my-project\\test-output\\Default suite\\artifact-4.txt
                 *     } catch (IOException e) {
                 *         provider.getLogger().info("Unable to get output path; no artifact was captured", e);
                 *         return;
                 *     }
                 *    \s
                 *     ...
                 * </pre>
                 */
                public final class PathUtils {
                                
                    private PathUtils() {
                        throw new AssertionError("PathUtils is a static utility class that cannot be instantiated");
                    }
                   \s
                    private static final String SUREFIRE_PATH = "surefire-reports";
                    private static final String FAILSAFE_PATH = "failsafe-reports";
                   \s
                    /**
                     * This enumeration contains methods to help build proxy subclass names and select reports directories.
                     */
                    public enum ReportsDirectory {
                       \s
                        SUREFIRE_1("(Test)(.*)", SUREFIRE_PATH),
                        SUREFIRE_2("(.*)(Test)", SUREFIRE_PATH),
                        SUREFIRE_3("(.*)(Tests)", SUREFIRE_PATH),
                        SUREFIRE_4("(.*)(TestCase)", SUREFIRE_PATH),
                        FAILSAFE_1("(IT)(.*)", FAILSAFE_PATH),
                        FAILSAFE_2("(.*)(IT)", FAILSAFE_PATH),
                        FAILSAFE_3("(.*)(ITCase)", FAILSAFE_PATH),
                        ARTIFACT(".*", "artifact-capture");
                       \s
                        private String regex;
                        private String folder;
                       \s
                        ReportsDirectory(String regex, String folder) {
                            this.regex = regex;
                            this.folder = folder;
                        }
                       \s
                        /**
                         * Get the regular expression that matches class names for this constant.
                         *\s
                         * @return class-matching regular expression string
                         */
                        public String getRegEx() {
                            return regex;
                        }
                       \s
                        /**
                         * Get the name of the folder associated with this constant.
                         *\s
                         * @return class-related folder name
                         */
                        public String getFolder() {
                            return folder;
                        }
                       \s
                        /**
                         * Get the resolved Maven-derived path associated with this constant.
                         *\s
                         * @return Maven folder path
                         */
                        public Path getPath() {
                            return getTargetPath().resolve(folder);
                        }
                       \s
                        /**
                         * Get the reports directory constant for the specified test class object.
                         *\s
                         * @param obj test class object
                         * @return reports directory constant
                         */
                        public static ReportsDirectory fromObject(Object obj) {
                            String name = obj.getClass().getSimpleName();
                            for (ReportsDirectory constant : values()) {
                                if (name.matches(constant.regex)) {
                                    return constant;
                                }
                            }
                            throw new IllegalStateException("Someone removed the 'default' pattern from this enumeration");
                        }
                       \s
                        /**
                         * Get reports directory path for the specified test class object.
                         *\s
                         * @param obj test class object
                         * @return reports directory path
                         */
                        public static Path getPathForObject(Object obj) {
                            ReportsDirectory constant = fromObject(obj);
                            return getTargetPath().resolve(constant.folder);
                        }
                       \s
                        /**
                         * Get the path for the 'target' folder of the current project.
                         *\s
                         * @return path for project 'target' folder
                         */
                        private static Path getTargetPath() {
                            return Paths.get(getBaseDir(), "target");
                        }
                    }
                                
                    /**
                     * Get the next available path in sequence for the specified base name and extension in the specified folder.
                     *\s
                     * @param targetPath path to target directory for the next available path in sequence
                     * @param baseName base name for the path sequence
                     * @param extension extension for the path sequence
                     * @return the next available path in sequence
                     * @throws IOException if an I/O error is thrown when accessing the starting file.
                     */
                    public static Path getNextPath(Path targetPath, String baseName, String extension) {
                        Objects.requireNonNull(targetPath, "[targetPath] must be non-null");
                        Objects.requireNonNull(baseName, "[baseName] must be non-null");
                        Objects.requireNonNull(extension, "[extension] must be non-null");
                       \s
                        File targetFile = targetPath.toFile();
                        if ( ! (targetFile.exists() && targetFile.isDirectory())) {
                            throw new IllegalArgumentException("[targetPath] must specify an existing directory");
                        }
                        if (baseName.isEmpty()) {
                            throw new IllegalArgumentException("[baseName] must specify a non-empty string");
                        }
                        if (extension.isEmpty()) {
                            throw new IllegalArgumentException("[extension] must specify a non-empty string");
                        }
                       \s
                        Visitor visitor = new Visitor(baseName, extension);
                        Files.walkFileTree(targetPath, EnumSet.noneOf(FileVisitOption.class), 1, visitor);
                       \s
                        return targetPath.resolve(visitor.getNewName());
                    }
                                
                    /**
                     * Get project base directory.
                     *\s
                     * @return project base directory
                     */
                    public static String getBaseDir() {
                        Path currentRelativePath = Paths.get(System.getProperty("user.dir"));
                        return currentRelativePath.toAbsolutePath().toString();
                    }
                                
                    private static class Visitor implements FileVisitor<Path> {
                       \s
                        private String baseName;
                        private String extension;
                        private int base, ext;
                        private PathMatcher pathMatcher;
                        private List<Integer> intList = new ArrayList<>();
                                
                        Visitor(String baseName, String extension) {
                            this.baseName = baseName;
                            this.extension = extension;
                            this.base = baseName.length();
                            this.ext = extension.length() + 1;
                            this.pathMatcher = FileSystems.getDefault().getPathMatcher("regex:\\\\Q" + baseName + "\\\\E(-\\\\d+)?\\\\." + extension);
                        }
                       \s
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                            return FileVisitResult.CONTINUE;
                        }
                                
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                            if (attrs.isRegularFile() && pathMatcher.matches(file.getFileName())) {
                                String name = file.getFileName().toString();
                                String iStr = "0" + name.substring(base, name.length() - ext);
                                iStr = iStr.replace("0-", "");
                                intList.add(Integer.valueOf(iStr) + 1);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                                
                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) {
                            return FileVisitResult.CONTINUE;
                        }
                                
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                            return FileVisitResult.CONTINUE;
                        }
                       \s
                        public String getNewName() {
                            String newName;
                           \s
                            if (intList.isEmpty()) {
                                newName = baseName + "." + extension;
                            } else {
                                Collections.sort(intList, Collections.reverseOrder());
                                newName = baseName + "-" + intList.get(0) + "." + extension;
                            }
                           \s
                            return newName;
                        }
                    }
                }
                """;
        String test_RandomArrayUtils = """
                // package com.github.rkumsher.collection;
                                
                import static com.google.common.base.Preconditions.*;
                import static com.google.common.collect.Iterables.isEmpty;
                                
                import java.util.Arrays;
                import java.util.Set;
                import java.util.function.Supplier;
                import java.util.stream.Stream;
                                
                import com.google.common.collect.ContiguousSet;
                import com.google.common.collect.DiscreteDomain;
                import com.google.common.collect.Range;
                                
                /** Utility library to generate random arrays. */
                public final class RandomArrayUtils {
                                
                  private RandomArrayUtils() {}
                                
                  /**
                   * Returns an array filled randomly from the given elements.
                   *
                   * @param elements elements to randomly fill array from
                   * @param size range that the size of the array will be randomly chosen from
                   * @param <T> the type of elements in the given iterable
                   * @return array filled randomly from the given elements
                   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size
                   *     range contains negative integers
                   */
                  public static <T> T[] randomArrayFrom(T[] elements, Range<Integer> size) {
                    return randomArrayFrom(Arrays.asList(elements), size);
                  }
                                
                  /**
                   * Returns an array filled randomly from the given elements.
                   *
                   * @param elements elements to randomly fill array from
                   * @param size range that the size of the array will be randomly chosen from
                   * @param <T> the type of elements in the given iterable
                   * @return array filled randomly from the given elements
                   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size
                   *     range contains negative integers
                   */
                  public static <T> T[] randomArrayFrom(Iterable<T> elements, Range<Integer> size) {
                    checkArgument(!isEmpty(elements), "Elements to populate random array from must not be empty");
                    return randomArrayFrom(() -> IterableUtils.randomFrom(elements), size);
                  }
                                
                  /**
                   * Returns an array filled randomly from the given elements.
                   *
                   * @param elements elements to randomly fill array from
                   * @param size of the random array to return
                   * @param <T> the type of elements in the given iterable
                   * @return array filled randomly from the given elements
                   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size is
                   *     negative
                   */
                  public static <T> T[] randomArrayFrom(T[] elements, int size) {
                    return randomArrayFrom(Arrays.asList(elements), size);
                  }
                                
                  /**
                   * Returns an array filled randomly from the given elements.
                   *
                   * @param elements elements to randomly fill array from
                   * @param size of the random array to return
                   * @param <T> the type of elements in the given iterable
                   * @return array filled randomly from the given elements
                   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size is
                   *     negative
                   */
                  public static <T> T[] randomArrayFrom(Iterable<T> elements, int size) {
                    checkArgument(!isEmpty(elements), "Elements to populate random array from must not be empty");
                    return randomArrayFrom(() -> IterableUtils.randomFrom(elements), size);
                  }
                                
                  /**
                   * Returns an array filled from the given element supplier.
                   *
                   * @param elementSupplier element supplier to fill array from
                   * @param size range that the size of the array will be randomly chosen from
                   * @param <T> the type of element the given supplier returns
                   * @return array filled from the given elements
                   * @throws IllegalArgumentException if the size range contains negative integers
                   */
                  public static <T> T[] randomArrayFrom(Supplier<T> elementSupplier, Range<Integer> size) {
                    checkArgument(
                        size.hasLowerBound() && size.lowerEndpoint() >= 0,
                        "Size range must consist of only positive integers");
                    Set<Integer> rangeSet = ContiguousSet.create(size, DiscreteDomain.integers());
                    int limit = IterableUtils.randomFrom(rangeSet);
                    return randomArrayFrom(elementSupplier, limit);
                  }
                                
                  /**
                   * Returns an array filled from the given element supplier.
                   *
                   * @param elementSupplier element supplier to fill array from
                   * @param size of the random array to return
                   * @param <T> the type of element the given supplier returns
                   * @return array filled from the given elements
                   * @throws IllegalArgumentException if the size is negative
                   */
                  @SuppressWarnings("unchecked")
                  public static <T> T[] randomArrayFrom(Supplier<T> elementSupplier, int size) {
                    checkArgument(size >= 0, "Size must be greater than or equal to zero");
                    return (T[]) Stream.generate(elementSupplier).limit(size).toArray(Object[]::new);
                  }
                }
                """;
        String test_VolumeInfo = """
                // package com.nordstrom.common.file;
                                
                import java.io.BufferedReader;
                import java.io.File;
                import java.io.IOException;
                import java.io.InputStream;
                import java.io.InputStreamReader;
                import java.util.HashMap;
                import java.util.Map;
                import java.util.regex.Matcher;
                import java.util.regex.Pattern;
                                
                import com.nordstrom.common.file.OSInfo.OSType;
                                
                public class VolumeInfo {
                   \s
                    static final boolean IS_WINDOWS = (OSInfo.getDefault().getType() == OSType.WINDOWS);
                   \s
                    private VolumeInfo() {
                        throw new AssertionError("VolumeInfo is a static utility class that cannot be instantiated");
                    }
                   \s
                    public static Map<String, VolumeProps> getVolumeProps() {
                        Process mountProcess;
                        if (IS_WINDOWS) {
                            String[] cmd = {"sh", "-c", "mount | grep noumount"};
                            mountProcess = Runtime.getRuntime().exec(cmd);
                        } else {
                            mountProcess = Runtime.getRuntime().exec("mount");
                        }
                        return getVolumeProps(mountProcess.getInputStream());
                    }
                                
                    public static Map<String, VolumeProps> getVolumeProps(InputStream is) {
                        Map<String, VolumeProps> propsList = new HashMap<>();
                        Pattern template = Pattern.compile("(.+) on (.+) type (.+) \\\\((.+)\\\\)");
                       \s
                        InputStreamReader isr = new InputStreamReader(is);
                        try(BufferedReader mountOutput = new BufferedReader(isr)) {
                            String line;
                            while(null != (line = mountOutput.readLine())) {
                                Matcher matcher = template.matcher(line);
                                if (matcher.matches()) {
                                    String spec = matcher.group(1);
                                    String file = matcher.group(2);
                                    String type = matcher.group(3);
                                    String[] opts = matcher.group(4).split(",");
                                    VolumeProps props = new VolumeProps(spec, file, type, opts);
                                    if (props.size > 0L) {
                                        propsList.put(spec, props);
                                    }
                                }
                            }
                        }
                        return propsList;
                    }
                   \s
                    public static class VolumeProps {
                       \s
                        String file;
                        String type;
                        String[] opts;
                       \s
                        private long size;
                        private long free;
                       \s
                        VolumeProps(String spec, String file, String type, String... opts) {
                            if (IS_WINDOWS) {
                                this.file = spec;
                            } else {
                                this.file = file;
                            }
                           \s
                            this.type = type;
                            this.opts = opts;
                           \s
                            File f = new File(this.file);
                            this.size = f.getTotalSpace();
                            this.free = f.getFreeSpace();
                        }
                       \s
                        public String getFile() {
                            return file;
                        }
                                
                        public String getType() {
                            return type;
                        }
                                
                        public String[] getOpts() {
                            return opts;
                        }
                       \s
                        public long getSize() {
                            return size;
                        }
                        public long getFree() {
                            return free;
                        }
                    }
                }
                """;
        String test_OSInfo = """
                // package com.nordstrom.common.file;
                                
                import java.util.LinkedHashMap;
                import java.util.LinkedList;
                import java.util.List;
                import java.util.ListIterator;
                import java.util.Map;
                import java.util.Map.Entry;
                                
                /**
                 * This class provides utility methods and abstractions for host operating system features.
                 *\s
                 * @param <T> an operating system mapping enumeration that implements the {@link OSProps} interface
                 */
                public class OSInfo<T extends Enum<T> & OSInfo.OSProps> {
                   \s
                    private static String osName = System.getProperty("os.name");
                    private static String version = System.getProperty("os.version");
                    private static String arch = System.getProperty("os.arch");
                                
                    private final Map<T, String> typeMap = new LinkedHashMap<>();
                   \s
                    /**
                     * Get an object that supports the set of operating systems defined in the {@link OSType} enumeration.
                     *\s
                     * @return OSUtils object that supports the operating systems defined in {@link OSType}
                     */
                    public static OSInfo<OSType> getDefault() {
                        return new OSInfo<>(OSType.class);
                    }
                   \s
                    /**
                     * Create an object that supports the mappings defined by the specified enumeration.
                     *\s
                     * @param enumClass operating system mapping enumeration
                     */
                    public OSInfo(Class<T> enumClass) {
                        putAll(enumClass);
                    }
                   \s
                    /**
                     * Get the enumerated type constant for the active operating system.
                     *\s
                     * @return OS type constant; if no match, returns 'null'
                     */
                    public T getType() {
                        // populate a linked list with the entries of the linked type map
                        List<Entry<T, String>> entryList = new LinkedList<>(typeMap.entrySet());
                        // get a list iterator, setting the cursor at the tail end
                        ListIterator<Entry<T, String>> iterator = entryList.listIterator(entryList.size());
                        // iterate from last to first
                        while (iterator.hasPrevious()) {
                            Entry<T, String> thisEntry = iterator.previous();
                            if (osName.matches(thisEntry.getValue())) {
                                return thisEntry.getKey();
                            }
                        }
                        return null;
                    }
                   \s
                    /**
                     * Add the specified mapping to the collection.<br>
                     * <b>NOTE</b>: If a mapping for the specified constant already exists, this mapping will be replaced.
                     *\s
                     * @param <U> an operating system mapping enumeration that implements the {@link OSProps} interface
                     * @param typeConst OS type constant
                     * @return value of previous mapping; 'null' if no mapping existed
                     */
                    @SuppressWarnings("unchecked")
                    public <U extends Enum<U> & OSProps> String put(U typeConst) {
                        return typeMap.put((T) typeConst, typeConst.pattern());
                    }
                   \s
                    /**
                     * Add the specified mapping to the collection.<br>
                     * <b>NOTE</b>: If a mapping for the specified constant already exists, this mapping will be replaced.
                     *\s
                     * @param <U> an operating system mapping enumeration that implements the {@link OSProps} interface
                     * @param typeConst OS type constant
                     * @param pattern OS name match pattern
                     * @return value of previous mapping; 'null' if no mapping existed
                     */
                    @SuppressWarnings("unchecked")
                    public <U extends Enum<U> & OSProps> String put(U typeConst, String pattern) {
                        return typeMap.put((T) typeConst, pattern);
                    }
                   \s
                    /**
                     * Add the mappings defined by the specified enumeration to the collection.<br>
                     * <b>NOTE</b>: If any of the specified mappings already exist, the previous mappings will be replaced.
                     *\s
                     * @param <U> an operating system mapping enumeration that implements the {@link OSProps} interface
                     * @param enumClass operating system mapping enumeration
                     */
                    public <U extends Enum<U> & OSProps> void putAll(Class<U> enumClass) {
                        for (U typeConst : enumClass.getEnumConstants()) {
                            put(typeConst);
                        }
                    }
                   \s
                    /**
                     * Get the name of the active operating system.
                     *\s
                     * @return name of the active operating system
                     */
                    public static String osName() {
                        return osName;
                    }
                   \s
                    /**
                     * Get the version of the existing operating system.
                     *\s
                     * @return version of the existing operating system
                     */
                    public static String version() {
                        return version;
                    }
                   \s
                    /**
                     * Get the architecture of the active operating system.
                     *\s
                     * @return architecture of the active operating system
                     */
                    public static String arch() {
                        return arch;
                    }
                   \s
                    /**
                     * This enumeration defines the default set of operating system mappings.
                     */
                    public enum OSType implements OSProps {
                        WINDOWS("(?i).*win.*"),
                        MACINTOSH("(?i).*mac.*"),
                        UNIX("(?i).*(?:nix|nux|aix).*"),
                        SOLARIS("(?i).*sunos.*");
                       \s
                        OSType(String pattern) {
                            this.pattern = pattern;
                        }
                       \s
                        private String pattern;
                       \s
                        @Override
                        public String pattern() {
                            return pattern;
                        }
                    }
                   \s
                    /**
                     * This interface defines the required contract for operating system mapping enumerations.
                     */
                    public interface OSProps {
                       \s
                        /**
                         * Get the OS name match pattern for this mapping.
                         * \s
                         * @return OS name match pattern
                         */
                        String pattern();
                       \s
                    }
                                
                }
                """;
        String test_DatabaseUtils = """
                // package com.nordstrom.common.jdbc.utils;
                 
                 import java.sql.CallableStatement;
                 import java.sql.Connection;
                 import java.sql.Driver;
                 import java.sql.DriverManager;
                 import java.sql.ResultSet;
                 import java.sql.SQLException;
                 import java.util.Arrays;
                 import java.util.Iterator;
                 import java.util.Objects;
                 import java.util.ServiceLoader;
                 import java.util.regex.Matcher;
                 import java.util.regex.Pattern;
                 
                 import com.nordstrom.common.base.UncheckedThrow;
                 import com.nordstrom.common.jdbc.Param;
                 import com.nordstrom.common.jdbc.Param.Mode;
                 
                 import java.sql.PreparedStatement;
                 
                 /**
                  * This utility class provides facilities that enable you to define collections of database queries and stored
                  * procedures in an easy-to-execute format.
                  * <p>
                  * Query collections are defined as Java enumerations that implement the {@link QueryAPI}
                  * interface:
                  * <ul>
                  * <li>{@link QueryAPI#getQueryStr() getQueryStr} - Get the query string for this constant. This is the actual query
                  *     that's sent to the database.</li>
                  * <li>{@link QueryAPI#getArgNames() getArgNames} - Get the names of the arguments for this query. This provides
                  *     diagnostic information if the incorrect number of arguments is specified by the client.</li>
                  * <li>{@link QueryAPI#getConnection() getConnection} - Get the connection string associated with this query. This
                  *     eliminates the need for the client to provide this information.</li>
                  * <li>{@link QueryAPI#getEnum() getEnum} - Get the enumeration to which this query belongs. This enables {@link
                  *     #executeQuery(Class, QueryAPI, Object[])} to retrieve the name of the query's enumerated constant for
                  *     diagnostic messages.</li>
                  * </ul>
                  * <p>
                  * Store procedure collections are defined as Java enumerations that implement the {@link SProcAPI}
                  * interface:
                  * <ul>
                  * <li>{@link SProcAPI#getSignature() getSignature} - Get the signature for this stored procedure object. This defines
                  *     the name of the stored procedure and the modes of its arguments. If the stored procedure accepts varargs, this
                  *     will also be indicated.</li>
                  * <li>{@link SProcAPI#getArgTypes() getArgTypes} - Get the argument types for this stored procedure object. </li>
                  * <li>{@link SProcAPI#getConnection() getConnection} - Get the connection string associated with this stored
                  *     procedure. This eliminates the need for the client to provide this information.</li>
                  * <li>{@link SProcAPI#getEnum() getEnum} - Get the enumeration to which this stored procedure belongs. This enables
                  *     {@link #executeStoredProcedure(Class, SProcAPI, Object[])} to retrieve the name of the stored procedured's
                  *     enumerated constant for diagnostic messages.</li>
                  * </ul>
                  * <p>
                  * To maximize usability and configurability, we recommend the following implementation strategy: <ul>
                  * <li>Define your collection as an enumeration: <ul>
                  *     <li>Query collections implement {@link QueryAPI}.</li>
                  *     <li>Stored procedure collections implement {@link SProcAPI}.</li>
                  *     </ul></li>
                  * <li>Define each constant: <ul>
                  *     <li>(query) Specify a property name and a name for each argument (if any).</li>
                  *     <li>(sproc) Declare the signature and the type for each argument (if any).</li>
                  *     </ul></li>
                  * <li>To assist users of your queries, preface their names with a type indicator (<b>GET</b> or <b>UPDATE</b>).</li>
                  * <li>Back query collections with configurations that implement the <b>{@code Settings API}</b>: <ul>
                  *     <li>groupId: com.nordstrom.test-automation.tools</li>
                  *     <li>artifactId: settings</li>
                  *     <li>className: com.nordstrom.automation.settings.SettingsCore</li>
                  *     </ul></li>
                  * <li>To support execution on multiple endpoints, implement {@link QueryAPI#getConnection()} or {@link
                  *     SProcAPI#getConnection()} with sub-configurations or other dynamic data sources (e.g.
                  *     - web service).</li>
                  * </ul>
                  * <b>Query Collection Example</b>
                  *
                  * <pre>
                  * public class OpctConfig extends {@code SettingsCore<OpctConfig.OpctValues>} {
                  *
                  *     private static final String SETTINGS_FILE = "OpctConfig.properties";
                  *
                  *     private OpctConfig() throws ConfigurationException, IOException {
                  *         super(OpctValues.class);
                  *     }
                  *
                  *     public enum OpctValues implements SettingsCore.SettingsAPI, QueryAPI {
                  *         /** args: [  ] */
                  *         GET_RULE_HEAD_DETAILS("opct.query.getRuleHeadDetails"),
                  *         /** args: [ name, zone_id, priority, rule_type ] */
                  *         GET_RULE_COUNT("opct.query.getRuleCount", "name", "zone_id", "priority", "rule_type"),
                  *         /** args: [ role_id, user_id ] */
                  *         UPDATE_USER_ROLE("opct.query.updateRsmUserRole", "role_id", "user_id"),
                  *         /** MST connection string */
                  *         MST_CONNECT("opct.connect.mst"),
                  *         /** RMS connection string */
                  *         RMS_CONNECT("opct.connect.rms");
                  *
                  *         private String key;
                  *         private String[] args;
                  *         private String query;
                  *
                  *         private static OpctConfig config;
                  *         private static String mstConnect;
                  *         private static String rmsConnect;
                  *
                  *         private static {@code EnumSet<OpctValues>} rmsQueries = EnumSet.of(UPDATE_USER_ROLE);
                  *
                  *         static {
                  *             try {
                  *                 config = new OpctConfig();
                  *             } catch (ConfigurationException | IOException e) {
                  *                 throw new RuntimeException("Unable to instantiate OPCT configuration object", e);
                  *             }
                  *         }
                  *
                  *         OpctValues(String key, String... args) {
                  *             this.key = key;
                  *             this.args = args;
                  *         }
                  *
                  *         {@code @Override}
                  *         public String key() {
                  *             return key;
                  *         }
                  *
                  *         {@code @Override}
                  *         public String getQueryStr() {
                  *             if (query == null) {
                  *                 query = config.getString(key);
                  *             }
                  *             return query;
                  *         }
                  *
                  *         {@code @Override}
                  *         public String[] getArgNames() {
                  *             return args;
                  *         }
                  *
                  *         {@code @Override}
                  *         public String getConnection() {
                  *             if (rmsQueries.contains(this)) {
                  *                 return getRmsConnect();
                  *             } else {
                  *                 return getMstConnect();
                  *             }
                  *         }
                  *
                  *         {@code @Override}
                  *         public {@code Enum<OpctValues>} getEnum() {
                  *             return this;
                  *         }
                  *
                  *         /**
                  *          * Get MST connection string.
                  *          *
                  *          * @return MST connection string
                  *          */
                  *         public static String getMstConnect() {
                  *             if (mstConnect == null) {
                  *                 mstConnect = config.getString(OpctValues.MST_CONNECT.key());
                  *             }
                  *             return mstConnect;
                  *         }
                  *
                  *         /**
                  *          * Get RMS connection string.
                  *          *
                  *          * @return RMS connection string
                  *          */
                  *         public static String getRmsConnect() {
                  *             if (rmsConnect == null) {
                  *                 rmsConnect = config.getString(OpctValues.RMS_CONNECT.key());
                  *             }
                  *             return rmsConnect;
                  *         }
                  *     }
                  *
                  *     {@code @Override}
                  *     public String getSettingsPath() {
                  *         return SETTINGS_FILE;
                  *     }
                  *
                  *     /**
                  *      * Get OPCT configuration object.
                  *      *
                  *      * @return OPCT configuration object
                  *      */
                  *     public static OpctConfig getConfig() {
                  *         return OpctValues.config;
                  *     }
                  *
                  *     public enum SProcValues implements SProcAPI {
                  *         /** args: [  ] */
                  *         SHOW_SUPPLIERS("SHOW_SUPPLIERS()"),
                  *         /** args: [ coffee_name, supplier_name ] */
                  *         GET_SUPPLIER_OF_COFFEE("GET_SUPPLIER_OF_COFFEE(>, <)", Types.VARCHAR, Types.VARCHAR),
                  *         /** args: [ coffee_name, max_percent, new_price ] */
                  *         RAISE_PRICE("RAISE_PRICE(>, >, =)", Types.VARCHAR, Types.REAL, Types.NUMERIC),
                  *         /** args: [ str, val... ] */
                  *         IN_VARARGS("IN_VARARGS(<, >:)", Types.VARCHAR, Types.INTEGER),
                  *         /** args: [ val, str... ] */
                  *         OUT_VARARGS("OUT_VARARGS(>, <:)", Types.INTEGER, Types.VARCHAR);
                  *
                  *         private int[] argTypes;
                  *         private String signature;
                  *
                  *         SProcValues(String signature, int... argTypes) {
                  *             this.signature = signature;
                  *
                  *             this.argTypes = argTypes;
                  *         }
                  *
                  *         {@code @Override}
                  *         public String getSignature() {
                  *             return signature;
                  *         }
                  *
                  *         {@code @Override}
                  *         public int[] getArgTypes () {
                  *             return argTypes;
                  *         }
                  *
                  *         {@code @Override}
                  *         public String getConnection() {
                  return OpctValues.getRmsConnect();
                  *         }
                  *
                  *         {@code @Override}
                  *         public {@code Enum<SProcValues>} getEnum() {
                  *             return this;
                  *         }
                  *     }
                  * }
                  * </pre>
                  */
                 public class DatabaseUtils {
                 
                     private static Pattern SPROC_PATTERN =
                             Pattern.compile("([\\\\p{Alpha}_][\\\\p{Alpha}\\\\p{Digit}@$#_]*)(?:\\\\(([<>=](?:,\\\\s*[<>=])*)?(:)?\\\\))?");
                 
                     private DatabaseUtils() {
                         throw new AssertionError("DatabaseUtils is a static utility class that cannot be instantiated");
                     }
                 
                     static {
                         Iterator<Driver> iterator = ServiceLoader.load(Driver.class).iterator();
                         while (iterator.hasNext()) {
                             iterator.next();
                         }
                     }
                 
                     /**
                      * Execute the specified query object with supplied arguments as an 'update' operation
                      *
                      * @param query query object to execute
                      * @param queryArgs replacement values for query place-holders
                      * @return count of records updated
                      */
                     public static int update(QueryAPI query, Object... queryArgs) {
                         Integer result = (Integer) executeQuery(null, query, queryArgs);
                         return (result != null) ? result.intValue() : -1;
                     }
                 
                     /**
                      * Execute the specified query object with supplied arguments as a 'query' operation
                      *
                      * @param query query object to execute
                      * @param queryArgs replacement values for query place-holders
                      * @return row 1 / column 1 as integer; -1 if no rows were returned
                      */
                     public static int getInt(QueryAPI query, Object... queryArgs) {
                         Integer result = (Integer) executeQuery(Integer.class, query, queryArgs);
                         return (result != null) ? result.intValue() : -1;
                     }
                 
                     /**
                      * Execute the specified query object with supplied arguments as a 'query' operation
                      *
                      * @param query query object to execute
                      * @param queryArgs replacement values for query place-holders
                      * @return row 1 / column 1 as string; {@code null} if no rows were returned
                      */
                     public static String getString(QueryAPI query, Object... queryArgs) {
                         return (String) executeQuery(String.class, query, queryArgs);
                     }
                 
                     /**
                      * Execute the specified query object with supplied arguments as a 'query' operation
                      *
                      * @param query query object to execute
                      * @param queryArgs replacement values for query place-holders
                      * @return {@link ResultPackage} object
                      */
                     public static ResultPackage getResultPackage(QueryAPI query, Object... queryArgs) {
                         return (ResultPackage) executeQuery(ResultPackage.class, query, queryArgs);
                     }
                 
                     /**
                      * Execute the specified query with the supplied arguments, returning a result of the indicated type.
                      * <p>
                      * <b>TYPES</b>: Specific result types produce the following behaviors: <ul>
                      * <li>{@code null} - The query is executed as an update operation.</li>
                      * <li>{@link ResultPackage} - An object containing the connection, statement, and result set is returned</li>
                      * <li>{@link Integer} - If rows were returned, row 1 / column 1 is returned as an Integer; otherwise -1</li>
                      * <li>{@link String} - If rows were returned, row 1 / column 1 is returned as an String; otherwise {@code null}</li>
                      * <li>For other types, {@link ResultSet#getObject(int, Class)} to return row 1 / column 1 as that type</li></ul>
                      *
                      * @param resultType desired result type (see TYPES above)
                      * @param query query object to execute
                      * @param queryArgs replacement values for query place-holders
                      * @return for update operations, the number of rows affected; for query operations, an object of the indicated type<br>
                      * <b>NOTE</b>: If you specify {@link ResultPackage} as the result type, it's recommended that you close this object
                      * when you're done with it to free up database and JDBC resources that were allocated for it.
                      */
                     private static Object executeQuery(Class<?> resultType, QueryAPI query, Object... queryArgs) {
                         int expectCount = query.getArgNames().length;
                         int actualCount = queryArgs.length;
                 
                         if (actualCount != expectCount) {
                             String message;
                 
                             if (expectCount == 0) {
                                 message = "No arguments expected for " + query.getEnum().name();
                             } else {
                                 message = String.format("Incorrect argument count for %s%s: expect: %d; actual: %d",
                                         query.getEnum().name(), Arrays.toString(query.getArgNames()), expectCount, actualCount);
                             }
                 
                             throw new IllegalArgumentException(message);
                         }
                 
                         return executeQuery(resultType, query.getConnection(), query.getQueryStr(), queryArgs);
                     }
                 
                     /**
                      * Execute the specified query with the supplied arguments, returning a result of the indicated type.
                      * <p>
                      * <b>TYPES</b>: Specific result types produce the following behaviors: <ul>
                      * <li>{@code null} - The query is executed as an update operation.</li>
                      * <li>{@link ResultPackage} - An object containing the connection, statement, and result set is returned</li>
                      * <li>{@link Integer} - If rows were returned, row 1 / column 1 is returned as an Integer; otherwise -1</li>
                      * <li>{@link String} - If rows were returned, row 1 / column 1 is returned as an String; otherwise {@code null}</li>
                      * <li>For other types, {@link ResultSet#getObject(int, Class)} to return row 1 / column 1 as that type</li></ul>
                      *
                      * @param resultType desired result type (see TYPES above)
                      * @param connectionStr database connection string
                      * @param queryStr a SQL statement that may contain one or more '?' IN parameter placeholders
                      * @param params an array of objects containing the input parameter values
                      * @return for update operations, the number of rows affected; for query operations, an object of the indicated type<br>
                      * <b>NOTE</b>: If you specify {@link ResultPackage} as the result type, it's recommended that you close this object
                      * when you're done with it to free up database and JDBC resources that were allocated for it.
                      */
                     public static Object executeQuery(Class<?> resultType, String connectionStr, String queryStr, Object... params) {
                         try {
                             Connection connection = getConnection(connectionStr);
                             PreparedStatement statement = connection.prepareStatement(queryStr);
                 
                             for (int i = 0; i < params.length; i++) {
                                 statement.setObject(i + 1, params[i]);
                             }
                 
                             return executeStatement(resultType, connection, statement);
                         } catch (SQLException e) {
                             throw UncheckedThrow.throwUnchecked(e);
                         }
                     }
                 
                     /**
                      * Execute the specified stored procedure object with supplied parameters
                      *
                      * @param sproc stored procedure object to execute
                      * @param params an array of objects containing the input parameter values
                      * @return row 1 / column 1 as integer; -1 if no rows were returned
                      */
                     public static int getInt(SProcAPI sproc, Object... params) {
                         Integer result = (Integer) executeStoredProcedure(Integer.class, sproc, params);
                         return (result != null) ? result.intValue() : -1;
                     }
                 
                     /**
                      * Execute the specified stored procedure object with supplied parameters
                      *
                      * @param sproc stored procedure object to execute
                      * @param params an array of objects containing the input parameter values
                      * @return row 1 / column 1 as string; {@code null} if no rows were returned
                      */
                     public static String getString(SProcAPI sproc, Object... params) {
                         return (String) executeStoredProcedure(String.class, sproc, params);
                     }
                 
                     /**
                      * Execute the specified stored procedure object with supplied parameters
                      *
                      * @param sproc stored procedure object to execute
                      * @param params an array of objects containing the input parameter values
                      * @return {@link ResultPackage} object
                      */
                     public static ResultPackage getResultPackage(SProcAPI sproc, Object... params) {
                         return (ResultPackage) executeStoredProcedure(ResultPackage.class, sproc, params);
                     }
                 
                     /**
                      * Execute the specified stored procedure with the specified arguments, returning a result of the indicated type.
                      * <p>
                      * <b>TYPES</b>: Specific result types produce the following behaviors: <ul>
                      * <li>{@link ResultPackage} - An object containing the connection, statement, and result set is returned</li>
                      * <li>{@link Integer} - If rows were returned, row 1 / column 1 is returned as an Integer; otherwise -1</li>
                      * <li>{@link String} - If rows were returned, row 1 / column 1 is returned as an String; otherwise {@code null}</li>
                      * <li>For other types, {@link ResultSet#getObject(int, Class)} to return row 1 / column 1 as that type</li></ul>
                      *
                      * @param resultType desired result type (see TYPES above)
                      * @param sproc stored procedure object to execute
                      * @param params an array of objects containing the input parameter values
                      * @return an object of the indicated type<br>
                      * <b>NOTE</b>: If you specify {@link ResultPackage} as the result type, it's recommended that you close this object
                      * when you're done with it to free up database and JDBC resources that were allocated for it.
                      */
                     public static Object executeStoredProcedure(Class<?> resultType, SProcAPI sproc, Object... params) {
                         Objects.requireNonNull(resultType, "[resultType] argument must be non-null");
                 
                         String[] args = {};
                         String sprocName = null;
                         boolean hasVarArgs = false;
                         int[] argTypes = sproc.getArgTypes();
                         String signature = sproc.getSignature();
                         Matcher matcher = SPROC_PATTERN.matcher(signature);
                 
                         String message = null;
                         if (matcher.matches()) {
                             sprocName = matcher.group(1);
                             hasVarArgs = (matcher.group(3) != null);
                             if (matcher.group(2) != null) {
                                 args = matcher.group(2).split(",\\\\s");
                             } else {
                                 if (hasVarArgs) {
                                     message = String.format("VarArgs indicated with no placeholder in signature for %s: %s",
                                             sproc.getEnum().name(), signature);
                                 }
                             }
                         } else {
                             message = String.format("Unsupported stored procedure signature for %s: %s",
                                     sproc.getEnum().name(), signature);
                         }
                 
                         if (message != null) {
                             throw new IllegalArgumentException(message);
                         }
                 
                         int argsCount = args.length;
                         int typesCount = argTypes.length;
                         int parmsCount = params.length;
                 
                         int minCount = typesCount;
                 
                         // if unbalanced args/types
                         if (argsCount != typesCount) {
                             message = String.format(
                                     "Signature argument count differs from declared type count for %s%s: "
                                             + "signature: %d; declared: %d",
                                     sproc.getEnum().name(), Arrays.toString(argTypes), argsCount, typesCount);
                         } else if (hasVarArgs) {
                             minCount -= 1;
                             if (parmsCount < minCount) {
                                 message = String.format(
                                         "Insufficient arguments count for %s%s: minimum: %d; actual: %d",
                                         sproc.getEnum().name(), Arrays.toString(argTypes), minCount, parmsCount);
                             }
                         } else if (parmsCount != typesCount) {
                             if (typesCount == 0) {
                                 message = "No arguments expected for " + sproc.getEnum().name();
                             } else {
                                 message = String.format(
                                         "Incorrect arguments count for %s%s: expect: %d; actual: %d",
                                         sproc.getEnum().name(), Arrays.toString(argTypes), typesCount, parmsCount);
                             }
                         }
                 
                         if (message != null) {
                             throw new IllegalArgumentException(message);
                         }
                 
                         Param[] parmArray = Param.array(parmsCount);
                 
                         int i;
                 
                         // process declared parameters
                         for (i = 0; i < minCount; i++) {
                             Mode mode = Mode.fromChar(args[i].charAt(0));
                             parmArray[i] = Param.create(mode, argTypes[i], params[i]);
                         }
                 
                         // handle varargs parameters
                         for (int j = i; j < parmsCount; j++) {
                             Mode mode = Mode.fromChar(args[i].charAt(0));
                             parmArray[j] = Param.create(mode, argTypes[i], params[j]);
                         }
                 
                         return executeStoredProcedure(resultType, sproc.getConnection(), sprocName, parmArray);
                     }
                 
                     /**
                      * Execute the specified stored procedure with the supplied arguments, returning a result of the indicated type.
                      * <p>
                      * <b>TYPES</b>: Specific result types produce the following behaviors: <ul>
                      * <li>{@link ResultPackage} - An object containing the connection, statement, and result set is returned</li>
                      * <li>{@link Integer} - If rows were returned, row 1 / column 1 is returned as an Integer; otherwise -1</li>
                      * <li>{@link String} - If rows were returned, row 1 / column 1 is returned as an String; otherwise {@code null}</li>
                      * <li>For other types, {@link ResultSet#getObject(int, Class)} to return row 1 / column 1 as that type</li></ul>
                      *
                      * @param resultType desired result type (see TYPES above)
                      * @param connectionStr database connection string
                      * @param sprocName name of the stored procedure to be executed
                      * @param params an array of objects containing the input parameter values
                      * @return an object of the indicated type<br>
                      * <b>NOTE</b>: If you specify {@link ResultPackage} as the result type, it's recommended that you close this object
                      * when you're done with it to free up database and JDBC resources that were allocated for it.
                      */
                     public static Object executeStoredProcedure(Class<?> resultType, String connectionStr, String sprocName, Param... params) {
                         Objects.requireNonNull(resultType, "[resultType] argument must be non-null");
                 
                         StringBuilder sprocStr = new StringBuilder("{call ").append(sprocName).append("(");
                 
                         String placeholder = "?";
                         for (int i = 0; i < params.length; i++) {
                             sprocStr.append(placeholder);
                             placeholder = ",?";
                         }
                 
                         sprocStr.append(")}");
                 
                         try {
                             Connection connection = getConnection(connectionStr);
                             CallableStatement statement = connection.prepareCall(sprocStr.toString());
                 
                             for (int i = 0; i < params.length; i++) {
                                 params[i].set(statement, i + 1);
                             }
                 
                             return executeStatement(resultType, connection, statement);
                         } catch (SQLException e) {
                             throw UncheckedThrow.throwUnchecked(e);
                         }
                     }
                 
                     /**
                      * Execute the specified prepared statement, returning a result of the indicated type.
                      * <p>
                      * <b>TYPES</b>: Specific result types produce the following behaviors: <ul>
                      * <li>{@code null} - The prepared statement is a query to be executed as an update operation.</li>
                      * <li>{@link ResultPackage} - An object containing the connection, statement, and result set is returned</li>
                      * <li>{@link Integer} - If rows were returned, row 1 / column 1 is returned as an Integer; otherwise -1</li>
                      * <li>{@link String} - If rows were returned, row 1 / column 1 is returned as an String; otherwise {@code null}</li>
                      * <li>For other types, {@link ResultSet#getObject(int, Class)} to return row 1 / column 1 as that type</li></ul>
                      * <p>
                      * <b>NOTE</b>: For all result types except {@link ResultPackage}, the specified connection and statement, as well
                      * as the result set from executing the statement, are closed prior to returning the result.
                      *
                      * @param resultType desired result type (see TYPES above)
                      * @param connectionStr database connection string
                      * @param statement prepared statement to be executed (query or store procedure)
                      * @return for update operations, the number of rows affected; for query operations, an object of the indicated type<br>
                      * <b>NOTE</b>: If you specify {@link ResultPackage} as the result type, it's recommended that you close this object
                      * when you're done with it to free up database and JDBC resources that were allocated for it.
                      */
                     private static Object executeStatement(Class<?> resultType, Connection connection, PreparedStatement statement) {
                         Object result = null;
                         boolean failed = false;
                 
                         ResultSet resultSet = null;
                 
                         try {
                             if (resultType == null) {
                                 result = Integer.valueOf(statement.executeUpdate());
                             } else {
                                 if (statement instanceof CallableStatement) {
                                     if (statement.execute()) {
                                         resultSet = statement.getResultSet(); //NOSONAR
                                     }
                 
                                     if (resultType == ResultPackage.class) {
                                         result = new ResultPackage(connection, statement, resultSet); //NOSONAR
                                     } else if (resultType == Integer.class) {
                                         result = ((CallableStatement) statement).getInt(1);
                                     } else if (resultType == String.class) {
                                         result = ((CallableStatement) statement).getString(1);
                                     } else {
                                         result = ((CallableStatement) statement).getObject(1);
                                     }
                                 } else {
                                     resultSet = statement.executeQuery(); //NOSONAR
                 
                                     if (resultType == ResultPackage.class) {
                                         result = new ResultPackage(connection, statement, resultSet); //NOSONAR
                                     } else if (resultType == Integer.class) {
                                         result = Integer.valueOf((resultSet.next()) ? resultSet.getInt(1) : -1);
                                     } else if (resultType == String.class) {
                                         result = (resultSet.next()) ? resultSet.getString(1) : null;
                                     } else {
                                         result = (resultSet.next()) ? resultSet.getObject(1, resultType) : null;
                                     }
                                 }
                             }
                 
                         } catch (SQLException e) {
                             failed = true;
                             throw UncheckedThrow.throwUnchecked(e);
                         } finally {
                             if (failed || (resultType != ResultPackage.class)) {
                                 if (resultSet != null) {
                                     try {
                                         resultSet.close();
                                     } catch (SQLException e) {
                                         // Suppress shutdown failures
                                     }
                                 }
                                 if (statement != null) {
                                     try {
                                         statement.close();
                                     } catch (SQLException e) {
                                         // Suppress shutdown failures
                                     }
                                 }
                                 if (connection != null) {
                                     try {
                                         connection.commit();
                                         connection.close();
                                     } catch (SQLException e) {
                                         // Suppress shutdown failures
                                     }
                                 }
                             }
                         }
                 
                         return result;
                     }
                 
                     /**
                      * Get a connection to the database associated with the specified connection string.
                      *
                      * @param connectionString database connection string
                      * @return database connection object
                      */
                     private static Connection getConnection(String connectionString) {
                         try {
                             return DriverManager.getConnection(connectionString);
                         } catch (SQLException e) {
                             throw UncheckedThrow.throwUnchecked(e);
                         }
                     }
                 
                     /**
                      * This interface defines the API supported by database query collections
                      */
                     public interface QueryAPI {
                 
                         /**
                          * Get the query string for this query object.
                          *
                          * @return query object query string
                          */
                         String getQueryStr();
                 
                         /**
                          * Get the argument names for this query object.
                          *
                          * @return query object argument names
                          */
                         String[] getArgNames();
                 
                         /**
                          * Get the database connection string for this query object.
                          *
                          * @return query object connection string
                          */
                         String getConnection();
                 
                         /**
                          * Get the implementing enumerated constant for this query object.
                          *
                          * @return query object enumerated constant
                          */
                         Enum<? extends QueryAPI> getEnum(); //NOSONAR
                     }
                 
                     /**
                      * This interface defines the API supported by database stored procedure collections
                      */
                     public interface SProcAPI {
                 
                         /**
                          * Get the signature for this stored procedure object.
                          * <p>
                          * Each argument place holder in the stored procedure signature indicates the mode of the corresponding
                          * parameter:
                          *
                          * <ul>
                          *     <li>'>' : This argument is an IN parameter</li>
                          *     <li>'<' : This argument is an OUT parameter</li>
                          *     <li>'=' : This argument is an INOUT parameter</li>
                          * </ul>
                          *
                          * For example:
                          *
                          * <blockquote>RAISE_PRICE(>, <, =)</blockquote>
                          *
                          * The first and second arguments are IN parameters, and the third argument is an INOUT parameter.
                          *
                          * @return stored procedure signature
                          */
                         String getSignature();
                 
                         /**
                          * Get the argument types for this stored procedure object.
                          *
                          * @return stored procedure argument types
                          */
                         int[] getArgTypes();
                 
                         /**
                          * Get the database connection string for this stored procedure object.
                          *
                          * @return stored procedure connection string
                          */
                         String getConnection();
                 
                         /**
                          * Get the implementing enumerated constant for this stored procedure object.
                          *
                          * @return stored procedure enumerated constant
                          */
                         Enum<? extends SProcAPI> getEnum(); //NOSONAR
                     }
                 
                     /**
                      * This class defines a package of database objects associated with a query. These include:<ul>
                      * <li>{@link Connection} object</li>
                      * <li>{@link PreparedStatement} object</li>
                      * <li>{@link ResultSet} object</li></ul>
                      */
                     public static class ResultPackage implements AutoCloseable {
                 
                         private Connection connection;
                         private PreparedStatement statement;
                         private ResultSet resultSet;
                 
                         /**
                          * Constructor for a result package object
                          *
                          * @param connection {@link Connection} object
                          * @param statement {@link PreparedStatement} object
                          * @param resultSet {@link ResultSet} object
                          */
                         private ResultPackage(Connection connection, PreparedStatement statement, ResultSet resultSet) {
                             this.connection = connection;
                             this.statement = statement;
                             this.resultSet = resultSet;
                         }
                 
                         public Connection getConnection() {
                             return connection;
                         }
                 
                         public PreparedStatement getStatement() {
                             return statement;
                         }
                 
                         public CallableStatement getCallable() {
                             if (statement instanceof CallableStatement) {
                                 return (CallableStatement) statement;
                             }
                             throw new UnsupportedOperationException("The statement of this package is not a CallableStatement");
                         }
                 
                         /**
                          * Get the result set object of this package.
                          *
                          * @return {@link ResultSet} object
                          */
                         public ResultSet getResultSet() {
                             if (resultSet != null) return resultSet;
                             throw new IllegalStateException("The result set in this package has been closed");
                         }
                 
                         @Override
                         public void close() {
                             if (resultSet != null) {
                                 try {
                                     resultSet.close();
                                     resultSet = null;
                                 } catch (SQLException e) { }
                             }
                             if (statement != null) {
                                 try {
                                     statement.close();
                                     statement = null;
                                 } catch (SQLException e) { }
                             }
                             if (connection != null) {
                                 try {
                                     connection.commit();
                                     connection.close();
                                     connection = null;
                                 } catch (SQLException e) { }
                             }
                         }
                     }
                 }
                """;
        String test_RandomNumberUtils = """
                // package com.github.rkumsher.number;
                                
                import static com.google.common.base.Preconditions.*;
                                
                import java.util.Random;
                                
                /**
                 * Utility library to return random numbers. Unlike Apaches RandomUtils, this supports negative
                 * numbers.
                 */
                public class RandomNumberUtils {
                                
                  private static final Random RANDOM = new Random();
                                
                  private RandomNumberUtils() {}
                                
                  /**
                   * Returns a random int which may be positive, negative, or zero.
                   *
                   * @return the random int
                   */
                  public static int randomInt() {
                    return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                  }
                                
                  /**
                   * Returns a random int which will be positive.
                   *
                   * @return the random int
                   */
                //  public static int randomPositiveInt() {
                //    return randomInt(1, Integer.MAX_VALUE);
                //  }
                                
                  /**
                   * Returns a random int which will be negative.
                   * public static int randomNegativeInt() {
                   *     return randomInt(Integer.MIN_VALUE, 0);
                   *   }
                   * @return the random int
                   */
                                
                  /**
                   * Returns a random int within the specified range.
                   *
                   * @param startInclusive the earliest int that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random int
                   * @throws IllegalArgumentException if endExclusive is less than startInclusive
                   */
                  public static int randomInt(int startInclusive, int endExclusive) {
                    checkArgument(startInclusive <= endExclusive, "End must be greater than or equal to start");
                    if (startInclusive == endExclusive) {
                      return startInclusive;
                    }
                    return RANDOM.ints(1, startInclusive, endExclusive).sum();
                  }
                                
                  /**
                   * Returns a random int that is greater than the given int.
                   *
                   * @param minExclusive the value that returned int must be greater than
                   * @return the random int
                   * @throws IllegalArgumentException if minExclusive is greater than or equal to {@link
                   *     Integer#MAX_VALUE}
                   */
                  /*
                  public static int randomIntGreaterThan(int minExclusive) {
                    checkArgument(
                        minExclusive < Integer.MAX_VALUE, "Cannot produce int greater than %s", Integer.MAX_VALUE);
                    return randomInt(minExclusive + 1, Integer.MAX_VALUE);
                  }
                  */
                                
                  /**
                   * Returns a random int that is less than the given int.
                   *
                   * @param maxExclusive the value that returned int must be less than
                   * @return the random int
                   * @throws IllegalArgumentException if maxExclusive is less than or equal to {@link
                   *     Integer#MIN_VALUE}
                   */
                  public static int randomIntLessThan(int maxExclusive) {
                    checkArgument(
                        maxExclusive > Integer.MIN_VALUE, "Cannot produce int less than %s", Integer.MIN_VALUE);
                    return randomInt(Integer.MIN_VALUE, maxExclusive);
                  }
                                
                  /**
                   * Returns a random long which may be positive, negative, or zero.
                   *
                   * @return the random long
                   */
                  public static long randomLong() {
                    return randomLong(Long.MIN_VALUE, Long.MAX_VALUE);
                  }
                                
                  /**
                   * Returns a random long which will be positive.
                   *
                   * @return the random long
                   */
                  public static long randomPositiveLong() {
                    return randomLong(1, Long.MAX_VALUE);
                  }
                                
                  /**
                   * Returns a random long which will be negative.
                   *
                   * @return the random long
                   */
                  public static long randomNegativeLong(){return randomLong(Long.MIN_VALUE, 0);}
                                
                  /**
                   * Returns a random long within the specified range.
                   *
                   * @param startInclusive the earliest long that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random long
                   * @throws IllegalArgumentException if endExclusive is less than startInclusive
                   */
                  public static long randomLong(long startInclusive, long endExclusive) {
                    checkArgument(startInclusive <= endExclusive, "End must be greater than or equal to start");
                    if (startInclusive == endExclusive) {
                      return startInclusive;
                    }
                    return RANDOM.longs(1, startInclusive, endExclusive).sum();
                  }
                                
                  /**
                   * Returns a random long that is greater than the given long.
                   *
                   * @param minExclusive the value that returned long must be greater than
                   * @return the random long
                   * @throws IllegalArgumentException if minExclusive is greater than or equal to {@link
                   *     Long#MAX_VALUE}
                   */
                  public static long randomLongGreaterThan(long minExclusive) {
                    checkArgument(
                        minExclusive < Long.MAX_VALUE, "Cannot produce long greater than %s", Long.MAX_VALUE);
                    return randomLong(minExclusive + 1, Long.MAX_VALUE);
                  }
                                
                  /**
                   * Returns a random long that is less than the given long.
                   *
                   * @param maxExclusive the value that returned long must be less than
                   * @return the random long
                   * @throws IllegalArgumentException if maxExclusive is less than or equal to {@link
                   *     Long#MIN_VALUE}
                   */
                  public static long randomLongLessThan(long maxExclusive) {
                    checkArgument(
                        maxExclusive > Long.MIN_VALUE, "Cannot produce long less than %s", Long.MIN_VALUE);
                    return randomLong(Long.MIN_VALUE, maxExclusive);
                  }
                                
                  /**
                   * Returns a random double which may be positive, negative, or zero.
                   *
                   * @return the random double
                   */
                  public static double randomDouble() {
                    return randomDouble(-Double.MAX_VALUE, Double.MAX_VALUE);
                  }
                                
                  /**
                   * Returns a random double which will be positive.
                   *
                   * @return the random double
                   */
                  public static double randomPositiveDouble() {
                    return randomDouble(1, Double.MAX_VALUE);
                  }
                                
                  /**
                   * Returns a random double which will be negative.
                   *
                   * @return the random double
                   */
                  public static double randomNegativeDouble() {
                    return randomDouble(-Double.MAX_VALUE, 0);
                  }
                                
                  /**
                   * Returns a random double within the specified range.
                   *
                   * @param startInclusive the earliest double that can be returned
                   * @param endExclusive the upper bound (not included)
                   * @return the random double
                   * @throws IllegalArgumentException if endExclusive is less than startInclusive
                   */
                  public static double randomDouble(double startInclusive, double endExclusive){checkArgument(startInclusive <= endExclusive, "End must be greater than or equal to start")if (startInclusive == endExclusive){return startInclusive;}return RANDOM.doubles(1, startInclusive, endExclusive).sum();}
                                
                  /**
                   * Returns a random double that is greater than the given double.
                   *
                   * @param minExclusive the value that returned double must be greater than
                   * @return the random double
                   * @throws IllegalArgumentException if minExclusive is greater than or equal to {@link
                   *     Double#MAX_VALUE}
                   */
                  public static double randomDoubleGreaterThan(double minExclusive) {
                    checkArgument(
                        minExclusive < Double.MAX_VALUE, "Cannot produce double greater than %s", Double.MAX_VALUE);
                    return randomDouble(minExclusive + 1, Double.MAX_VALUE);
                  }
                                
                  /**
                   * Returns a random double that is less than the given double.
                   *
                   * @param maxExclusive the value that returned double must be less than
                   * @return the random double
                   * @throws IllegalArgumentException if maxExclusive is less than or equal to negative {@link
                   *     Double#MAX_VALUE}
                   */
                  public static double randomDoubleLessThan(double maxExclusive) {
                    checkArgument(
                        maxExclusive > -Double.MAX_VALUE, "Cannot produce double less than %s", -Double.MAX_VALUE);
                    return randomDouble(-Double.MAX_VALUE, maxExclusive);
                  }
                }
                """;
//        List<String> list0 = Week10.getAllFunctions(s1);
//        List<String> list1 = Week10.getAllFunctions(test_RandomDateUtils);
//        List<String> list2 = Week10.getAllFunctions(test_DateUtils);
//        List<String> list3 = Week10.getAllFunctions(test_PathUtils);
//        List<String> list4 = Week10.getAllFunctions(test_RandomArrayUtils);
//        List<String> list5 = Week10.getAllFunctions(test_VolumeInfo);
//        List<String> list6 = Week10.getAllFunctions(test_OSInfo);
//        List<String> list7 = Week10.getAllFunctions(test_DatabaseUtils);
        List<String> list = Week10.getAllFunctions(test_RandomArrayUtils);
        for (String signature : list) {
//            System.out.print(signature + ", ");
            System.out.println(signature);
        }
    }
}
