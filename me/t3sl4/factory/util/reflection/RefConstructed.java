package me.t3sl4.factory.util.reflection;

import org.jetbrains.annotations.NotNull;

public interface RefConstructed {
   @NotNull
   Object create(@NotNull Object var1, @NotNull Object... var2);
}
