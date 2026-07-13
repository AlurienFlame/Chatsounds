package io.github.alurienflame.chatsounds;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;

@Config(name = "chatsounds")
public class ChatsoundsConfig implements ConfigData {

    // Join
    @ConfigEntry.Gui.CollapsibleObject
    public SoundConfig join = new SoundConfig("minecraft:block.barrel.open", 1f, 1f);

    // Leave
    @ConfigEntry.Gui.CollapsibleObject
    public SoundConfig leave = new SoundConfig("minecraft:block.barrel.close", 1f, 1f);

    // Death
    @ConfigEntry.Gui.CollapsibleObject
    public SoundConfig death = new SoundConfig("minecraft:block.bell.use", 1f, 0.1f);

    // Private Message
    @ConfigEntry.Gui.CollapsibleObject
    public SoundConfig pm = new SoundConfig("minecraft:entity.villager.work_cartographer", 1f, 1f);

    // Advancement announcement
    @ConfigEntry.Gui.CollapsibleObject
    public SoundConfig advancement = new SoundConfig("minecraft:entity.experience_orb.pickup", 1f, 1f);

    // Message
    @ConfigEntry.Gui.CollapsibleObject
    public SoundConfig message = new SoundConfig("minecraft:entity.item.pickup", 0.3f, 0.3f);

    // TODO: "Preview sound" button.

    public static class SoundConfig {

        public boolean enabled = true;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float volume;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float pitch;
        // Any id the sound manager knows, including modded sounds. Rendered as
        // a searchable dropdown by the gui provider registered in Chatsounds.
        String sound;

        public SoundConfig() {
        }

        SoundConfig(String sound, float volume, float pitch) {
            this.sound = sound;
            this.volume = volume;
            this.pitch = pitch;
        }

        public SimpleSoundInstance getChatSound() {
            Identifier id = sound == null ? null : Identifier.tryParse(sound);
            if (id == null) {
                return null;
            }
            return new SimpleSoundInstance(id, SoundSource.PLAYERS, this.volume, this.pitch, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, 0, 0, 0, true);
        }
    }
}
