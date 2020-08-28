package me.nikl.calendarevents;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class EventCommand implements TabExecutor {
    private final CalendarEvents plugin;

    public EventCommand(CalendarEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LinkedHashMap<String, Instant> nextCalls = Utils.convertAndSortByValue(this.plugin.getApi().getNextCallsOfEvents());
        Utils.constructEventsList(sender, nextCalls);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
