package org.lifeanarchy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class LightLag extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            final int time = 1000;
            final String version = Bukkit.getServer()
                    .getClass()
                    .getPackage()
                    .getName()
                    .split("\\.")[3];

            final Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);

            final Field MAX_TIME = Class.forName("net.minecraft.server." + version + ".PaperLightingQueue")
                    .getDeclaredField("MAX_TIME");
            MAX_TIME.setAccessible(true);

            modifiers.setInt(MAX_TIME, MAX_TIME.getModifiers() & ~Modifier.FINAL);
            MAX_TIME.set(null, (long) (time / 20 * 1.15));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}