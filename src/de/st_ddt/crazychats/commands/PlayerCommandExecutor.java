package de.st_ddt.crazychats.commands;

import de.st_ddt.crazychats.CrazyChats;
import de.st_ddt.crazyplugin.commands.CrazyPlayerCommandExecutor;

public abstract class PlayerCommandExecutor extends CrazyPlayerCommandExecutor<CrazyChats> implements CommandExecutorInterface
{

	public PlayerCommandExecutor(final CrazyChats plugin)
	{
		super(plugin);
	}

	@Override
	public CrazyChats getPlugin()
	{
		return plugin;
	}
}
