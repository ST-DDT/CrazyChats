package de.st_ddt.crazychats.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.ChatHelper;
import de.st_ddt.crazyutil.source.Localized;
import de.st_ddt.crazyutil.source.Permission;

public class CommandGroupPrefix extends CommandExecutor
{

	public CommandGroupPrefix(final CrazyChats plugin)
	{
		super(plugin);
	}

	@Override
	@Localized({ "CRAZYCHATS.COMMAND.GROUP.PREFIX.DELETED $Group$", "CRAZYCHATS.COMMAND.GROUP.PREFIX.SET $Group$ $Prefix$" })
	public void command(final CommandSender sender, final String[] args) throws CrazyException
	{
		if (args.length == 1)
		{
			owner.getGroupPrefixes().remove(args[0]);
			owner.sendLocaleMessage("COMMAND.GROUP.PREFIX.DELETED", sender, args[0]);
			owner.saveConfiguration();
		}
		else if (args.length == 2)
		{
			owner.getGroupPrefixes().put(args[0], ChatHelper.colorise(args[1]));
			owner.sendLocaleMessage("COMMAND.GROUP.PREFIX.SET", sender, args[0], ChatHelper.colorise(args[1]) + sender.getName());
			owner.saveConfiguration();
		}
		else
			throw new CrazyCommandUsageException("<Group> [Prefix]");
	}

	@Override
	public List<String> tab(final CommandSender sender, final String[] args)
	{
		if (args.length != 1)
			return null;
		final String name = args[0].toLowerCase();
		final List<String> res = new LinkedList<String>();
		for (final String group : owner.getGroupPrefixes().keySet())
			if (group.toLowerCase().startsWith(name))
				res.add(group);
		return res;
	}

	@Override
	@Permission("crazychats.group.prefix")
	public boolean hasAccessPermission(final CommandSender sender)
	{
		return sender.hasPermission("crazychats.group.prefix");
	}
}
