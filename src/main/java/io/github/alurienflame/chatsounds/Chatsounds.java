package io.github.alurienflame.chatsounds;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;

public class Chatsounds implements ClientModInitializer {

    public String latestMessage;
    public static ChatsoundsConfig config;

    @Override
    public void onInitializeClient() {
        // Setup config
        ConfigHolder<ChatsoundsConfig> holder = AutoConfig.register(ChatsoundsConfig.class, GsonConfigSerializer::new);
        config = holder.getConfig();
        holder.registerLoadListener((manager, newData) -> {
            config = newData;
            return InteractionResult.SUCCESS;
        });
        holder.registerSaveListener((manager, newData) -> {
            config = newData;
            return InteractionResult.SUCCESS;
        });

        registerSoundDropdown();
    }

    // Render SoundConfig.sound fields as a searchable dropdown of every sound id the
    // sound manager knows about (vanilla, data-driven, and modded alike), instead of
    // the plain text field AutoConfig would generate for a String.
    private static void registerSoundDropdown() {
        AutoConfigClient.getGuiRegistry(ChatsoundsConfig.class).registerPredicateProvider(
                (i13n, field, conf, defaults, guiRegistry) -> {
                    List<String> sounds = Minecraft.getInstance().getSoundManager().getAvailableSounds()
                            .stream().map(Identifier::toString).sorted().toList();
                    Set<String> known = Set.copyOf(sounds);
                    return Collections.singletonList(ConfigEntryBuilder.create()
                            .startStringDropdownMenu(Component.translatable(i13n), Utils.getUnsafely(field, conf, ""))
                            .setSelections(sounds)
                            .setSuggestionMode(true)
                            .setDefaultValue((String) Utils.getUnsafely(field, defaults))
                            .setErrorSupplier(value -> known.contains(value) ? Optional.empty()
                                    : Optional.of(Component.literal("Unknown sound: " + value)))
                            .setSaveConsumer(value -> Utils.setUnsafely(field, conf, value))
                            .build());
                },
                field -> field.getDeclaringClass() == ChatsoundsConfig.SoundConfig.class
                        && field.getName().equals("sound"));
    }
}
