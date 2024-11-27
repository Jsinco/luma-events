package dev.jsinco.luma.commands;

import dev.jsinco.luma.ThanksgivingEvent;
import dev.jsinco.luma.commands.subcommands.MineUpgrade;
import dev.jsinco.luma.commands.subcommands.ViewTokens;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements TabExecutor {

    private static final Map<String, Subcommand> SUBCOMMAND_MAP = new HashMap<>();

    public static void registerSubcommand(String name, Subcommand subcommand) {
        SUBCOMMAND_MAP.put(name, subcommand);
    }


    static {
        registerSubcommand("viewtokens", new ViewTokens());
        registerSubcommand("mineupgrade", new MineUpgrade());
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        Subcommand subcommand = SUBCOMMAND_MAP.get(args[0]);
        if (subcommand == null) {
            return false;
        } else if (subcommand.isPlayerOnly() && !(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        } else if (!sender.hasPermission(subcommand.permission())) {
            sender.sendMessage("You do not have permission to execute this command.");
            return true;
        }

        subcommand.execute(sender, args, ThanksgivingEvent.getInstance());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.copyOf(SUBCOMMAND_MAP.keySet());
        }

        Subcommand subcommand = SUBCOMMAND_MAP.get(args[0]);
        if (subcommand == null) {
            return null;
        }

        return subcommand.tabComplete(sender, args, ThanksgivingEvent.getInstance());
    }
}