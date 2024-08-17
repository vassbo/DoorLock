package net.vassbo.door_lock;

import net.fabricmc.api.ClientModInitializer;
import net.vassbo.door_lock.packets.DataReceiverClient;

public class DoorLockClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		DataReceiverClient.init();

		// // transparent
		// BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DISSOLVER_BLOCK, RenderLayer.getTranslucent());

		// // particle
		// ParticleFactoryRegistry.getInstance().register(ModParticles.CRYSTAL, EndRodParticle.Factory::new);
	}
}