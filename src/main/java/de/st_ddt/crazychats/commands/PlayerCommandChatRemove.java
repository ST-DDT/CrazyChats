package de.st_ddt.crazychats.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazychats.channels.PrivateChannel;
import de.st_ddt.crazychats.data.ChatPlayerData;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandNoSuchException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.ChatHelper;
import de.st_ddt.crazyutil.paramitrisable.OfflinePlayerParamitrisable;
import de.st_ddt.crazyutil.paramitrisable.VisiblePlayerParamitrisable;
import de.st_ddt.crazyutil.source.Localized;

public class PlayerCommandChatRemove extends PlayerCommandExecutor
{

	public PlayerCommandChatRemove(final CrazyChats plugin)
	{
		super(plugin);
	}

	@Override
	@Localized({ "CRAZYCHATS.CHANNEL.CHANGED $Channel$", "CRAZYCHATS.COMMAND.CHANNEL.PRIVATE.TARGET.REMOVED $Players$" })
	public void command(final Player player, final String[] args) throws CrazyException
	{
		final ChatPlayerData data = owner.getPlayerData(player);
		final PrivateChannel channel = data.getPrivateChannel();
		final Set<Player> targets = channel.getTargets(null);
		if (args.length == 0)
			throw new CrazyCommandUsageException("<Player...>");
		else
		{
			final List<Player> targetList = new ArrayList<Player>();
			for (final String arg : args)
			{
				final Player target = Bukkit.getPlayerExact(arg);
				if (target == null || !player.canSee(target))
					throw new CrazyCommandNoSuchException("Player", arg);
				targetList.add(target);
			}
			targets.removeAll(targetList);
			if (data.getCurrentChannel() != channel)
			{
				data.setCurrentChannel(channel);
				owner.sendLocaleMessage("CHANNEL.CHANGED", player, channel.getName());
			}
			owner.sendLocaleMessage("COMMAND.CHANNEL.PRIVATE.TARGET.REMOVED", player, ChatHelper.listingString(OfflinePlayerParamitrisable.getPlayerNames(targetList)));
		}
	}

	@Override
	public List<String> tab(final Player player, final String[] args)
	{
		return VisiblePlayerParamitrisable.tabHelp(player, args[args.length - 1]);
	}
}
