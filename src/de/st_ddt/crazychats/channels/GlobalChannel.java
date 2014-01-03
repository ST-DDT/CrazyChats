package de.st_ddt.crazychats.channels;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.st_ddt.crazyutil.source.Permission;

public class GlobalChannel extends AbstractMuteableChannel
{

	public GlobalChannel()
	{
		super("Global");
		aliases.add("g");
		aliases.add("global");
	}

	@Override
	@Permission("crazychats.globalchannel.talk")
	public boolean hasTalkPermission(final Player player)
	{
		return player.hasPermission("crazychats.globalchannel.talk");
	}

	@Override
	public Set<Player> getTargets(final Player player)
	{
		final Set<Player> players = new HashSet<Player>();
		for (final Player online : Bukkit.getOnlinePlayers())
			players.add(online);
		players.removeAll(deafPlayers);
		return players;
	}

	@Override
	public String getFormat(final Player player)
	{
		return plugin.getGlobalChatFormat();
	}

	@Override
	public String toString()
	{
		return "GlobalChatChannel";
	}

	@Override
	public boolean isAffectedByServerSilence()
	{
		return true;
	}
}
