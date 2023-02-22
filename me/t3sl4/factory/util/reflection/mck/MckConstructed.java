package me.t3sl4.factory.util.reflection.mck;

import me.t3sl4.factory.util.reflection.RefConstructed;
import org.jetbrains.annotations.NotNull;

public class MckConstructed implements RefConstructed {
   @NotNull
   public Object create(@NotNull Object fallback, @NotNull Object... parameters) {
      return fallback;
   }
}
