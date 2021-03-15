package net.chatsounds;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "chatsounds")
class ChatsoundsConfig implements ConfigData {
    // Sounds: message, pm, leave, join, death
    // for each sound
        // toggle sound
        // volume
        // pitch
        // sound to use
        // restore defaults?

    // Message
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    MessageConfig messageConfig = new MessageConfig();

    static class MessageConfig {
        boolean enabled = true;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float volume = 1f;
        @ConfigEntry.BoundedDiscrete(max = 1)
        float pitch = .3f;
        // String messageSound = "entity.item.pickup";
        // List<String> messageSound = Arrays.asList(Registry.SOUND_EVENT.getIds().toArray()).stream().map(item -> item.getPath()).collect(Collectors.toList());
        // List<String> messageSound = Arrays.asList("entity.item.pickup", "entity.experience_orb.pickup", "entity.arrow.hit_player", "block.beehive.enter", "block.lava.pop");
        // TODO: make every sound in the game available
        // FIXME: Needs identifiers, not strings.

        public MessageSounds sound = MessageSounds.ENTITY_ITEM_PICKUP;
    }

    enum MessageSounds {
        ENTITY_ITEM_PICKUP("entity.item.pickup"),
        ENTITY_EXPERIENCE_ORB_PICKUP("entity.experience_orb.pickup"),
        ENTITY_ARROW_HIT_PLAYER("entity.arrow.hit_player");

        private final String label;

        MessageSounds(final String label) {
            this.label = label;
        }
    
        @Override
        public String toString() {
            return label;
        }
    }
}