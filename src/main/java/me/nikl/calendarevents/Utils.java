package me.nikl.calendarevents;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static String getTimeUntil(Duration duration) {
        if (duration.toDays() > 1)
            return duration.toDays() + " days";
        else if (duration.toHours() > 1)
            return duration.toDays() + " hours";
        else
            return duration.toMinutes() + " minutes";
    }

    public static String separateByCasing(String str) {
        return String.join(" ", str.split("(?<=[a-z])(?=[A-Z])"));
    }

    public static LinkedHashMap<String, Instant> sortByValue(Map<String, Instant> map) {
        List<Map.Entry<String, Instant>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        LinkedHashMap<String, Instant> result = new LinkedHashMap<>();
        for (Map.Entry<String, Instant> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static LinkedHashMap<String, Instant> convertAndSortByValue(Map<String, Long> map) {
        return sortByValue(map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> Instant.ofEpochMilli(entry.getValue()))));
    }

    public static void constructEventsList(CommandSender sender, LinkedHashMap<String, Instant> events) {
        sender.sendMessage("§7§m---------------------§r §bEvents§r §7§m------------------------");
        events.forEach((String event, Instant millis) -> {
            Duration dur = Duration.between(millis, Instant.now());
            dur = dur.abs();
            ChatColor color = ChatColor.valueOf(CalendarEvents.instance.getConfig().getString("events." + event + ".color", "GOLD"));
            String str = CalendarEvents.instance.getConfig().getString("events." + event + ".placehldr", "%event% in %time%");
            assert str != null;
            sender.sendMessage(color + "§l" + str.replace("%event%", Utils.separateByCasing(event)).replace("%time%", Utils.getTimeUntil(dur)));
        });
        sender.sendMessage("§7§m----------------------------------------------------");
    }
}
