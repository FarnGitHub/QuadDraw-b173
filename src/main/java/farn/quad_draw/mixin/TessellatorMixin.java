package farn.quad_draw.mixin;

import net.minecraft.client.render.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Tessellator.class, priority = 9999)
public class TessellatorMixin {

    @Shadow
    private static boolean TRIANGLE_MODE;

    @Inject(method="<clinit>", at = @At("TAIL"))
    private static void quad_draw_changeTMtoFalse(CallbackInfo ci) {
        TRIANGLE_MODE = false;
    }
}
