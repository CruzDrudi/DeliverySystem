package com.solvd.delivery.utils;

import com.solvd.delivery.Main;
import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.annotations.SensitiveData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;


public class ObjectPrinter {
    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static PrintedObject inspect(Object o){
        Class<?> clazz =  o.getClass();
        StringBuilder text = new StringBuilder();
        text.append("Object -----------------------\n");
        text.append("Class: " + clazz.getSimpleName() + "\n");

        if (clazz.isAnnotationPresent(EntityInfo.class)) {
            EntityInfo info = (EntityInfo) clazz.getAnnotation(EntityInfo.class);
            text.append("Info: ").append(info.value()).append("\n");
        }
        text.append("Fields -----------------------\n");

        for(Field f : clazz.getDeclaredFields()){
            f.setAccessible(true);
            if (f.isAnnotationPresent(SensitiveData.class)) {
                text.append("\t").append(f.getName()).append(": *****\n");
                continue;
            }
            try {
                text.append("\t").append(f.getName()).append(": ").append(f.get(o)).append("\n");
            } catch (IllegalAccessException e) {
                LOGGER.error("Error while getting field " + f.getName(), e);
            }
        }

        return new PrintedObject(text.toString());
    }
}
