/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tan.lib_utils_android.apache;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.SortedMap;

/**
 * Provides utility methods and decorators for
 * {@link Map} and {@link SortedMap} instances.
 * <p>
 * It contains various type safe methods
 * as well as other useful features like deep copying.
 * <p>
 * It also provides the following decorators:
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @author <a href="mailto:nissim@nksystems.com">Nissim Karpenstein</a>
 * @author <a href="mailto:knielsen@apache.org">Kasper Nielsen</a>
 * @author Paul Jack
 * @author Stephen Colebourne
 * @author Matthew Hawthorne
 * @author Arun Mammen Thomas
 * @author Janek Bogucki
 * @author Max Rydahl Andersen
 * @author <a href="mailto:equinus100@hotmail.com">Ashwin S</a>
 * @author <a href="mailto:jcarman@apache.org">James Carman</a>
 * @author Neil O'Toole
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 * @since Commons Collections 1.0
 */
public class MapUtils {

    /**
     * String used to indent the verbose and debug Map prints.
     */
    private static final String INDENT_STRING = "    ";

    /**
     * <code>MapUtils</code> should not normally be instantiated.
     */
    public MapUtils() {
    }

    // Type safe getters
    //-------------------------------------------------------------------------

    /**
     * Gets from a Map in a null-safe manner.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map, <code>null</code> if null map input
     */
    public static Object getObject(final Map map, final Object key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * Gets a String from a Map in a null-safe manner.
     * <p>
     * The String is obtained via <code>toString</code>.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a String, <code>null</code> if null map input
     */
    public static String getString(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    /**
     * Gets a Boolean from a Map in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> it is returned directly.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>null</code> is returned.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Boolean, <code>null</code> if null map input
     */
    public static Boolean getBoolean(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;

                } else if (answer instanceof String) {
                    return new Boolean((String) answer);

                } else if (answer instanceof Number) {
                    Number n = (Number) answer;
                    return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return null;
    }

    /**
     * Gets a Number from a Map in a null-safe manner.
     * <p>
     * If the value is a <code>Number</code> it is returned directly.
     * If the value is a <code>String</code> it is converted using
     * {@link NumberFormat#parse(String)} on the system default formatter
     * returning <code>null</code> if the conversion fails.
     * Otherwise, <code>null</code> is returned.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Number, <code>null</code> if null map input
     */
    public static Number getNumber(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;

                } else if (answer instanceof String) {
                    try {
                        String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);

                    } catch (ParseException e) {
                        logInfo(e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets a Byte from a Map in a null-safe manner.
     * <p>
     * The Byte is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Byte, <code>null</code> if null map input
     */
    public static Byte getByte(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return new Byte(answer.byteValue());
    }

    /**
     * Gets a Short from a Map in a null-safe manner.
     * <p>
     * The Short is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Short, <code>null</code> if null map input
     */
    public static Short getShort(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Short) {
            return (Short) answer;
        }
        return new Short(answer.shortValue());
    }

    /**
     * Gets a Integer from a Map in a null-safe manner.
     * <p>
     * The Integer is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Integer, <code>null</code> if null map input
     */
    public static Integer getInteger(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return new Integer(answer.intValue());
    }

    /**
     * Gets a Long from a Map in a null-safe manner.
     * <p>
     * The Long is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Long, <code>null</code> if null map input
     */
    public static Long getLong(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return new Long(answer.longValue());
    }

    /**
     * Gets a Float from a Map in a null-safe manner.
     * <p>
     * The Float is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Float, <code>null</code> if null map input
     */
    public static Float getFloat(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Float) {
            return (Float) answer;
        }
        return new Float(answer.floatValue());
    }

    /**
     * Gets a Double from a Map in a null-safe manner.
     * <p>
     * The Double is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Double, <code>null</code> if null map input
     */
    public static Double getDouble(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return new Double(answer.doubleValue());
    }

    /**
     * Gets a Map from a Map in a null-safe manner.
     * <p>
     * If the value returned from the specified map is not a Map then
     * <code>null</code> is returned.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Map, <code>null</code> if null map input
     */
    public static Map getMap(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null && answer instanceof Map) {
                return (Map) answer;
            }
        }
        return null;
    }

    // Type safe getters with default values
    //-------------------------------------------------------------------------

    /**
     * Looks up the given key in the given map, converting null into the
     * given default value.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null
     * @return the value in the map, or defaultValue if the original value
     * is null or the map is null
     */
    public static Object getObject(Map map, Object key, Object defaultValue) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer;
            }
        }
        return defaultValue;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a string, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a string, or defaultValue if the
     * original value is null, the map is null or the string conversion
     * fails
     */
    public static String getString(Map map, Object key, String defaultValue) {
        String answer = getString(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a boolean, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a boolean, or defaultValue if the
     * original value is null, the map is null or the boolean conversion
     * fails
     */
    public static Boolean getBoolean(Map map, Object key, Boolean defaultValue) {
        Boolean answer = getBoolean(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a number, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the number conversion
     * fails
     */
    public static Number getNumber(Map map, Object key, Number defaultValue) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a byte, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the number conversion
     * fails
     */
    public static Byte getByte(Map map, Object key, Byte defaultValue) {
        Byte answer = getByte(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a short, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the number conversion
     * fails
     */
    public static Short getShort(Map map, Object key, Short defaultValue) {
        Short answer = getShort(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * an integer, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the number conversion
     * fails
     */
    public static Integer getInteger(Map map, Object key, Integer defaultValue) {
        Integer answer = getInteger(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a long, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the number conversion
     * fails
     */
    public static Long getLong(Map map, Object key, Long defaultValue) {
        Long answer = getLong(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a float, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the number conversion
     * fails
     */
    public static Float getFloat(Map map, Object key, Float defaultValue) {
        Float answer = getFloat(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a double, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the number conversion
     * fails
     */
    public static Double getDouble(Map map, Object key, Double defaultValue) {
        Double answer = getDouble(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Looks up the given key in the given map, converting the result into
     * a map, using the default value if the the conversion fails.
     *
     * @param map          the map whose value to look up
     * @param key          the key of the value to look up in that map
     * @param defaultValue what to return if the value is null or if the
     *                     conversion fails
     * @return the value in the map as a number, or defaultValue if the
     * original value is null, the map is null or the map conversion
     * fails
     */
    public static Map getMap(Map map, Object key, Map defaultValue) {
        Map answer = getMap(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }


    // Type safe primitive getters
    //-------------------------------------------------------------------------

    /**
     * Gets a boolean from a Map in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> its value is returned.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>false</code> is returned.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a Boolean, <code>false</code> if null map input
     */
    public static boolean getBooleanValue(final Map map, final Object key) {
        Boolean booleanObject = getBoolean(map, key);
        if (booleanObject == null) {
            return false;
        }
        return booleanObject.booleanValue();
    }

    /**
     * Gets a byte from a Map in a null-safe manner.
     * <p>
     * The byte is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a byte, <code>0</code> if null map input
     */
    public static byte getByteValue(final Map map, final Object key) {
        Byte byteObject = getByte(map, key);
        if (byteObject == null) {
            return 0;
        }
        return byteObject.byteValue();
    }

    /**
     * Gets a short from a Map in a null-safe manner.
     * <p>
     * The short is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a short, <code>0</code> if null map input
     */
    public static short getShortValue(final Map map, final Object key) {
        Short shortObject = getShort(map, key);
        if (shortObject == null) {
            return 0;
        }
        return shortObject.shortValue();
    }

    /**
     * Gets an int from a Map in a null-safe manner.
     * <p>
     * The int is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as an int, <code>0</code> if null map input
     */
    public static int getIntValue(final Map map, final Object key) {
        Integer integerObject = getInteger(map, key);
        if (integerObject == null) {
            return 0;
        }
        return integerObject.intValue();
    }

    /**
     * Gets a long from a Map in a null-safe manner.
     * <p>
     * The long is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a long, <code>0L</code> if null map input
     */
    public static long getLongValue(final Map map, final Object key) {
        Long longObject = getLong(map, key);
        if (longObject == null) {
            return 0L;
        }
        return longObject.longValue();
    }

    /**
     * Gets a float from a Map in a null-safe manner.
     * <p>
     * The float is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a float, <code>0.0F</code> if null map input
     */
    public static float getFloatValue(final Map map, final Object key) {
        Float floatObject = getFloat(map, key);
        if (floatObject == null) {
            return 0f;
        }
        return floatObject.floatValue();
    }

    /**
     * Gets a double from a Map in a null-safe manner.
     * <p>
     * The double is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map the map to use
     * @param key the key to look up
     * @return the value in the Map as a double, <code>0.0</code> if null map input
     */
    public static double getDoubleValue(final Map map, final Object key) {
        Double doubleObject = getDouble(map, key);
        if (doubleObject == null) {
            return 0d;
        }
        return doubleObject.doubleValue();
    }

    // Type safe primitive getters with default values
    //-------------------------------------------------------------------------

    /**
     * Gets a boolean from a Map in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * If the value is a <code>Boolean</code> its value is returned.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>defaultValue</code> is returned.
     *
     * @param map          the map to use
     * @param key          the key to look up
     * @param defaultValue return if the value is null or if the
     *                     conversion fails
     * @return the value in the Map as a Boolean, <code>defaultValue</code> if null map input
     */
    public static boolean getBooleanValue(final Map map, final Object key, boolean defaultValue) {
        Boolean booleanObject = getBoolean(map, key);
        if (booleanObject == null) {
            return defaultValue;
        }
        return booleanObject.booleanValue();
    }

    /**
     * Gets a byte from a Map in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The byte is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map          the map to use
     * @param key          the key to look up
     * @param defaultValue return if the value is null or if the
     *                     conversion fails
     * @return the value in the Map as a byte, <code>defaultValue</code> if null map input
     */
    public static byte getByteValue(final Map map, final Object key, byte defaultValue) {
        Byte byteObject = getByte(map, key);
        if (byteObject == null) {
            return defaultValue;
        }
        return byteObject.byteValue();
    }

    /**
     * Gets a short from a Map in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The short is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map          the map to use
     * @param key          the key to look up
     * @param defaultValue return if the value is null or if the
     *                     conversion fails
     * @return the value in the Map as a short, <code>defaultValue</code> if null map input
     */
    public static short getShortValue(final Map map, final Object key, short defaultValue) {
        Short shortObject = getShort(map, key);
        if (shortObject == null) {
            return defaultValue;
        }
        return shortObject.shortValue();
    }

    /**
     * Gets an int from a Map in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The int is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map          the map to use
     * @param key          the key to look up
     * @param defaultValue return if the value is null or if the
     *                     conversion fails
     * @return the value in the Map as an int, <code>defaultValue</code> if null map input
     */
    public static int getIntValue(final Map map, final Object key, int defaultValue) {
        Integer integerObject = getInteger(map, key);
        if (integerObject == null) {
            return defaultValue;
        }
        return integerObject.intValue();
    }

    /**
     * Gets a long from a Map in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The long is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map          the map to use
     * @param key          the key to look up
     * @param defaultValue return if the value is null or if the
     *                     conversion fails
     * @return the value in the Map as a long, <code>defaultValue</code> if null map input
     */
    public static long getLongValue(final Map map, final Object key, long defaultValue) {
        Long longObject = getLong(map, key);
        if (longObject == null) {
            return defaultValue;
        }
        return longObject.longValue();
    }

    /**
     * Gets a float from a Map in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The float is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map          the map to use
     * @param key          the key to look up
     * @param defaultValue return if the value is null or if the
     *                     conversion fails
     * @return the value in the Map as a float, <code>defaultValue</code> if null map input
     */
    public static float getFloatValue(final Map map, final Object key, float defaultValue) {
        Float floatObject = getFloat(map, key);
        if (floatObject == null) {
            return defaultValue;
        }
        return floatObject.floatValue();
    }

    /**
     * Gets a double from a Map in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The double is obtained from the results of {@link #getNumber(Map, Object)}.
     *
     * @param map          the map to use
     * @param key          the key to look up
     * @param defaultValue return if the value is null or if the
     *                     conversion fails
     * @return the value in the Map as a double, <code>defaultValue</code> if null map input
     */
    public static double getDoubleValue(final Map map, final Object key, double defaultValue) {
        Double doubleObject = getDouble(map, key);
        if (doubleObject == null) {
            return defaultValue;
        }
        return doubleObject.doubleValue();
    }

    // Conversion methods
    //-------------------------------------------------------------------------

    /**
     * Gets a new Properties object initialised with the values from a Map.
     * A null input will return an empty properties object.
     *
     * @param map the map to convert to a Properties object, may not be null
     * @return the properties object
     */
    public static Properties toProperties(final Map map) {
        Properties answer = new Properties();
        if (map != null) {
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                answer.put(key, value);
            }
        }
        return answer;
    }

    /**
     * Creates a new HashMap using data copied from a ResourceBundle.
     *
     * @param resourceBundle the resource bundle to convert, may not be null
     * @return the hashmap containing the data
     * @throws NullPointerException if the bundle is null
     */
    public static Map toMap(final ResourceBundle resourceBundle) {
        Enumeration enumeration = resourceBundle.getKeys();
        Map map = new HashMap();

        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            Object value = resourceBundle.getObject(key);
            map.put(key, value);
        }

        return map;
    }

    // Printing methods
    //-------------------------------------------------------------------------


    // Implementation methods
    //-------------------------------------------------------------------------

    /**
     * Logs the given exception to <code>System.out</code>.
     * <p>
     * This method exists as Jakarta Collections does not depend on logging.
     *
     * @param ex the exception to log
     */
    protected static void logInfo(final Exception ex) {
        System.out.println("INFO: Exception: " + ex);
    }

    /**
     * Writes indentation to the given stream.
     *
     * @param out the stream to indent
     */
    private static void printIndent(final PrintStream out, final int indent) {
        for (int i = 0; i < indent; i++) {
            out.print(INDENT_STRING);
        }
    }

    // Misc
    //-----------------------------------------------------------------------

    /**
     * Inverts the supplied map returning a new HashMap such that the keys of
     * the input are swapped with the values.
     * <p>
     * This operation assumes that the inverse mapping is well defined.
     * If the input map had multiple entries with the same value mapped to
     * different keys, the returned map will map one of those keys to the
     * value, but the exact key which will be mapped is undefined.
     *
     * @param map the map to invert, may not be null
     * @return a new HashMap containing the inverted data
     * @throws NullPointerException if the map is null
     */
    public static Map invertMap(Map map) {
        Map out = new HashMap(map.size());
        for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            out.put(entry.getValue(), entry.getKey());
        }
        return out;
    }

    //-----------------------------------------------------------------------

    /**
     * Protects against adding null values to a map.
     * <p>
     * This method checks the value being added to the map, and if it is null
     * it is replaced by an empty string.
     * <p>
     * This could be useful if the map does not accept null values, or for
     * receiving data from a source that may provide null or empty string
     * which should be held in the same way in the map.
     * <p>
     * Keys are not validated.
     *
     * @param map   the map to add to, may not be null
     * @param key   the key
     * @param value the value, null converted to ""
     * @throws NullPointerException if the map is null
     */
    public static void safeAddToMap(Map map, Object key, Object value) throws NullPointerException {
        if (value == null) {
            map.put(key, "");
        } else {
            map.put(key, value);
        }
    }

    //-----------------------------------------------------------------------


    //-----------------------------------------------------------------------

    /**
     * Null-safe check if the specified map is empty.
     * <p>
     * Null returns true.
     *
     * @param map the map to check, may be null
     * @return true if empty or null
     * @since Commons Collections 3.2
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    /**
     * Null-safe check if the specified map is not empty.
     * <p>
     * Null returns false.
     *
     * @param map the map to check, may be null
     * @return true if non-null and non-empty
     * @since Commons Collections 3.2
     */
    public static boolean isNotEmpty(Map map) {
        return !MapUtils.isEmpty(map);
    }

    // Map decorators
    //-----------------------------------------------------------------------

    /**
     * Returns a synchronized map backed by the given map.
     * <p>
     * You must manually synchronize on the returned buffer's iterator to
     * avoid non-deterministic behavior:
     * <p>
     * <pre>
     * Map m = MapUtils.synchronizedMap(myMap);
     * Set s = m.keySet();  // outside synchronized block
     * synchronized (m) {  // synchronized on MAP!
     *     Iterator i = s.iterator();
     *     while (i.hasNext()) {
     *         process (i.next());
     *     }
     * }
     * </pre>
     * <p>
     * This method uses the implementation in {@link Collections Collections}.
     *
     * @param map the map to synchronize, must not be null
     * @return a synchronized map backed by the given map
     * @throws IllegalArgumentException if the map is null
     */
    public static Map synchronizedMap(Map map) {
        return Collections.synchronizedMap(map);
    }


    // SortedMap decorators
    //-----------------------------------------------------------------------

    /**
     * Returns a synchronized sorted map backed by the given sorted map.
     * <p>
     * You must manually synchronize on the returned buffer's iterator to
     * avoid non-deterministic behavior:
     * <p>
     * <pre>
     * Map m = MapUtils.synchronizedSortedMap(myMap);
     * Set s = m.keySet();  // outside synchronized block
     * synchronized (m) {  // synchronized on MAP!
     *     Iterator i = s.iterator();
     *     while (i.hasNext()) {
     *         process (i.next());
     *     }
     * }
     * </pre>
     * <p>
     * This method uses the implementation in {@link Collections Collections}.
     *
     * @param map the map to synchronize, must not be null
     * @return a synchronized map backed by the given map
     * @throws IllegalArgumentException if the map is null
     */
    public static Map synchronizedSortedMap(SortedMap map) {
        return Collections.synchronizedSortedMap(map);
    }


}
