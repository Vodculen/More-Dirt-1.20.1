package net.vodculen.moredirt;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.vodculen.moredirt.blocks.ModBlocks;

public class MoreDirtClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MUSKEG, RenderLayer.getTranslucent());
	}
}
