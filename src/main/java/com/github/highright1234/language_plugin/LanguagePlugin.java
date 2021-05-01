package com.github.highright1234.language_plugin;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import me.pikamug.localelib.LocaleLib;
import me.pikamug.localelib.LocaleManager;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Objects;

public final class LanguagePlugin extends JavaPlugin implements CommandExecutor {

    private LocaleManager localeManager;

    @Override
    public void onEnable() {
        languageFile = new File(getPlugin(LanguagePlugin.class).getDataFolder(),"ko_kr.json");
        Objects.requireNonNull(getCommand("test")).setExecutor(this);
        LocaleLib localeLib = (LocaleLib) getServer().getPluginManager().getPlugin("LocaleLib");
        if (localeLib != null) { localeManager = localeLib.getLocaleManager(); }
        loadLanguage();
        // Plugin startup logic
    }
    private File languageFile;
    private HashMap<String, String> language;

    private void loadLanguage() {

        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(languageFile));
            language = gson.fromJson(reader, HashMap.class);
            if (language==null) {
                language = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            this.saveResource("ko_kr.json", false);
            loadLanguage();
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        sender.sendMessage(language.get(localeManager.queryMaterial(Material.DIAMOND_SWORD)));
        return true;
    }
}
