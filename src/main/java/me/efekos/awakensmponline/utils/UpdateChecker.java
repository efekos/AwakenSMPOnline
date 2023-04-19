package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private AwakenSMPOnline plugin;
    private int resourdeId;

    public UpdateChecker(AwakenSMPOnline plugin, int resourdeId) {
        this.plugin = plugin;
        this.resourdeId = resourdeId;
    }

    public void getLatestVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourdeId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
               Logger.error("Update checker is broken, can't find an update!" + exception.getMessage());
            }
        });
    }
}
