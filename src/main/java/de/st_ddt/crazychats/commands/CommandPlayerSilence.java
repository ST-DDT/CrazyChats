package de.st_ddt.crazychats.commands;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazychats.data.ChatPlayerData;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandPermissionException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.ChatHeaderProvider;
import de.st_ddt.crazyutil.ChatHelperExtended;
import de.st_ddt.crazyutil.paramitrisable.BooleanParamitrisable;
import de.st_ddt.crazyutil.paramitrisable.PlayerDataParamitrisable;
import de.st_ddt.crazyutil.paramitrisable.StringParamitrisable;
import de.st_ddt.crazyutil.paramitrisable.TabbedParamitrisable;
import de.st_ddt.crazyutil.paramitrisable.TargetDateParamitrisable;
import de.st_ddt.crazyutil.source.Localized;
import de.st_ddt.crazyutil.source.Permission;

public class CommandPlayerSilence extends CommandExecutor
{

	public CommandPlayerSilence(final CrazyChats plugin)
	{
		super(plugin);
	}

	@Override
	@Permission("crazychats.player.silence.adminbypass")
	@Localized({ "CRAZYCHATS.COMMAND.PLAYER.SILENCED.DONE $Player$ $UntilDateTime$", "CRAZYCHATS.COMMAND.PLAYER.SILENCED.MESSAGE $Muter$ $UntilDateTime$", "CRAZYCHATS.COMMAND.PLAYER.SILENCED.MESSAGE2 $Muter$ $UntilDateTime$ $Reason$" })
	public void command(final CommandSender sender, final String[] args) throws CrazyException
	{
		final Map<String, TabbedParamitrisable> params = new TreeMap<String, TabbedParamitrisable>();
		final PlayerDataParamitrisable<ChatPlayerData> playerData = new PlayerDataParamitrisable<ChatPlayerData>(owner);
		params.put("p", playerData);
		params.put("player", playerData);
		final TargetDateParamitrisable until = new TargetDateParamitrisable(60000);
		params.put("until", until);
		params.put("duration", until);
		final BooleanParamitrisable quiet = new BooleanParamitrisable(false);
		params.put("q", quiet);
		params.put("quiet", quiet);
		final StringParamitrisable reason = new StringParamitrisable(null);
		params.put("r", reason);
		params.put("reason", reason);
		final BooleanParamitrisable admin = new BooleanParamitrisable(false);
		params.put("ab", admin);
		params.put("adminbypass", admin);
		ChatHelperExtended.readParameters(args, params, playerData, until, quiet, reason);
		final ChatPlayerData data = playerData.getValue();
		if (data == null)
			throw new CrazyCommandUsageException("<player:Player> [until:Date/Duration] [quiet:Boolean] [reason:String]");
		if (admin.getValue())
			if (!sender.hasPermission("crazychats.player.silence.adminbypass"))
				throw new CrazyCommandPermissionException();
		final Date date = until.getValue();
		if (!admin.getValue())
			date.setTime(Math.min(date.getTime(), System.currentTimeMillis() + owner.getMaxSilenceTime()));
		data.setSilenced(date);
		owner.sendLocaleMessage("COMMAND.PLAYER.SILENCED.DONE", sender, data.getName(), ChatHeaderProvider.DATETIMEFORMAT.format(date));
		if (!quiet.getValue())
		{
			final Player player = data.getPlayer();
			if (player != null)
				if (player.isOnline())
					if (reason.getValue() == null)
						owner.sendLocaleMessage("COMMAND.PLAYER.SILENCED.MESSAGE", player, sender.getName(), ChatHeaderProvider.DATETIMEFORMAT.format(date));
					else
						owner.sendLocaleMessage("COMMAND.PLAYER.SILENCED.MESSAGE2", player, sender.getName(), ChatHeaderProvider.DATETIMEFORMAT.format(date), reason.getValue());
		}
		owner.getCrazyDatabase().save(data);
	}

	@Override
	public List<String> tab(final CommandSender sender, final String[] args)
	{
		final Map<String, TabbedParamitrisable> params = new HashMap<String, TabbedParamitrisable>();
		final PlayerDataParamitrisable<ChatPlayerData> playerData = new PlayerDataParamitrisable<ChatPlayerData>(owner);
		params.put("p", playerData);
		params.put("player", playerData);
		final TargetDateParamitrisable until = new TargetDateParamitrisable(60000);
		params.put("until", until);
		params.put("duration", until);
		final BooleanParamitrisable quiet = new BooleanParamitrisable(false);
		params.put("q", quiet);
		params.put("quiet", quiet);
		final StringParamitrisable reason = new StringParamitrisable(null);
		params.put("r", reason);
		params.put("reason", reason);
		final BooleanParamitrisable admin = new BooleanParamitrisable(false);
		params.put("ab", admin);
		params.put("adminbypass", admin);
		return ChatHelperExtended.tabHelp(args, params, playerData, until, quiet, reason);
	}

	@Override
	@Permission("crazychats.player.silence")
	public boolean hasAccessPermission(final CommandSender sender)
	{
		return sender.hasPermission("crazychats.player.silence");
	}
}
