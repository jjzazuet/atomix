package io.atomix.utils;

public class Assert {

  public static void isTrue(boolean condition) {
    isTrue(condition, null);
  }

  public static void isTrue(boolean condition, String whenFailed) {
    if (!condition) {
      throw new IllegalStateException(
          whenFailed != null && whenFailed.trim().length() > 0 ?
              whenFailed : "Condition not satisfied"
      );
    }
  }
}
