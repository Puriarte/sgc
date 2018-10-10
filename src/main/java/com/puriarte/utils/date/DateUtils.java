package com.puriarte.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puriarte.gcp.web.Constantes;


public class DateUtils {

	public static final String DATE_FORMAT_REGEX = Constantes.FORMATO_FECHA_REGEX;
	public static final String DATE_TIME_FORMAT_REGEX = Constantes.FORMATO_FECHA_HORA_REGEX;

	public static final String DATE_FORMAT = Constantes.FORMATO_FECHA;
	public static final String DATE_TIME_FORMAT = Constantes.FORMATO_FECHA_HORA;

	public static Date parseDate(String strDate, String formatoRegEx, String formatoFecha ) throws ParseException {

		if (checkFormatRegex(strDate, formatoRegEx)) {
			SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, new Locale("es", "ES"));
			return sdf.parse(strDate);

		} else {
			throw new ParseException(strDate, 0);
		}
	}

	public static Date parseDateTime(String strDate) throws ParseException {

		if (checkFormatRegex(strDate,DATE_TIME_FORMAT_REGEX)) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, new Locale("es", "ES"));
			return sdf.parse(strDate);

		} else {
			throw new ParseException(strDate, 0);
		}
	}


	private static boolean checkFormatRegex(String strDate, String formatRex) {
		Pattern pattern = Pattern.compile(formatRex);
		Matcher matcher = pattern.matcher(strDate);

		return matcher.matches();
	}

	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("es", "ES"));
		return sdf.format(date);
	}

	public static String formatDate(Date date, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato, new Locale("es", "ES"));
		return sdf.format(date);
	}

	public static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, new Locale("es", "ES"));
		return sdf.format(date);
	}

}
