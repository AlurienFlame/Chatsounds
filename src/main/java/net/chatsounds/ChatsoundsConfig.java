package net.chatsounds;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

@Config(name = "chatsounds")
public class ChatsoundsConfig implements ConfigData {
    // Sounds: message, pm, leave, join, death
    // for each sound
        // toggle sound
        // volume
        // pitch
        // sound to use
        // restore defaults?

    // Message
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public
    MessageConfig message = new MessageConfig();

    // TODO: interface or something for the different sound configs to inherit from
    public static class MessageConfig {
        boolean enabled = true;
        @ConfigEntry.BoundedDiscrete(max = 1)
        public float volume = 1f;
        @ConfigEntry.BoundedDiscrete(max = 1)
        public float pitch = .3f;
        // String messageSound = "entity.item.pickup";
        // List<String> messageSound = Arrays.asList(Registry.SOUND_EVENT.getIds().toArray()).stream().map(item -> item.getPath()).collect(Collectors.toList());
        // List<String> messageSound = Arrays.asList("entity.item.pickup", "entity.experience_orb.pickup", "entity.arrow.hit_player", "block.beehive.enter", "block.lava.pop");
        
        public Sounds sound = Sounds.ENTITY_ITEM_PICKUP;
    }
    
    public enum Sounds {
        // TODO: make every sound in the game available
        ENTITY_ITEM_PICKUP(SoundEvents.ENTITY_ITEM_PICKUP),
        ENTITY_EXPERIENCE_ORB_PICKUP(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP),
        ENTITY_ARROW_HIT_PLAYER(SoundEvents.ENTITY_ARROW_HIT_PLAYER);

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