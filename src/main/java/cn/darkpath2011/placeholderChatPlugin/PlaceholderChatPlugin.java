package cn.darkpath2011.placeholderChatPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        String format = getConfig().getString("format_chat");
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            format = PlaceholderAPI.setPlaceholders(p, format);
        format = colorize(format);
        format = format.replace("{player}", p.getName()).replace("{displayname}", p.getDisplayName()).replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("{message}", message).replace("{world}", p.getWorld().getName()).replace("%", "%%");
        e.setMessage(message);
        e.setFormat(format);
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}