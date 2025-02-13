package ru.t1.java.demo.config;

import ch.qos.logback.classic.Level;

public class LoggerProperties {
   private Level level = Level.INFO;

   public Level getLevel() {
      return level;
   }

   public void setLevel(Level level) {
      this.level = level;
   }
}
