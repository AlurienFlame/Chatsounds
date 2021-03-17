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

// TODO: Detect join, leave, death, and private messages and play different sounds for them.
// Use lang files to find relevant messages so works with all languages
// Filter out everythink prefixed by [Chat] first then filter through those
@Mixin(ChatHud.class)
public class ChatsoundsMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("HEAD"))
    private void addMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci) {
        System.out.println("Chat message detected! Playing sound...");
        MinecraftClient client = MinecraftClient.getInstance();
        ChatsoundsConfig config = AutoConfig.getConfigHolder(ChatsoundsConfig.class).getConfig();
        client.getSoundManager()
                .play(new PositionedSoundInstance(config.message.sound.getId(), SoundCategory.PLAYERS,
                        config.message.volume, config.message.pitch, false, 0, SoundInstance.AttenuationType.LINEAR,
                        client.player.getX(), client.player.getY(), client.player.getZ(), false));
    }
}
