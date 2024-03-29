package com.example.insta.faker;

import com.example.insta.domain.BaseEntity;
import java.lang.reflect.Field;

public class BaseEntityHelper {

  //    public static Object setField(Object entity) {
  //    public static void setField(Object instance, String field, Object value) {
  public static <T extends BaseEntity<?>> void setBaseEntityField(
      T entity, String field, Object value) {

    try {
      // Reflection API from java
      Class<?> clazz = entity.getClass();

      // Get the field of the BaseEntity
      Field declaredField = clazz.getSuperclass().getDeclaredField(field);

      //            clazz.getDeclaredField(field);

      // Set the field to be accessed
      declaredField.setAccessible(true);

      // Set the value
      declaredField.set(entity, value);

      // return entity;
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // This should never happen
      throw new RuntimeException(e);
    }
  }
}
