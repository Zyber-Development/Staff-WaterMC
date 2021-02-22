package me.elb1to.watermc.user;

import me.elb1to.watermc.Staff;
import me.elb1to.watermc.utils.CC;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Elb1to
 * Project: Staff [Bungee]
 * Date: 2/20/2021 @ 3:15 PM
 */
public class StaffListener implements Listener {

	@EventHandler
	public void onProxyPing(ProxyPingEvent event) {
		if (Staff.getInstance().isMaintenance()) {
			event.getResponse().setVersion(new ServerPing.Protocol(CC.translate("&4En Mantenimiento"), 9999));
		}
		event.setResponse(event.getResponse());
	}

	@EventHandler
	public void onServerConnect(ServerConnectEvent event) {
		if (Staff.getInstance().isMaintenance()) {
			if (event.getPlayer().hasPermission("proxy.watermc.admin.bypass")) {
				return;
			}
			event.setCancelled(true);
			event.getPlayer().disconnect(CC.translate("&4&lMAINTENANCE\n&7\n&cThe server is currently in maintenance.\n&cFollow us on Twitter to stay updated:\n&b@WaterMCNetwork"));
		}
	}

	@EventHandler
	public void onServerSwitch(ServerSwitchEvent event) {
		event.getPlayer().resetTabHeader();

		if (event.getPlayer().hasPermission("proxy.watermc.staff.join")) {
			for (ProxiedPlayer staff : Staff.getInstance().getProxy().getPlayers()) {
				if (staff.hasPermission("proxy.watermc.staff.join")) {
					staff.sendMessage(CC.translate("&3[S] " + CC.getNameColor(event.getPlayer()) + event.getPlayer() + " &ajoined &7(&b" + event.getPlayer().getServer().getInfo().getName() + "&7)" + "&a from &7(&b" + event.getFrom().getName() + "&7)"));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event) {
		if (event.getPlayer().hasPermission("proxy.watermc.staff.leave")) {
			for (ProxiedPlayer staff : Staff.getInstance().getProxy().getPlayers()) {
				if (staff.hasPermission("proxy.watermc.staff.leave")) {
					staff.sendMessage(CC.translate("&3[S] " + CC.getNameColor(event.getPlayer()) + event.getPlayer() + " &cleft the network from &7(&b" + event.getPlayer().getServer().getInfo().getName() + "&7)"));
				}
			}
		}

		//if (event.getPlayer().hasPermission("proxy.watermc.staffchat") && Staff.getInstance().getStaffChatToggled().contains(event.getPlayer().getUniqueId())) {
		//	Staff.getInstance().getStaffChatToggled().remove(event.getPlayer().getUniqueId());
		//}
	}

	@EventHandler
	public void onStaffChatToggled(ChatEvent event) {
		//ProxiedPlayer player = (ProxiedPlayer) event.getSender();
		//if (!event.getMessage().equalsIgnoreCase("/tsc") || !event.getMessage().equalsIgnoreCase("/togglestaffchat") || !event.getMessage().equalsIgnoreCase("/togglesc")) {
		//	if (player.hasPermission("proxy.watermc.staffchat")) {
		//		if (Staff.getInstance().getStaffChatToggled().contains(player.getUniqueId())) {
		//			event.setCancelled(true);
		//			Staff.getInstance().getProxy().getPluginManager().dispatchCommand(player, "/sc " + event.getMessage());

		//Staff.getInstance().getProxy().getPlayers()
		//		.stream()
		//		.filter(staffP -> staffP.hasPermission("proxy.watermc.staffchat"))
		//		.collect(Collectors.toList())
		//		//.forEach(staffP -> staffP.sendMessage(CC.translate("&3[S] &b[" + player.getServer().getInfo().getName() + "] " + CC.getNameColor(player) + player + "&8: &7" + event.getMessage()))
		//		.forEach(staffP -> Staff.getInstance().getProxy().getPluginManager().dispatchCommand(player, "/sc " + event.getMessage())
		//);
		//		}
		//	}
		//}
	}
}
