package net.chatsounds;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

// TODO: Reset to defaults button for each sound
@Config(name = "chatsounds")
public class ChatsoundsConfig implements ConfigData {

    // Message
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public MessageConfig message = new MessageConfig();

    // Private Message
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public PmConfig pm = new PmConfig();

    // TODO: Leave Message
    // TODO: Join Message
    // TODO: Death message

    public static class MessageConfig{
        
        boolean enabled = true;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float volume = 1f;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float pitch = .3f;
        Sounds sound = Sounds.ENTITY_ITEM_PICKUP;

        public PositionedSoundInstance getChatSound(double x, double y, double z) {
            return new PositionedSoundInstance(this.sound.getId(), SoundCategory.PLAYERS, this.volume, this.pitch, false, 0, SoundInstance.AttenuationType.LINEAR, x, y, z, false);
        }
    }

    public static class PmConfig{
        
        boolean enabled = true;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float volume = 1f;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float pitch = 0.3f;
        Sounds sound = Sounds.BLOCK_ANVIL_LAND;

        public PositionedSoundInstance getChatSound(double x, double y, double z) {
            return new PositionedSoundInstance(this.sound.getId(), SoundCategory.PLAYERS, this.volume, this.pitch, false, 0, SoundInstance.AttenuationType.LINEAR, x, y, z, false);
        }
    }
    
    public enum Sounds {
        // TODO: make every sound in the game available
        // List<String> messageSound = Arrays.asList(Registry.SOUND_EVENT.getIds().toArray()).stream().map(item -> item.getPath()).collect(Collectors.toList());
        // List<String> messageSound = Arrays.asList("entity.item.pickup", "entity.experience_orb.pickup", "entity.arrow.hit_player", "block.beehive.enter", "block.lava.pop");
        ENTITY_ITEM_PICKUP(SoundEvents.ENTITY_ITEM_PICKUP),
        ENTITY_EXPERIENCE_ORB_PICKUP(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP),
        ENTITY_ARROW_HIT_PLAYER(SoundEvents.ENTITY_ARROW_HIT_PLAYER),
        BLOCK_ANVIL_LAND(SoundEvents.BLOCK_ANVIL_LAND);

        private final String label;
        private final Identifier id;

        Sounds(final SoundEvent event) {
            this.id = event.getId();
            this.label = id.toString();
        }
    
        @Override
        public String toString() {
            return label;
        }

        public Identifier getId() {
            return this.id;
        }
    }
}