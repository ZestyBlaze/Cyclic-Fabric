package com.lothrazar.cyclic;

import com.lothrazar.cyclic.config.ConfigRegistry;
import com.lothrazar.cyclic.registry.*;
import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;

public class ModCyclic implements ModInitializer {
	public static final String MODID = "cyclic";
    public static final CyclicLogger LOGGER = new CyclicLogger(LogManager.getLogger());

	@Override
	public void onInitialize() {
		ConfigRegistry cfg = new ConfigRegistry();
		cfg.setupMain();
		cfg.setupClient();
		BlockRegistry.init();
		ItemRegistry.init();
		EntityRegistry.init();
		PotionEffectRegistry.init();
		PotionRegistry.init();
		SoundRegistry.init();
		EnchantRegistry.init();
		AttributeRegistry.register();
		MaterialRegistry.init();
		PacketRegistry.registerC2S();
	}
}