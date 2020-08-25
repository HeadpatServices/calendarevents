package me.nikl.calendarevents;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EventCommand implements TabExecutor {
    private final CalendarEvents plugin;

    public EventCommand(CalendarEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Long> nextCalls = this.plugin.getApi().getNextCallsOfEvents();
        sender.sendMessage("§7§m---------------------§r §bEvents§r §7§m------------------------");
        nextCalls.forEach((String event, Long millis) -> {
            Duration dur = Duration.between(Instant.ofEpochMilli(millis), Instant.now());
            dur = dur.abs();
            ChatColor color = ChatColor.valueOf(CalendarEvents.instance.getConfig().getString("events." + event + ".color", "GOLD"));
            String str = CalendarEvents.instance.getConfig().getString("events." + event + ".placehldr", "%event% in %time%");
            assert str != null;
            sender.sendMessage(color + "§l" + str.replace("%event%", Utils.separateByCasing(event)).replace("%time%", Utils.getTimeUntil(dur)));
        });
        sender.sendMessage("§7§m----------------------------------------------------");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
