package kr.co.queenssmile.core.utils;


import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * 특정 형식으로 현재 일자 반환 기능.
 * 입력 날짜 형식 검사 기능.
 * 현재 기준, 이전/이후 시간 반환 기능.
 * 기간 계산 기능.
 * 양력/음력 날짜 변환, 윤년 확인 기능.
 * 특정 기간의 종료 일자 및 요일 반환 기능.
 */
@Slf4j
public class DateUtils {
    public static final String FORMAT_DATE_TIME = "yyyyMMddHHmmss";
    public static final String FORMAT_DATE = "yyyyMMdd";
    public static final String FORMAT_TIME = "HHmmss";

    public static final String FORMAT_DATE_TIME_UNIT = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_DATE_UNIT = "yyyy/MM/dd";
    public static final String FORMAT_MONTH_DATE_UNIT = "MM/dd";
    public static final String FORMAT_TIME_UNIT = "HH:mm:ss";

    public static final String FORMAT_DATE_MIN_UNIT_BAR = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE_TIME_UNIT_BAR = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_UNIT_BAR = "yyyy-MM-dd";

    public static final String FORMAT_HOUR_MINUTE_UNIT_BAR = "HH:mm";
    public static final String FORMAT_MONTH_DAY_UNIT_BAR = "MM-dd";

    public static Date getDateToFormat(String dateStr, final String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getFormatStrToDate(Date date, final String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getFormatStrToLocalDate(LocalDate localDate, final String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDate);
    }

    public static String getFormatStrToLocalDateTime(LocalDateTime dateTime, final String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(dateTime);
    }

    public static Date addDay(Date date, int day) {
        long dayValue = (1000 * 60 * 60 * 24) * day;
        return new Date(date.getTime() + dayValue);
    }

    public static Date addOneDay(Date date) {
        return addDay(date, 1);
    }

    // 캘린더 비교
    public static String calendarCompare(Calendar a, Calendar b) {
        if (a.compareTo(b) > 0) {
            return "after"; // a가 b보다 이후 날짜이다.
        } else if (a.compareTo(b) < 0) {
            return "before"; // a가 b보다 이전 날짜이다.
        } else {
            return "equal"; // 날짜가 같습니다.
        }
    }

    /**
     * 현재 날짜 반환
     */

    // 현재 날짜를 java.time.LocalDate 타입으로 반환한다.
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    // 현재 날짜를 java.time.LocalDateTime 타입으로 반환한다.
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    // 현재 날짜를 java.time.LocalTime 타입으로 반환한다.
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    // 현재 날짜를 java.utils.Date 타입으로 반환한다.
    public static Date getDate() {
        return new Date();
    }

    //현재 날짜를 'yyyyMMddHHmmss' 형식의 문자열로 변환한다.
    public static String getDateTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME);
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatter.format(localDateTime);
    }

    // 현재 날짜를 'yyyyMMdd' 형식의 문자열로 반환한다.
    public static String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatter.format(localDateTime);
    }

    //현재 시각을 'HHmmss' 형식의 문자열로 변환한다.
    public static String getTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TIME);
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatter.format(localDateTime);
    }

    // 현재 날짜를 입력 포맷의 문자열로 반환한다.
    public static String getTodayString(String dataPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dataPattern);
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatter.format(localDateTime);
    }

    /**
     * 입력 날짜/시각을 문자열 타입으로 변환
     */

    // java.util.Date 타입 객체를 'yyyyMMdd' 형식의 문자열로 변환한다.
    public static String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        return sdf.format(date);
    }

    //java.util.Date 객체를 'HHmmss' 형식의 문자열로 변환한다.
    public static String getTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        return sdf.format(date);
    }

    //java.util.Date 객체를 'yyyyMMddHHmmss' 형식의 문자열로 변환한다.
    public static String getDateTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
        return sdf.format(date);
    }

    //LocalDateTime 타입을 'yyyyMMddHHmmss' 형식의 문자열로 변환한다.
    public static String getDateTimeString(LocalDateTime date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
        return sdf.format(toDate(date));
    }


    /**
     * 문자열/Date 형식의 날짜(시간)을 Date/Timestamp 타입으로 변환
     */
    // 날짜(+시간) 형식의 문자열을 java.util.date 타입으로 변환한다.
    public static Date toDate(String from) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
        return sdf.parse(from);
    }

    // 날짜(+시간) 형식의 문자열을 java.time.LocalDateTime 타입으로 변환한다.
    public static LocalDateTime toLocalDateTime(String from) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME_UNIT);
        return LocalDateTime.parse(from, formatter); // yyyy/MM/dd HH:mm:ss
    }

    // 날짜(+시간) 형식의 문자열을 java.time.LocalDate 타입으로 변환한다.
    public static LocalDate toLocalDate(String from) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_UNIT);
        return LocalDate.parse(from, formatter); // yyyy/MM/dd
    }

    // 날짜(+시간) 형식의 문자열을 java.time.LocalTime 타입으로 변환한다.
    public static LocalTime toLocalTime(String from) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TIME_UNIT);
        return LocalTime.parse(from, formatter); // HH:mm:ss
    }

    public static LocalDate toLocalDate(String from, String format) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(from, formatter); // yyyy/MM/dd
    }

    /**
     * Date 형식의 날짜(시간)을 LocalDate/LocalDateTime 타입으로 변환
     */
    // java.util.Date 객체를 java.time.LocalDateTime 타입으로 변환한다.
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // java.util.Date 객체를 java.time.LocalDate 타입으로 변환한다.
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // java.util.Date 객체를 java.time.LocalTime 타입으로 변환한다.
    public static LocalTime toLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * LocalDate/LocalDateTime 형식의 날짜(시간)을 Date 타입으로 변환
     */
    // java.time.LocalDate 객체를 java.util.Date 타입으로 변환한다.
    public static Date toDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    // java.time.localDateTime 객체를 java.util.Date 타입으로 변환한다.
    public static Date toDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    /**
     * 날짜/시간 데이터 유효성 검사
     */
    //날짜(혹은 시간)입력 데이터의 형식이 유효한지 검사한다
    public static boolean isValid(String value, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null;
    }

    /**
     * 기간 계산
     */
    //LocalDateTime 타입의 날짜를 입력 받아, 시작/종료 일자 간의 시간 차이를 반환한다.
    public static int getNumberOfTime(LocalDateTime begin, LocalDateTime end) {
        return (int) ChronoUnit.SECONDS.between(begin, end);
    }

    //LocalDateTime 타입의 날짜를 입력 받아, 시작/종료 일자 간의 일 수 차이를 반환한다.
    public static int getNumberOfDays(LocalDateTime begin, LocalDateTime end) {
        return (int) ChronoUnit.DAYS.between(begin, end);
    }

    //LocalDate 타입의 날짜를 입력 받아, 시작/종료 일자 간의 일 수 차이를 반환한다.
    public static int getNumberOfDays(LocalDate begin, LocalDate end) {
        return (int) ChronoUnit.DAYS.between(begin, end);
    }

    //LocalTime 타입의 날짜를 입력 받아, 시작/종료 일자 간의 일 수 차이를 반환한다.
    public static int getNumberOfDays(LocalTime begin, LocalTime end) {
        return (int) ChronoUnit.DAYS.between(begin, end);
    }

    //String 타입의 날짜를 입력 받아, 시작/종료 일자 간의 일 수 차이를 반환한다.
    public static int getNumberOfDays(String begin, String end) throws DateTimeParseException {
        LocalDate beginLocalDate = toLocalDate(begin);
        LocalDate endLocalDate = toLocalDate(end);
        return getNumberOfDays(beginLocalDate, endLocalDate);
    }

    //Date 타입의 날짜를 입력 받아, 시작/종료 일자 간의 일 수 차이를 반환한다.
    public static int getNumberOfDays(Date begin, Date end) {
        LocalDate beginLocalDate = toLocalDate(begin);
        LocalDate endLocalDate = toLocalDate(end);
        return getNumberOfDays(beginLocalDate, endLocalDate);
    }

    //LocalDateTime 타입의 시작/종료 년월을 입력 받아, 개월 수를 반환한다.
    public static int getNumberOfMonths(LocalDateTime begin, LocalDateTime end) {
        return (int) ChronoUnit.MONTHS.between(begin, end);
    }

    //LocalDate 타입의 시작/종료 년월을 입력 받아, 개월 수를 반환한다.
    public static int getNumberOfMonths(LocalDate begin, LocalDate end) {
        return (int) ChronoUnit.MONTHS.between(begin, end);
    }

    //LocalTime 타입의 시작/종료 년월을 입력 받아, 개월 수를 반환한다.
    public static int getNumberOfMonths(LocalTime begin, LocalTime end) {
        return (int) ChronoUnit.MONTHS.between(begin, end);
    }

    //문자열 타입의 시작/종료 년월을 입력 받아, 개월 수를 반환한다.
    public static int getNumberOfMonths(String begin, String end) {
        LocalDate beginLocalDate = toLocalDate(begin);
        LocalDate endLocalDate = toLocalDate(end);
        return getNumberOfMonths(beginLocalDate, endLocalDate);
    }

    //Date 타입의 날짜를 입력 받아, 시작/종료 일자 간의 일 수 차이를 반환한다.
    public static int getNumberOfMonths(Date begin, Date end) {
        LocalDate beginLocalDate = toLocalDate(begin);
        LocalDate endLocalDate = toLocalDate(end);
        return getNumberOfMonths(beginLocalDate, endLocalDate);
    }

    //LocalDateTime 타입의 시작/종료 일자를 입력 받아, 년 수 차이를 반환한다.
    public static int getNumberOfYears(LocalDateTime begin, LocalDateTime end) {
        return (int) ChronoUnit.YEARS.between(begin, end);
    }

    //LocalDate 타입의 시작/종료 일자를 입력 받아, 년 수 차이를 반환한다.
    public static int getNumberOfYears(LocalDate begin, LocalDate end) {
        return (int) ChronoUnit.YEARS.between(begin, end);
    }

    //LocalTime 타입의 시작/종료 일자를 입력 받아, 년 수 차이를 반환한다.
    public static int getNumberOfYears(LocalTime begin, LocalTime end) {
        return (int) ChronoUnit.YEARS.between(begin, end);
    }

    //Date 타입의 시작/종료 일자를 입력 받아, 년 수 차이를 반환한다.
    public static int getNumberOfYears(Date begin, Date end) {
        LocalDate beginLocalDate = toLocalDate(begin);
        LocalDate endLocalDate = toLocalDate(end);
        return getNumberOfYears(beginLocalDate, endLocalDate);
    }

    // 문자열 타입의 시작/종료 일자를 입력 받아, 일 수 차이를 반환한다. 24시간을 넘어서면 2일로 계산한다.
    public static long getNumberOfDaysAbove(String beginDate, String endDate) {
        return 0;
    }

    // Date 타입의 시작/종료 일자를 입력 받아, 일 수 차이를 반환한다. 24 시간을 넘어서면 2일로 계산한다.
    public static long getNumberOfDaysAbove(Date beginDate, Date endDate) {
        return 0;
    }

    // 문자열 타입의 시작/종료 일자를 입력 받아, 개월 수 차이를 반환한다. 1개월을 넘어서면 2개월로 계산한다.
    public static int getNumberOfMonthsAbove(String beginDate, String endDate) {
        return 0;
    }

    // Date 타입의 시작/종료 일자를 입력 받아, 개월 수 차이를 반환한다. 1개월을 넘어서면 2개월로 계산한다.
    public static int getNumberOfMonthsAbove(Date beginDate, Date endDate) {
        return 0;
    }

    // 문자열 타입의 시작/종료 일자를 입력 받아, 년 수 차이를 반환한다. 1년을 넘어서면 2년으로 계산한다.
    public static int getNumberOfYearsAbove(String beginDate, String endDate) {
        return 0;
    }

    // Date 타입의 시작/종료 일자를 입력 받아, 년 수 차이를 반환한다. 1년을 넘어서면 2년으로 계산한다.
    public static int getNumberOfYearsAbove(Date beginDate, Date endDate) {
        return 0;
    }

    /**
     * 날짜 비교
     */
    // String 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이전인지 검사한다.
    public static boolean before(String date1, String date2) {
        return getNumberOfDays(date1, date2) > 0;
    }

    // String 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이후인지 검사한다.
    public static boolean after(String date1, String date2) {
        return getNumberOfDays(date1, date2) < 0;
    }

    // Date 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이전인지 검사한다.
    public static boolean before(Date date1, Date date2) {
        return getNumberOfDays(date1, date2) > 0;
    }

    // Date 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이후인지 검사한다.
    public static boolean after(Date date1, Date date2) {
        return getNumberOfDays(date1, date2) < 0;
    }

    // LocalDateTime 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이전인지 검사한다.
    public static boolean before(LocalDateTime date1, LocalDateTime date2) {
        return getNumberOfDays(date1, date2) > 0;
    }

    // LocalDateTime 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이후인지 검사한다.
    public static boolean after(LocalDateTime date1, LocalDateTime date2) {
        return getNumberOfDays(date1, date2) < 0;
    }

    // LocalDate 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이전인지 검사한다.
    public static boolean before(LocalDate date1, LocalDate date2) {
        return getNumberOfDays(date1, date2) > 0;
    }

    // LocalDate 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이후인지 검사한다.
    public static boolean after(LocalDate date1, LocalDate date2) {
        return getNumberOfDays(date1, date2) < 0;
    }

    // LocalTime 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이전인지 검사한다.
    public static boolean before(LocalTime date1, LocalTime date2) {
        return getNumberOfDays(date1, date2) > 0;
    }

    // LocalTime 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이후인지 검사한다.
    public static boolean after(LocalTime date1, LocalTime date2) {
        return getNumberOfDays(date1, date2) < 0;
    }

    /**
     * 날짜/시간 비교
     */
    // LocalDateTime 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이전인지 검사한다.
    public static boolean beforeDateTime(LocalDateTime date1, LocalDateTime date2) {
        return getNumberOfTime(date1, date2) > 0;
    }

    // LocalDateTime 타입의 날짜를 입력 받아, 첫번째 날짜가 두번째 날짜 보다 이후인지 검사한다.
    public static boolean afterDateTime(LocalDateTime date1, LocalDateTime date2) {
        return getNumberOfTime(date1, date2) < 0;
    }

    /**
     * 양력/음력 변환
     */
    // String 타입의 양력 날짜를 입력 받아, Date 타입의 음력 날짜로 반환한다.
    public static Date toLunar(String date) {
        return null;
    }

    // Date 타입의 양력 날짜를 입력 받아, Date 타입의 음력 날짜로 변환한다.
    public static Date toLunar(Date date) {
        return null;
    }

    // String 타입의 음력 날짜를 입력 받아, Date 타입의 양력 날짜로 변환한다.
    public static Date toSolar(String date) {
        return null;
    }

    // Date 타입의 음력 날짜를 입력 받아, Date 타입의 양력 날짜로 변환한다.
    public static Date toSolar(Date date) {
        return null;
    }

    /**
     * Date 타입 변환
     */
    public static Date toSqlDate(Date date) {
        return null;
    }

    /**
     * 분기 계산
     */
    // 문자열 타입의 날짜를 입력 받아 분기를 반환한다.
    public static int getQuaterOfYear(String date) {
        return 0;
    }

    // Date 타입의 날짜를 입력 받아 분기를 반환한다.
    public static int getQuaterOfYear(Date date) {
        return 0;
    }

    /**
     * 윤년 여부 검사
     */
    // 입력된 년도가 윤년인지 판단한다.
    public static boolean isLeapYear(int year) {
        return false;
    }

    /**
     * 요일 관련 계산
     */
    // 문자열 타입의 날짜를 입력 받아, 해당 일자의 요일을 반환한다.
    public static int getWeekday(String date) {
        return 0;
    }

    // Date 타입의 날짜를 입력 받아, 해당 일자의 요일을 반환한다.
    public static int getWeekday(Date date) {
        return 0;
    }

    // 문자열 타입의 날짜를 입력 받아, 그 주의 일요일을 반환한다.
    public static Date getSunday(String date) {
        return null;
    }

    // Date 타입의 날짜를 입력 받아, 그 주의 일요일을 반환한다.
    public static Date getSunday(Date date) {
        return null;
    }

    // 문자열 타입의 날짜를 입력 받아, 그 주의 토요일을 반환한다.
    public static Date getSaturday(String date) {
        return null;
    }

    // Date 타입의 날짜를 입력 받아, 그 주의 토요일을 반환한다.
    public static Date getSaturday(Date date) {
        return null;
    }

    /**
     * 트랜잭션 시작 시간 반환
     */
    // 트랜잭션 시작 시간을 Timestamp 타입으로 반환한다.
    public static Timestamp getTxTimestamp() {
        return null;
    }

    /**
     * 달 추가
     */
    // int 달 수를 입력 받아 , 00 달후 날짜를 LocalDate 반환한다.
    public static LocalDateTime getLocalDateToAfterMonth(long months) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMonths(months);
    }

    public static Date getDateToAfterMonth(long afterMonths) {
        LocalDateTime now = LocalDateTime.now();
        return toDate(now.plusMonths(afterMonths));
    }

    public static Date getDateToAfterMonth(Date date, long afterMonths) {
        LocalDateTime now = toLocalDateTime(date);
        return toDate(now.plusMonths(afterMonths));
    }

    public static Date getDateToAfterDate(long afterMoths) {
        LocalDateTime now = LocalDateTime.now();
        return toDate(now.plusDays(afterMoths * 30));
    }

    public static Date getDateToAfterDate(Date date, long afterMoths) {
        LocalDateTime now = toLocalDateTime(date);
        return toDate(now.plusDays(afterMoths * 30));
    }

    public static int daysByYear(LocalDateTime localDateTime) {
        return DateUtils.daysByYear(localDateTime.toLocalDate());
    }

    public static int daysByYear(LocalDate localDate) {
        if (localDate.isLeapYear()) {
            return 366;
        } else {
            return 365;
        }
    }

    /**
     * TemporalAdjusters 사용
     */
    //=== 다음 해의 첫 날
    public static LocalDate getFirstDayOfNextYearToLocalDate(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfNextYear());
    }

    public static LocalDate getFirstDayOfNextYearToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfNextYear());
    }

    public static LocalDateTime getFirstDayOfNextYearToLocalDateTime(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfNextYear()).atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfNextYearToLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfNextYear()).with(LocalTime.MIDNIGHT);
    }

    //=== 다음 달의 첫 날
    public static LocalDate getFirstDayOfNextMonthToLocalDate(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfNextMonth());
    }

    public static LocalDate getFirstDayOfNextMonthToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfNextMonth());
    }

    public static LocalDateTime getFirstDayOfNextMonthToLocalDateTime(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfNextMonth()).atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfNextMonthToLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfNextMonth()).with(LocalTime.MIDNIGHT);
    }

    //=== 올 해의 첫 날
    public static LocalDate getFirstDayOfYearToLocalDate(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfYear());
    }

    public static LocalDate getFirstDayOfYearToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfYear());
    }

    public static LocalDateTime getFirstDayOfYearToLocalDateTime(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfYearToLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIDNIGHT);
    }

    //=== 이번 달의 첫 날
    public static LocalDate getFirstDayOfMonthToLocalDate(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getFirstDayOfMonthToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDateTime getFirstDayOfMonthToLocalDateTime(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfMonthToLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIDNIGHT);
    }

    //=== 올 해의 마지막 날
    public static LocalDate getLastDayOfYearToLocalDate(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.lastDayOfYear());
    }
    public static LocalDate getLastDayOfYearToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfYear());
    }
    public static LocalDateTime getLastDayOfYearToLocalDateTime(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.lastDayOfYear()).atStartOfDay();
    }
    public static LocalDateTime getLastDayOfYearToLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MIDNIGHT);
    }

    //=== 이번 달의 마지막 날
    public static LocalDate getLastDayOfMonthToLocalDate(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }
    public static LocalDate getLastDayOfMonthToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth());
    }
    public static LocalDateTime getLastDayOfMonthToLocalDateTime(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay();
    }
    public static LocalDateTime getLastDayOfMonthToLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MIDNIGHT);
    }

    //=== 이번 달의 첫 번째 ?요일
    public static LocalDate getFirstInMonthToLocalDate(LocalDate localDate, DayOfWeek dayOfWeek) {
        return localDate.with(TemporalAdjusters.firstInMonth(dayOfWeek));
    }

    public static LocalDateTime getFirstInMonthToLocalDateTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek) {
        return localDateTime.with(TemporalAdjusters.firstInMonth(dayOfWeek)).with(LocalTime.MIDNIGHT);
    }

    //=== 이번 달의 마지막 ?요일
    public static LocalDate getLastInMonthToLocalDate(LocalDate localDate, DayOfWeek dayOfWeek) {
        return localDate.with(TemporalAdjusters.lastInMonth(dayOfWeek));
    }

    public static LocalDateTime getLastInMonthToLocalDateTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek) {
        return localDateTime.with(TemporalAdjusters.lastInMonth(dayOfWeek)).with(LocalTime.MIDNIGHT);
    }

    //=== 지난 ?요일(당일 미포함)
    public static LocalDate getPreviousToLocalDate(LocalDate localDate, DayOfWeek dayOfWeek) {
        return localDate.with(TemporalAdjusters.previous(dayOfWeek));
    }

    public static LocalDateTime getPreviousToLocalDateTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek) {
        return localDateTime.with(TemporalAdjusters.previous(dayOfWeek)).with(LocalTime.MIDNIGHT);
    }

    //=== 지난 ?요일(당일 포함)
    public static LocalDate getPreviousOrSameToLocalDate(LocalDate localDate, DayOfWeek dayOfWeek) {
        return localDate.with(TemporalAdjusters.previousOrSame(dayOfWeek));
    }

    public static LocalDateTime getPreviousOrSameToLocalDateTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek) {
        return localDateTime.with(TemporalAdjusters.previousOrSame(dayOfWeek)).with(LocalTime.MIDNIGHT);
    }

    //=== 다음 ?요일(당일 미포함)
    public static LocalDate getNextToLocalDate(LocalDate localDate, DayOfWeek dayOfWeek) {
        return localDate.with(TemporalAdjusters.next(dayOfWeek));
    }

    public static LocalDateTime getNextToLocalDateTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek) {
        return localDateTime.with(TemporalAdjusters.next(dayOfWeek)).with(LocalTime.MIDNIGHT);
    }

    //=== 다음 ?요일(당일 포함)
    public static LocalDate getNextOrSameToLocalDate(LocalDate localDate, DayOfWeek dayOfWeek) {
        return localDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
    }

    public static LocalDateTime getNextOrSameToLocalDateTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek) {
        return localDateTime.with(TemporalAdjusters.nextOrSame(dayOfWeek)).with(LocalTime.MIDNIGHT);
    }

    //=== 이번 달의 n번째 ?요일
    public static LocalDate getDayOfWeekInMonthToLocalDate(LocalDate localDate, int ordinal, DayOfWeek dayOfWeek) {
        return localDate.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek));
    }

    public static LocalDateTime getDayOfWeekInMonthToLocalDateTime(LocalDateTime localDateTime, int ordinal, DayOfWeek dayOfWeek) {
        return localDateTime.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek)).with(LocalTime.MIDNIGHT);
    }

    //=== 날짜범위에 포함 여부
    public static boolean isWithinRange(LocalDateTime targetDate, LocalDateTime startDate, LocalDateTime endDate) {
        if (targetDate == null || startDate == null || endDate == null) {
            return false;
        }
        return !(targetDate.isBefore(startDate) || targetDate.isAfter(endDate));
    }
    public static boolean isWithinRange(LocalDate targetDate, LocalDate startDate, LocalDate endDate) {
        if (targetDate == null || startDate == null || endDate == null) {
            return false;
        }
        return !(targetDate.isBefore(startDate) || targetDate.isAfter(endDate));
    }
}
