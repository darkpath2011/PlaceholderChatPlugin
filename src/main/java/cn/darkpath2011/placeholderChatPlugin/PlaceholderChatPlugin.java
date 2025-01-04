package cn.darkpath2011.placeholderChatPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaceholderChatPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("PlaceholderAPI 插件未安装！此插件将无法正常工作。");
        } else {
            getLogger().info("PlaceholderAPI 插件已成功加载。");
        }
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {}

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        String format = getConfig().getString("format_chat");
        String cleanMessage = message.replace("%", "");
        if (cleanMessage.trim().isEmpty()) {
            event.setCancelled(true);
            return;
        }
        String processedMessage = format.replace("{player}", player.getName());
        processedMessage = PlaceholderAPI.setPlaceholders(player, processedMessage);
        processedMessage = processedMessage.replace("{message}", cleanMessage);
        event.setFormat(processedMessage);
    }
}
