package de.st_ddt.crazychats.channels;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.st_ddt.crazyutil.source.Permission;

public class BroadcastChannel extends AbstractChannel
{

	public BroadcastChannel()
	{
		super("Broadcast");
		aliases.add("*");
		aliases.add("all");
		aliases.add("broadcast");
	}

	@Override
	@Permission("crazychats.broadcastchannel.talk")
	public boolean hasTalkPermission(final Player player)
	{
		return player.hasPermission("crazychats.broadcastchannel.talk");
	}

	@Override
	public Set<Player> getTargets(final Player player)
	{
		final Set<Player> players = new HashSet<Player>();
		for (final Player online : Bukkit.getOnlinePlayers())
			players.add(online);
		return players;
	}

	@Override
	public String getFormat(final Player player)
	{
		return plugin.getBroadcastChatFormat();
	}

	@Override
	public String toString()
	{
		return "BroadcastChatChannel";
	}

	@Override
	public boolean isAffectedByServerSilence()
	{
		return false;
	}
}
