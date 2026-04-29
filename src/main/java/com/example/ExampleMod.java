package com.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ExampleMod implements ModInitializer {
    private static KeyBinding menuKey;
    public static double reachDistance = 3.0;

    @Override
    public void onInitialize() {
        // Sağ Shift ile menü bildirimi
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nikaxrox.menu", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_RIGHT_SHIFT, 
                "category.nikaxrox.hub"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                while (menuKey.wasPressed()) {
                    client.player.sendMessage(Text.literal("§6[Nikaxrox-hub] §fMod Aktif!"), false);
                }
                
                // E tuşu ile Reach (20 blok sınır)
                if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_E)) {
                    if (reachDistance < 20.0) {
                        reachDistance += 0.1;
                    }
                }
            }
        });
    }
}
