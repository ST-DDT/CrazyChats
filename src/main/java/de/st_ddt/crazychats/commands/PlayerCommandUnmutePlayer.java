package de.st_ddt.crazychats.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazychats.data.ChatPlayerData;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.source.Localized;

public class PlayerCommandUnmutePlayer extends PlayerCommandExecutor
{

	public PlayerCommandUnmutePlayer(final CrazyChats plugin)
	{
		super(plugin);
	}

	@Override
	@Localized("CRAZYCHATS.COMMAND.UNMUTEDPLAYER $Name$")
	public void command(final Player player, final String[] args) throws CrazyException
	{
		if (args.length == 0)
			throw new CrazyCommandUsageException("<Player...>");
		final ChatPlayerData data = owner.getPlayerData(player);
		if (args.length == 1)
			if (args[0].equals("*"))
			{
				data.getMutedPlayers().clear();
				owner.sendLocaleMessage("COMMAND.UNMUTEDPLAYER", player, "*ALL*");
			}
		for (final String arg : args)
		{
			data.unmute(arg);
			owner.sendLocaleMessage("COMMAND.UNMUTEDPLAYER", player, arg);
		}
		owner.getCrazyDatabase().save(data);
	}

	@Override
	public List<String> tab(final Player player, final String[] args)
	{
		final String last = args[args.length - 1].toLowerCase();
		final List<String> res = new LinkedList<String>();
		for (final String name : owner.getPlayerData(player).getMutedPlayers())
			if (name.toLowerCase().startsWith(last))
				res.add(name);
		return res;
	}
}
