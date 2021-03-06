package de.st_ddt.crazychats.commands;

import org.bukkit.entity.Player;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazychats.data.ChatPlayerData;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.source.Localized;

public class PlayerCommandMutePlayer extends PlayerCommandExecutor
{

	public PlayerCommandMutePlayer(final CrazyChats plugin)
	{
		super(plugin);
	}

	@Override
	@Localized("CRAZYCHATS.COMMAND.MUTEDPLAYER $Name$")
	public void command(final Player player, final String[] args) throws CrazyException
	{
		if (args.length == 0)
			throw new CrazyCommandUsageException("<Player...>");
		final ChatPlayerData data = owner.getPlayerData(player);
		for (final String arg : args)
		{
			data.mute(arg);
			owner.sendLocaleMessage("COMMAND.MUTEDPLAYER", player, arg);
		}
		owner.getCrazyDatabase().save(data);
	}
}
