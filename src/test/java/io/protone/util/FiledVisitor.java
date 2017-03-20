package io.protone.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
public class FiledVisitor {
    public static boolean checkFiledsNotNull(Object obj) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(obj) == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean checkFiledsNotNull(List<Object> obj) {
        for (Object object : obj) {
            if (checkFiledsNotNull(object)) {
                return true;
            }
        }
        return false;
    }
}
