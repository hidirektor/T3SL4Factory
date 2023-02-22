package me.t3sl4.factory.util.reflection.mck;

import me.t3sl4.factory.util.reflection.RefField;
import me.t3sl4.factory.util.reflection.RefFieldExecuted;
import org.jetbrains.annotations.NotNull;

public class MckField implements RefField {
   public void set(@NotNull Object value) {
   }

   @NotNull
   public Object get(@NotNull Object fallback) {
      return fallback;
   }

   @NotNull
   public RefFieldExecuted of(@NotNull Object object) {
      return new RefFieldExecuted() {
         public void set(@NotNull Object value) {
         }

         @NotNull
         public Object get(@NotNull Object fallback) {
            return fallback;
         }
      };
   }
}
