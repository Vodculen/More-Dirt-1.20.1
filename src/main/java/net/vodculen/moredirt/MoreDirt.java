package net.vodculen.moredirt;

import net.fabricmc.api.ModInitializer;
import net.vodculen.moredirt.blocks.ModBlocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreDirt implements ModInitializer {
	public static final String MOD_ID = "more-dirt";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
	}
}