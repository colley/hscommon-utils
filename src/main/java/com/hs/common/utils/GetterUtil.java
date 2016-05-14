/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */
package com.hs.common.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 公共方法 处理 字符串转数据，日期等
 *@FileName  GetterUtil.java
 *@Date  16-5-14 下午9:54
 *@author Colley
 *@version 1.0
 */
public final class GetterUtil {
    private static Log logger = LogFactory.getLog(GetterUtil.class);
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final boolean[] DEFAULT_BOOLEAN_VALUES = new boolean[0];
    private static final double DEFAULT_DOUBLE = 0.0;
    private static final double[] DEFAULT_DOUBLE_VALUES = new double[0];
    private static final float DEFAULT_FLOAT = 0;
    private static final float[] DEFAULT_FLOAT_VALUES = new float[0];
    private static final int DEFAULT_INTEGER = 0;
    private static final int[] DEFAULT_INTEGER_VALUES = new int[0];
    private static final long DEFAULT_LONG = 0;
    private static final long[] DEFAULT_LONG_VALUES = new long[0];
    private static final short DEFAULT_SHORT = 0;
    private static final short[] DEFAULT_SHORT_VALUES = new short[0];
    private static final String DEFAULT_STRING = "";
    private static String[] BOOLEANS = {
            "true",
            "t",
            "y",
            "on",
            "1"
        };

    public static boolean getBoolean(String value) {
        return getBoolean(value, DEFAULT_BOOLEAN);
    }

    public static boolean getBoolean(String value, boolean defaultValue) {
        return get(value, defaultValue);
    }

    public static boolean[] getBooleanValues(String[] values) {
        return getBooleanValues(values, DEFAULT_BOOLEAN_VALUES);
    }

    public static boolean[] getBooleanValues(String[] values, boolean[] defaultValue) {
        if (values == null) {
            return defaultValue;
        }

        boolean[] booleanValues = new boolean[values.length];

        for (int i = 0; i < values.length; i++) {
            booleanValues[i] = getBoolean(values[i]);
        }

        return booleanValues;
    }

    public static Date getDate(String value, DateFormat df) {
        return getDate(value, df, new Date());
    }

    public static Date getDate(String value, DateFormat df, Date defaultValue) {
        return get(value, df, defaultValue);
    }

    public static double getDouble(String value) {
        return getDouble(value, DEFAULT_DOUBLE);
    }

    public static double getDouble(String value, double defaultValue) {
        return get(value, defaultValue);
    }

    public static double[] getDoubleValues(String[] values) {
        return getDoubleValues(values, DEFAULT_DOUBLE_VALUES);
    }

    public static double[] getDoubleValues(String[] values, double[] defaultValue) {
        if (values == null) {
            return defaultValue;
        }

        double[] doubleValues = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            doubleValues[i] = getDouble(values[i]);
        }

        return doubleValues;
    }

    public static float getFloat(String value) {
        return getFloat(value, DEFAULT_FLOAT);
    }

    public static float getFloat(String value, float defaultValue) {
        return get(value, defaultValue);
    }

    public static float[] getFloatValues(String[] values) {
        return getFloatValues(values, DEFAULT_FLOAT_VALUES);
    }

    public static float[] getFloatValues(String[] values, float[] defaultValue) {
        if (values == null) {
            return defaultValue;
        }

        float[] floatValues = new float[values.length];

        for (int i = 0; i < values.length; i++) {
            floatValues[i] = getFloat(values[i]);
        }

        return floatValues;
    }

    public static int getInteger(String value) {
        return getInteger(value, DEFAULT_INTEGER);
    }

    public static int getInteger(Integer value, int defaultValue) {
        if ((value == null) || (value == 0)) {
            value = defaultValue;
        }

        return value;
    }

    public static int getInteger(Integer value) {
        return getInteger(value, DEFAULT_INTEGER);
    }

    public static int getInteger(String value, int defaultValue) {
        return get(value, defaultValue);
    }

    public static int[] getIntegerValues(String[] values) {
        return getIntegerValues(values, DEFAULT_INTEGER_VALUES);
    }

    public static int[] getIntegerValues(String[] values, int[] defaultValue) {
        if (values == null) {
            return defaultValue;
        }

        int[] intValues = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            intValues[i] = getInteger(values[i]);
        }

        return intValues;
    }

    public static long getLong(String value) {
        return getLong(value, DEFAULT_LONG);
    }

    public static long getLong(Long value) {
        return getLong(value, DEFAULT_LONG);
    }

    public static long getLong(Long value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        return value.longValue();
    }

    public static long getLong(String value, long defaultValue) {
        return get(value, defaultValue);
    }

    public static long[] getLongValues(String[] values) {
        return getLongValues(values, DEFAULT_LONG_VALUES);
    }

    public static long[] getLongValues(String[] values, long[] defaultValue) {
        if (values == null) {
            return defaultValue;
        }

        long[] longValues = new long[values.length];

        for (int i = 0; i < values.length; i++) {
            longValues[i] = getLong(values[i]);
        }

        return longValues;
    }

    public static short getShort(String value) {
        return getShort(value, DEFAULT_SHORT);
    }

    public static short getShort(String value, short defaultValue) {
        return get(value, defaultValue);
    }

    public static short[] getShortValues(String[] values) {
        return getShortValues(values, DEFAULT_SHORT_VALUES);
    }

    public static short[] getShortValues(String[] values, short[] defaultValue) {
        if (values == null) {
            return defaultValue;
        }

        short[] shortValues = new short[values.length];

        for (int i = 0; i < values.length; i++) {
            shortValues[i] = getShort(values[i]);
        }

        return shortValues;
    }

    public static String getString(String value) {
        return getString(value, DEFAULT_STRING);
    }

    public static String getString(String value, String defaultValue) {
        return get(value, defaultValue);
    }

    public static boolean get(String value, boolean defaultValue) {
        if (value != null) {
            value = value.trim();

            if (value.equalsIgnoreCase(BOOLEANS[0]) || value.equalsIgnoreCase(BOOLEANS[1]) ||
                    value.equalsIgnoreCase(BOOLEANS[2]) || value.equalsIgnoreCase(BOOLEANS[3]) ||
                    value.equalsIgnoreCase(BOOLEANS[4])) {
                return true;
            } else {
                return false;
            }
        }

        return defaultValue;
    }

    public static Date get(String value, DateFormat df, Date defaultValue) {
        if (StringUtils.isEmpty(getString(value))) {
            return defaultValue;
        }

        try {
            Date date = df.parse(value.trim());

            if (date != null) {
                return date;
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return defaultValue;
    }

    public static double get(String value, double defaultValue) {
        if (StringUtils.isBlank(value) || !NumberUtils.isNumber(value)) {
            return defaultValue;
        }

        return Double.parseDouble(_trim(value));
    }

    public static float get(String value, float defaultValue) {
        if (StringUtils.isBlank(value) || !NumberUtils.isNumber(value)) {
            return defaultValue;
        }

        return Float.parseFloat(_trim(value));
    }

    public static int get(String value, int defaultValue) {
        if (StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
            return defaultValue;
        }

        return Integer.parseInt(_trim(value));
    }

    public static long get(String value, long defaultValue) {
        if (StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
            return defaultValue;
        }

        return Long.parseLong(_trim(value));
    }

    public static short get(String value, short defaultValue) {
        if (StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
            return defaultValue;
        }

        return Short.parseShort(_trim(value));
    }

    public static String get(String value, String defaultValue) {
        if (!StringUtils.isBlank(value)) {
            value = value.trim();

            if ("null".equals(value)) {
                value = defaultValue;
            }

            return value;
        }

        return defaultValue;
    }

    public static String getString(Map<String, String> datamap, String key) {
        return getString(datamap, key, DEFAULT_STRING);
    }

    public static String getString(Map<String, String> datamap, String key, String defaultValue) {
        return get(datamap, key, defaultValue);
    }

    public static int getInteger(Map<String, String> datamap, String key) {
        return getInteger(datamap, key, DEFAULT_INTEGER);
    }

    public static int getInteger(Map<String, String> datamap, String key, int defaultValue) {
        return get(datamap, key, defaultValue);
    }

    public static String get(Map<String, String> datamap, String key, String defaultValue) {
        String value = datamap.get(key);

        return get(value, defaultValue);
    }

    public static int get(Map<String, String> datamap, String key, int defaultValue) {
        String value = datamap.get(key);

        return get(value, defaultValue);
    }

    public static boolean get(Map<String, String> datamap, String key, boolean defaultValue) {
        String value = datamap.get(key);

        if (value != null) {
            value = value.trim();

            if (value.equalsIgnoreCase(BOOLEANS[0]) || value.equalsIgnoreCase(BOOLEANS[1]) ||
                    value.equalsIgnoreCase(BOOLEANS[2]) || value.equalsIgnoreCase(BOOLEANS[3]) ||
                    value.equalsIgnoreCase(BOOLEANS[4])) {
                return true;
            } else {
                return false;
            }
        }

        return defaultValue;
    }

    public static boolean getBoolean(Map<String, String> datamap, String key) {
        return get(datamap, key, DEFAULT_BOOLEAN);
    }

    public static boolean getBoolean(Map<String, String> datamap, String key, boolean defaultValue) {
        return get(datamap, key, defaultValue);
    }

    private static String _trim(String value) {
        if (value != null) {
            value = value.trim();
            value.replaceAll(" ", "");
        }

        return value;
    }

    public static boolean isChineseChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            String bb = str.substring(i, i + 1);
            boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);

            if (cc) {
                return true;
            }
        }

        return false;
    }

    public static <K, V> Map<K, V> getFixDatamap(Map<K, V> datamap) {
        return (datamap == null) ? new HashMap<K, V>() : datamap;
    }

    public static <T> List<T> getFixDatalist(List<T> dataList) {
        return (dataList == null) ? new ArrayList<T>() : dataList;
    }

    public static <T> Set<T> getFixDataset(Set<T> dataSet) {
        return (dataSet == null) ? new HashSet<T>() : dataSet;
    }

    public static List<String> getSplit2Array(String codes, String splitChar) {
        if (StringUtils.isBlank(codes)) {
            return new ArrayList<String>(0);
        }

        return Arrays.asList(StringUtils.split(codes, splitChar));
    }

    public static List<String> getSplit2Array(String codes) {
        return getSplit2Array(codes, ",");
    }

    public static List<Long> getSplit2Long(String codes, String splitChar) {
        if (StringUtils.isBlank(codes)) {
            return new ArrayList<Long>(0);
        }

        String[] stringArr = StringUtils.split(codes, splitChar);

        return getSplit2Long(stringArr);
    }

    public static List<Long> getSplit2Long(String[] stringArray) {
        List<Long> longList = new ArrayList<Long>();

        if (ArrayUtils.isNotEmpty(stringArray)) {
            for (int i = 0; i < stringArray.length; i++) {
                longList.add(getLong(stringArray[i]));
            }
        }

        return longList;
    }

    public static List<Long> getSplit2Long(String codes) {
        return getSplit2Long(codes, ",");
    }
}
