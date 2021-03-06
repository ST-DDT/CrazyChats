package de.st_ddt.crazychats.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandNoSuchException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandPermissionException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.ChatHelper;
import de.st_ddt.crazyutil.locales.CrazyLocale;
import de.st_ddt.crazyutil.paramitrisable.PlayerParamitrisable;
import de.st_ddt.crazyutil.source.Localized;
import de.st_ddt.crazyutil.source.Permission;

public class CommandClearChat extends CommandExecutor
{

	public CommandClearChat(final CrazyChats plugin)
	{
		super(plugin);
	}

	@SuppressWarnings("deprecation")
	@Override
	@Permission({ "crazychats.clearchat", "crazychats.chatspy" })
	@Localized({ "CRAZYCHATS.COMMAND.CLEARCHAT.CLEAR", "CRAZYCHATS.COMMAND.CLEARCHAT.MESSAGE $Clearer$", "CRAZYCHATS.COMMAND.CLEARCHAT.DONE $Clearer$ $Cleared$" })
	public void command(final CommandSender sender, final String[] args) throws CrazyException
	{
		final CrazyLocale clear = owner.getLocale().getLanguageEntry("COMMAND.CLEARCHAT.CLEAR");
		final CrazyLocale cleared = owner.getLocale().getLanguageEntry("COMMAND.CLEARCHAT.MESSAGE");
		final int count = owner.getClearChatLength();
		if (args.length == 0)
			if (sender instanceof Player)
				Bukkit.getScheduler().scheduleAsyncDelayedTask(owner, new Runnable()
				{

					@Override
					public void run()
					{
						clearChat((Player) sender, count, sender, clear, cleared);
					}
				});
			else
				throw new CrazyCommandUsageException("<Player/*>");
		else if (args.length == 1 && args[0].equalsIgnoreCase(sender.getName()))
			if (sender instanceof Player)
				Bukkit.getScheduler().scheduleAsyncDelayedTask(owner, new Runnable()
				{

					@Override
					public void run()
					{
						clearChat((Player) sender, count, sender, clear, cleared);
					}
				});
			else
				throw new CrazyCommandUsageException("<Player/*>");
		else if (!sender.hasPermission("crazychats.clearchat"))
			throw new CrazyCommandPermissionException();
		else if (args.length == 1 && args[0].equals("*"))
		{
			final Player[] players = Bukkit.getOnlinePlayers();
			final CrazyLocale done = owner.getLocale().getLanguageEntry("COMMAND.CLEARCHAT.DONE");
			Bukkit.getScheduler().scheduleAsyncDelayedTask(owner, new Runnable()
			{

				@Override
				public void run()
				{
					for (final Player player : players)
						if (player.hasPermission("crazychats.chatspy"))
							done.sendMessage(player, sender.getName(), "*ALL*");
						else
							clearChat(player, count, sender, clear, cleared);
					if (!sender.hasPermission("crazychats.chatspy") || !(sender instanceof Player))
						done.sendMessage(sender, sender.getName(), "*ALL*");
					if (!(sender instanceof ConsoleCommandSender))
						done.sendMessage(Bukkit.getConsoleSender(), sender.getName(), "*ALL*");
				}
			});
		}
		else
		{
			final Player[] players = new Player[args.length];
			for (int i = 0; i < args.length; i++)
				if ((players[i] = Bukkit.getPlayerExact(args[i])) == null)
					throw new CrazyCommandNoSuchException("Player", args[i]);
			final CrazyLocale done = owner.getLocale().getLanguageEntry("COMMAND.CLEARCHAT.DONE");
			Bukkit.getScheduler().scheduleAsyncDelayedTask(owner, new Runnable()
			{

				@Override
				public void run()
				{
					for (final Player player : players)
						clearChat(player, count, sender, clear, cleared);
					done.sendMessage(sender, sender.getName(), PlayerParamitrisable.getPlayerNames(players));
					if (!(sender instanceof ConsoleCommandSender))
						done.sendMessage(Bukkit.getConsoleSender(), sender.getName(), "*ALL*");
				}
			});
		}
	}

	private void clearChat(final Player player, final int count, final CommandSender clearer, final CrazyLocale clearMessage, final CrazyLocale clearedMessage)
	{
		clearChat(player, count, clearMessage.getLanguageText(player), ChatHelper.putArgs(clearedMessage.getLanguageText(player), clearer.getName()));
	}

	private void clearChat(final Player player, final int count, final String clearMessage, final String clearedMessage)
	{
		for (int i = 0; i < count; i++)
			player.sendMessage(clearMessage);
		player.sendMessage(clearedMessage);
	}

	@Override
	public List<String> tab(final CommandSender sender, final String[] args)
	{
		return PlayerParamitrisable.tabHelp(args[args.length - 1]);
	}
}
