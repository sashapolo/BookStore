package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReverseEnumMap<V extends Enum<V> & EnumConverter> {
    private Map<Integer, V> map = new HashMap<>();

    public ReverseEnumMap(Class<V> valueType) {
        for (V v : valueType.getEnumConstants()) {
            map.put(v.convert(), v);
        }
    }

    public V get(int num) {
        return map.get(num);
    }
}
