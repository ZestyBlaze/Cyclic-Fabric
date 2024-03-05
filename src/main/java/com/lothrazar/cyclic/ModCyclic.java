package com.lothrazar.cyclic;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.lothrazar.cyclic.config.ConfigRegistry;
import com.lothrazar.cyclic.potion.CyclicMobEffect;
import com.lothrazar.cyclic.registry.*;
import dev.zestyblaze.zestylib.events.living.LivingEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.effect.MobEffectInstance;
import org.apache.logging.log4j.LogManager;

import java.util.Iterator;

public class ModCyclic implements ModInitializer {
	public static final String MODID = "cyclic";
    public static final CyclicLogger LOGGER = new CyclicLogger(LogManager.getLogger());

	@Override
	public void onInitialize() {
		MixinExtrasBootstrap.init();
		ConfigRegistry cfg = new ConfigRegistry();
		cfg.setupMain();
		cfg.setupClient();
		AttributeRegistry.init();
		BlockRegistry.init();
		ItemRegistry.init();
		TileRegistry.init();
		EntityRegistry.init();
		PotionEffectRegistry.init();
		PotionRegistry.init();
		SoundRegistry.init();
		EnchantRegistry.init();
		MenuTypeRegistry.init();
		MaterialRegistry.init();
		setupEvents();
	}

	private static void setupEvents() {
		LivingEvent.LivingTickEvent.TICK.register(event -> {
			Iterator<MobEffectInstance> itr = event.getEntity().getActiveEffects().iterator();
			if (itr.hasNext()) {
				MobEffectInstance mobEffect = itr.next();
				if (mobEffect.getEffect() instanceof CyclicMobEffect cyclicMobEffect) {
					cyclicMobEffect.tick(event);
				}
			}
		});
	}
}