package de.st_ddt.crazychats.commands;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.command.CommandSender;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazychats.data.ChatPlayerData;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.ChatHelperExtended;
import de.st_ddt.crazyutil.paramitrisable.OfflinePlayerParamitrisable;
import de.st_ddt.crazyutil.paramitrisable.PlayerDataParamitrisable;
import de.st_ddt.crazyutil.paramitrisable.TabbedParamitrisable;
import de.st_ddt.crazyutil.source.Localized;
import de.st_ddt.crazyutil.source.Permission;

public class CommandPlayerMute extends CommandExecutor
{

	public CommandPlayerMute(final CrazyChats plugin)
	{
		super(plugin);
	}

	@Override
	@Localized("CRAZYCHATS.COMMAND.PLAYER.MUTED $Player$ $MutedPlayer$")
	public void command(final CommandSender sender, final String[] args) throws CrazyException
	{
		final Map<String, TabbedParamitrisable> params = new TreeMap<String, TabbedParamitrisable>();
		final PlayerDataParamitrisable<ChatPlayerData> playerData = new PlayerDataParamitrisable<ChatPlayerData>(owner);
		params.put("p", playerData);
		params.put("player", playerData);
		final OfflinePlayerParamitrisable muted = new OfflinePlayerParamitrisable(null);
		params.put("m", muted);
		params.put("muted", muted);
		ChatHelperExtended.readParameters(args, params, playerData, muted);
		final ChatPlayerData data = playerData.getValue();
		if (data == null || muted.getValue() == null)
			throw new CrazyCommandUsageException("<player:Player> <muted:Player>");
		data.mute(muted.getValue());
		owner.sendLocaleMessage("COMMAND.PLAYER.MUTED", sender, data.getName(), muted.getValue().getName());
		owner.getCrazyDatabase().save(data);
	}

	@Override
	public List<String> tab(final CommandSender sender, final String[] args)
	{
		final Map<String, TabbedParamitrisable> params = new TreeMap<String, TabbedParamitrisable>();
		final PlayerDataParamitrisable<ChatPlayerData> playerData = new PlayerDataParamitrisable<ChatPlayerData>(owner);
		params.put("p", playerData);
		params.put("player", playerData);
		final OfflinePlayerParamitrisable muted = new OfflinePlayerParamitrisable(null);
		params.put("m", muted);
		params.put("muted", muted);
		return ChatHelperExtended.tabHelp(args, params, playerData, muted);
	}

	@Override
	@Permission("crazychats.player.mute")
	public boolean hasAccessPermission(final CommandSender sender)
	{
		return sender.hasPermission("crazychats.player.mute");
	}
}
