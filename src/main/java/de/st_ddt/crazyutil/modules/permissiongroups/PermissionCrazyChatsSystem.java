package de.st_ddt.crazyutil.modules.permissiongroups;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import de.st_ddt.crazychats.CrazyChats;

public class PermissionCrazyChatsSystem extends NoPermissionSystem
{

	private final CrazyChats plugin = CrazyChats.getPlugin();

	@Override
	public String getName()
	{
		return "CrazyChats";
	}

	@Override
	public String getGroupPrefix(final Player player)
	{
		final Map<String, String> groupPrefixes = plugin.getGroupPrefixes();
		for (final Entry<String, String> entry : groupPrefixes.entrySet())
			if (!entry.getKey().equals("nogroup"))
				if (hasGroup(player, entry.getKey()))
					return entry.getValue();
		return groupPrefixes.get("nogroup");
	}

	@Override
	public String getGroupSuffix(final Player player)
	{
		final Map<String, String> groupSuffixes = plugin.getGroupSuffixes();
		for (final Entry<String, String> entry : groupSuffixes.entrySet())
			if (!entry.getKey().equals("nogroup"))
				if (hasGroup(player, entry.getKey()))
					return entry.getValue();
		return groupSuffixes.get("nogroup");
	}
}
