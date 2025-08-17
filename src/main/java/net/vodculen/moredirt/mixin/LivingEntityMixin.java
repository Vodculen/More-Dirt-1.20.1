package net.vodculen.moredirt.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.vodculen.moredirt.util.DrownableEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements DrownableEntity {
	@Unique
	private boolean isInMuskeg = false;

	// This counts down the time that the player is "stunned"
	@Inject(method = "tick", at = @At("HEAD"))
	private void isInMuskegTicks(CallbackInfo callbackInfo) {
		if (this.isInMuskeg) {
			LivingEntity entity = (LivingEntity) (Object) this;

			World world = entity.getWorld();
			if (world != null && !world.isClient()) {
				if (world.getTime() % 10L == 0) {
					int air = entity.getAir();
					
					if (air > 0) {
						entity.setAir(air - 1);
					} else {
						entity.damage(world.getDamageSources().drown(), 0.5F);
					}
				}
			}
		}
	}

	public boolean isInMuskeg() {
		return this.isInMuskeg;
	}

	public boolean inMuskeg(boolean inMuskeg) {
		return this.isInMuskeg = inMuskeg;
	}
}