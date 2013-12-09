package edu.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ReverseEnumMap<V extends Enum<V> & EnumConverter> {
    private final Map<Integer, V> map;

    public ReverseEnumMap(final Class<V> valueType) {
        final V[] enums = valueType.getEnumConstants();
        map = new HashMap<>(enums.length);

        for (final V v : valueType.getEnumConstants()) {
            map.put(v.convert(), v);
        }
    }

    public V get(final int num) {
        return map.get(num);
    }
}
