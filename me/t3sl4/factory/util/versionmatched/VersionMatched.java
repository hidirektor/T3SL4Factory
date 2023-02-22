package me.t3sl4.factory.util.versionmatched;

import me.t3sl4.factory.util.reflection.RefConstructed;
import me.t3sl4.factory.util.reflection.clazz.ClassOf;
import java.util.Iterator;
import java.util.List;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.jetbrains.annotations.NotNull;

public final class VersionMatched<T> {
    @NotNull
    private final String rawVersion;
    @NotNull
    private final T fallback;
    @NotNull
    private final List<VersionClass<T>> versionClasses;

    public VersionMatched(@NotNull String rawVersion, @NotNull T fallback, @NotNull List<VersionClass<T>> versionClasses) {
        this.rawVersion = rawVersion;
        this.fallback = fallback;
        this.versionClasses = versionClasses;
    }

    public VersionMatched(@NotNull Version version, @NotNull T fallback, @NotNull List<VersionClass<T>> versionClasses) {
        this(version.raw(), fallback, versionClasses);
    }

    @SafeVarargs
    public VersionMatched(@NotNull String rawVersion, @NotNull T fallback, @NotNull Class<? extends T>... versionClasses) {
        this((String)rawVersion, fallback, (List)(new ListOf(new Mapped<>(VersionClass::new, new IterableOf<>(versionClasses)))));
    }

    @SafeVarargs
    public VersionMatched(@NotNull T fallback, @NotNull Class<? extends T>... versionClasses) {
        this((Version)(new Version()), fallback, (List)(new ListOf(new Mapped<>(VersionClass::new, new IterableOf<>(versionClasses)))));
    }

    @NotNull
    public VersionMatched<T>.Instantiated of(Object... types) {
        return new VersionMatched.Instantiated((new ClassOf(this.match())).getConstructor(types));
    }

    @NotNull
    public VersionMatched<T>.Instantiated ofPrimitive(Object... types) {
        return new VersionMatched.Instantiated((new ClassOf(this.match())).getPrimitiveConstructor(types));
    }

    @NotNull
    private Class<? extends T> match() {
        Iterator var1 = this.versionClasses.iterator();

        VersionClass versionClass;
        do {
            if (!var1.hasNext()) {
                throw new IllegalStateException("match() -> Couldn't find any matched class on \"" + this.rawVersion + "\" version!");
            }

            versionClass = (VersionClass)var1.next();
        } while(!versionClass.match(this.rawVersion));

        return versionClass.getVersionClass();
    }

    public class Instantiated {
        @NotNull
        private final RefConstructed refConstructed;

        public Instantiated(@NotNull RefConstructed refConstructed) {
            this.refConstructed = refConstructed;
        }

        @NotNull
        public T instance(@NotNull Object... args) {
            return (T) this.refConstructed.create(VersionMatched.this.fallback, args);
        }
    }
}
