package me.nikl.calendarevents;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.time.Instant;
import java.util.LinkedHashMap;

/**
 * @author Niklas Eicker, Headpat Services
 * <p>
 * Commands for CalendarEvents
 */
public class Commands implements CommandExecutor {

    private CalendarEvents plugin;

    public Commands(CalendarEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    if (!checkPermission(sender, args[0].toLowerCase())) {
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Reloading...");
                    plugin.reload();
                    sender.sendMessage(ChatColor.GREEN + "CalendarEvents was successfully reloaded");
                    return true;
                case "help":
                    if (!checkPermission(sender, args[0].toLowerCase())) {
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Commands:");
                    sender.sendMessage(ChatColor.RED + "reload: Reload the plugin.");
                    sender.sendMessage(ChatColor.RED + "list: List all CalendarEvents.");
                    sender.sendMessage(ChatColor.RED + "help: Reload the plugin.");
                    return true;
                case "list":
                    if (!checkPermission(sender, args[0].toLowerCase())) {
                        return true;
                    }
                    LinkedHashMap<String, Instant> nextCalls = Utils.convertAndSortByValue(this.plugin.getApi().getNextCallsOfEvents());
                    Utils.constructEventsList(sender, nextCalls);
                    return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "There is no such command!");
        return true;
    }

    private boolean checkPermission(CommandSender sender, String command) {
        if (!sender.hasPermission("calendarevents." + command)) {
            sender.sendMessage("[CalendarEvents] You have no permission!");
            return false;
        }
        return true;
    }
}
