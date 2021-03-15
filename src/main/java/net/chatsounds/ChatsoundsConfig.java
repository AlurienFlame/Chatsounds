package net.chatsounds;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "chatsounds")
class ChatsoundsConfig implements ConfigData {
    boolean toggleA = true;
    boolean toggleB = false;
    
    @ConfigEntry.Gui.CollapsibleObject
    InnerStuff stuff = new InnerStuff();
    
    @ConfigEntry.Gui.Excluded
    InnerStuff invisibleStuff = new InnerStuff();
    
    static class InnerStuff {
        int a = 0;
        int b = 1;
    }
}