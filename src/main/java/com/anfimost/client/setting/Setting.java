package com.anfimost.client.setting;

import java.util.function.Supplier;

public class Setting<T> {
    private final String name;
    private T value;
    private final T defaultValue;
    private T min;
    private T max;
    private final Class<T> type;
    private Supplier<Boolean> visible;

    public Setting(String name, T defaultValue, Class<T> type) {
        this.name = name;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.type = type;
        this.visible = () -> true;
    }

    public Setting(String name, T defaultValue, T min, T max, Class<T> type) {
        this(name, defaultValue, type);
        this.min = min;
        this.max = max;
    }

    public Setting<T> withVisibility(Supplier<Boolean> visible) {
        this.visible = visible;
        return this;
    }

    public String getName() { return name; }
    public T getValue() { return value; }
    public T getDefaultValue() { return defaultValue; }
    public T getMin() { return min; }
    public T getMax() { return max; }
    public Class<T> getType() { return type; }
    public boolean isVisible() { return visible.get(); }

    public void setValue(T value) {
        if (min != null && max != null && value instanceof Number) {
            double v = ((Number) value).doubleValue();
            double lo = ((Number) min).doubleValue();
            double hi = ((Number) max).doubleValue();
            v = Math.max(lo, Math.min(hi, v));
            this.value = castNumber(v);
        } else {
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    private T castNumber(double v) {
        if (type == Integer.class) return (T) Integer.valueOf((int) Math.round(v));
        if (type == Float.class) return (T) Float.valueOf((float) v);
        if (type == Double.class) return (T) Double.valueOf(v);
        if (type == Long.class) return (T) Long.valueOf(Math.round(v));
        return (T) Double.valueOf(v);
    }

    @SuppressWarnings("unchecked")
    public void increment() {
        if (value instanceof Boolean) {
            this.value = (T) Boolean.valueOf(!(Boolean) value);
        } else if (value instanceof Enum) {
            Enum<?> e = (Enum<?>) value;
            Object[] constants = e.getClass().getEnumConstants();
            int next = (e.ordinal() + 1) % constants.length;
            this.value = (T) constants[next];
        }
    }
}
