package com.example.insta.foundation;

import java.util.UUID;

public abstract class EntityHelpers {
  public static String generateUUIDv4() {
    return UUID.randomUUID().toString();
  }
}
