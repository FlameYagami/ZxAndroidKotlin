package com.dab.zx.uc.charts.formatter;

import com.dab.zx.uc.charts.model.SubcolumnValue;

public interface ColumnChartValueFormatter {

    int formatChartValue(char[] formattedValue, SubcolumnValue value);

}
