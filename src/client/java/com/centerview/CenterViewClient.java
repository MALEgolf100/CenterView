package com.centerview;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class CenterViewClient implements ClientModInitializer {
	// Key binding category
	private static final KeyBinding.Category CENTER_VIEW_CATEGORY = KeyBinding.Category.create(Identifier.of("centerview", "main"));

	// Key binding
	private static KeyBinding centerViewKey;

	private boolean isYAxisLocked = false;

	@Override
	public void onInitializeClient() {
		// Register the key binding
		centerViewKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.centerview.center_view",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C,
				CENTER_VIEW_CATEGORY
		));

		// Register the tick event handler
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (centerViewKey.isPressed()) {
					lockYAxis(client);
				} else if (isYAxisLocked) {
					unlockYAxis(client);
				}
			}
		});
	}

	private void lockYAxis(MinecraftClient client) {
		if (!isYAxisLocked) {
			client.player.setPitch(0.0F); // Center the camera vertically
			isYAxisLocked = true;
		}
		// Keep pitch locked
		client.player.setPitch(0.0F);
	}

	private void unlockYAxis(MinecraftClient client) {
		isYAxisLocked = false;
	}
}
