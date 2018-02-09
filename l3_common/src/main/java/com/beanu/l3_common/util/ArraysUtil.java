package com.beanu.l3_common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by lizhi on 2017/6/28.
 * 数组工具类
 */

public class ArraysUtil {

    @SafeVarargs
    @SuppressWarnings({"unchecked", "varargs"})
    public static <T> T[] toArray(T... vararg) {
        return vararg;
    }

    @SafeVarargs
    @SuppressWarnings({"unchecked", "varargs"})
    public static <T> List<T> toMutableList(T... vararg) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, vararg);
        return list;
    }

    public static <T> int length(T[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(Collection collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <T> boolean isEmpty(T[] array) {
        return length(array) == 0;
    }

    public static boolean isEmpty(Collection collection) {
        return length(collection) == 0;
    }

    public static <T> boolean contains(T[] array, T o) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (T t : array) {
            if (equals(t, o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Collection collection, Object o) {
        return !(collection == null || collection.size() == 0) && collection.contains(o);
    }

    public static <T> boolean indexIn(T[] array, int index) {
        return index >= 0 && index < length(array);
    }

    public static boolean indexIn(Collection collection, int index) {
        return index >= 0 && index < length(collection);
    }

    public static <T> T get(List<? extends T> list, int position) {
        return get(list, position, null);
    }

    public static <T> T get(List<? extends T> list, int position, T defaultValue) {
        if (list == null || !indexIn(list, position)) {
            return defaultValue;
        }
        return list.get(position);
    }

    private static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }


}
