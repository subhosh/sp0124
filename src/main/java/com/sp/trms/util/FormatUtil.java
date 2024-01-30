/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.sp.trms.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatUtil {
    //Getting the default locale i.e. en-US. Need to read this from a config file.
    private final static DecimalFormat currencyFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();

    //Percentage formatter with no decimal places
    private final static DecimalFormat percentFormat = new DecimalFormat("###%");

    private final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy");

    public static String formatCurrency(Float value) {
        return currencyFormat.format(value);
    }

    public static String formatPercent(Integer discountPercent) {
        return percentFormat.format(discountPercent / 100.0);
    }

    public static String formatDate(LocalDate date) {
        return dateFormat.format(date);
    }
}
