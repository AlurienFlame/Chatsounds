package net.chatsounds.mixin;

import net.chatsounds.ChatsoundsConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.TranslatableText;

// TODO: Detect join, leave, death, and private messages and play different sounds for them.
// Use lang files to find relevant messages so works with all languages
@Mixin(ChatHud.class)
public class ChatsoundsMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("HEAD"))
    private void addMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci) {

        ChatsoundsConfig config = AutoConfig.getConfigHolder(ChatsoundsConfig.class).getConfig();
        MinecraftClient client = MinecraftClient.getInstance();

        if (message instanceof TranslatableText) {
            String key = ((TranslatableText)message).getKey();

            switch(key) {
                case "chat.type.text":
                    // TODO: Check if config.enabled
                    client.getSoundManager().play(new PositionedSoundInstance(config.message.sound.getId(), SoundCategory.PLAYERS, config.message.volume, config.message.pitch, false, 0, SoundInstance.AttenuationType.LINEAR, client.player.getX(), client.player.getY(), client.player.getZ(), false));
                    break;
                
                case "commands.message.display.incoming":
                    client.getSoundManager().play(new PositionedSoundInstance(config.pm.sound.getId(), SoundCategory.PLAYERS, config.pm.volume, config.pm.pitch, false, 0, SoundInstance.AttenuationType.LINEAR, client.player.getX(), client.player.getY(), client.player.getZ(), false));
                    break;

                default:
                    System.out.println(String.format("Chatsounds failed to recognize translation key: %s", key));
                    client.getSoundManager().play(new PositionedSoundInstance(config.message.sound.getId(), SoundCategory.PLAYERS, config.message.volume, config.message.pitch, false, 0, SoundInstance.AttenuationType.LINEAR, client.player.getX(), client.player.getY(), client.player.getZ(), false));
            }

        } else {
            System.out.println("Chatsounds failed to find translation key.");
            client.getSoundManager().play(new PositionedSoundInstance(config.message.sound.getId(), SoundCategory.PLAYERS, config.message.volume, config.message.pitch, false, 0, SoundInstance.AttenuationType.LINEAR, client.player.getX(), client.player.getY(), client.player.getZ(), false));
        }
    }
}
