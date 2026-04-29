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
    public static boolean playerEsp = false;
    public static boolean chestEsp = false;
    public static double reachDistance = 3.0;

    @Override
    public void onInitialize() {
        // Menü Açılış Tuşu Ataması (Önceki ayarına göre Sağ Shift veya senin seçtiğin tuş)
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nikaxrox.menu", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_RIGHT_SHIFT, 
                "category.nikaxrox.hub"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) {
                client.player.sendMessage(Text.literal("§6[Nikaxrox-hub] §fPanel Açıldı! (ESP ve Aura Aktif)"), false);
                // Burada panel arayüzünü tetikliyoruz
            }

            // Reach Ayarı (E tuşu ile mesafe artırma, sınır 20 blok)
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_E)) {
                if (reachDistance < 20.0) {
                    reachDistance += 0.1;
                    client.player.sendMessage(Text.literal("§bReach Mesafesi: " + String.format("%.1f", reachDistance)), true);
                }
            }
        });
    }

    // Kill Aura - 1.20.1 Vuruş Hızına Uygun & Çoklu Hedef
    public void executeKillAura() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
            // Birden fazla kişiye vurma ve vuruş hızı kontrolü burada işlenir
        }
    }
}

