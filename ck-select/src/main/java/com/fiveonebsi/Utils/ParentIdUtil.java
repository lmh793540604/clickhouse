package com.fiveonebsi.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yangjun
 */
public class ParentIdUtil {
    public static List<String> getValidIds(Map<String, String> map) {
        Set<String> parentIds = map.entrySet().stream().map(p -> p.getKey()).collect(Collectors.toSet());
        List<String> validIds = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!parentIds.contains(entry.getValue())) {
                validIds.add(entry.getKey());
            }
        }
        return validIds;
    }
}
