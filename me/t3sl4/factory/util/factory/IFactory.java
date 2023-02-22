package me.t3sl4.factory.util.factory;

import org.bukkit.scheduler.BukkitTask;

public class IFactory {
    private int factoryLevel;
    private int factorySpeed;
    private String factoryOwner;
    private BukkitTask task;

    public IFactory(int factoryLevel, int factorySpeed, String factoryOwner, BukkitTask task) {
        this.factoryLevel = factoryLevel;
        this.factorySpeed = factorySpeed;
        this.factoryOwner = factoryOwner;
        this.task = task;
    }

    public int getFactoryLevel() {
        return factoryLevel;
    }

    public void setFactoryLevel(int factoryLevel) {
        this.factoryLevel = factoryLevel;
    }

    public int getFactorySpeed() {
        return factorySpeed;
    }

    public void setFactorySpeed(int factorySpeed) {
        this.factorySpeed = factorySpeed;
    }

    public String getFactoryOwner() {
        return factoryOwner;
    }

    public void setFactoryOwner(String factoryOwner) {
        this.factoryOwner = factoryOwner;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }
}
