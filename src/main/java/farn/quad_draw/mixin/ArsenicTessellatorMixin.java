package farn.quad_draw.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.Tessellator;
import net.modificationstation.stationapi.api.client.render.model.BakedQuad;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Matrix4f;
import net.modificationstation.stationapi.api.util.math.Vec3f;
import net.modificationstation.stationapi.api.util.math.Vec4f;
import net.modificationstation.stationapi.impl.client.render.StationTessellatorImpl;
import net.modificationstation.stationapi.mixin.render.client.TessellatorAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StationTessellatorImpl.class)
public abstract class ArsenicTessellatorMixin {

    @Shadow
    @Final
    private int[] fastVertexData;

    @Shadow
    @Final
    private Vec4f damageUV;

    @Shadow
    @Final
    private TessellatorAccessor access;

    @Shadow
    @Final
    private Tessellator self;

    @WrapMethod(method="quad")
    public void test(BakedQuad quad, float x, float y, float z, int colour0, int colour1, int colour2, int colour3, float normalX, float normalY, float normalZ, boolean spreadUV, Operation<Void> original) {
        System.arraycopy(quad.getVertexData(), 0, fastVertexData, 0, 32);
        float u1 = Float.intBitsToFloat(fastVertexData[3]);
        float v1 = Float.intBitsToFloat(fastVertexData[4]);
        float u2 = Float.intBitsToFloat(fastVertexData[11]);
        float v2 = Float.intBitsToFloat(fastVertexData[12]);
        float u3 = Float.intBitsToFloat(fastVertexData[19]);
        float v3 = Float.intBitsToFloat(fastVertexData[20]);
        float u4 = Float.intBitsToFloat(fastVertexData[27]);
        float v4 = Float.intBitsToFloat(fastVertexData[28]);
        if (spreadUV) {
            Direction facing = quad.getFace();
            Matrix4f texture = Matrix4f.translateTmp((float) access.getXOffset(), (float) access.getYOffset(), (float) access.getZOffset());
            texture.invert();
            damageUV.set(Float.intBitsToFloat(fastVertexData[0]), Float.intBitsToFloat(fastVertexData[1]), Float.intBitsToFloat(fastVertexData[2]), 1.0F);
            damageUV.transform(texture);
            damageUV.rotate(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            damageUV.rotate(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            damageUV.rotate(facing.getRotationQuaternion());
            u1 = -damageUV.getX();
            v1 = -damageUV.getY();
            damageUV.set(Float.intBitsToFloat(fastVertexData[8]), Float.intBitsToFloat(fastVertexData[9]), Float.intBitsToFloat(fastVertexData[10]), 1.0F);
            damageUV.transform(texture);
            damageUV.rotate(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            damageUV.rotate(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            damageUV.rotate(facing.getRotationQuaternion());
            u2 = -damageUV.getX();
            v2 = -damageUV.getY();
            damageUV.set(Float.intBitsToFloat(fastVertexData[16]), Float.intBitsToFloat(fastVertexData[17]), Float.intBitsToFloat(fastVertexData[18]), 1.0F);
            damageUV.transform(texture);
            damageUV.rotate(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            damageUV.rotate(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            damageUV.rotate(facing.getRotationQuaternion());
            u3 = -damageUV.getX();
            v3 = -damageUV.getY();
            damageUV.set(Float.intBitsToFloat(fastVertexData[24]), Float.intBitsToFloat(fastVertexData[25]), Float.intBitsToFloat(fastVertexData[26]), 1.0F);
            damageUV.transform(texture);
            damageUV.rotate(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            damageUV.rotate(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            damageUV.rotate(facing.getRotationQuaternion());
            u4 = -damageUV.getX();
            v4 = -damageUV.getY();
        }
        self.normal(normalX, normalY, normalZ);

        if (!access.getColorDisabled()) self.color(colour0);
        self.vertex((Float.intBitsToFloat(fastVertexData[0]) + x), (Float.intBitsToFloat(fastVertexData[1]) + y), (Float.intBitsToFloat(fastVertexData[2]) + z), u1, v1);

        if (!access.getColorDisabled()) self.color(colour1);
        self.vertex((Float.intBitsToFloat(fastVertexData[8]) + x), (Float.intBitsToFloat(fastVertexData[9]) + y), (Float.intBitsToFloat(fastVertexData[10]) + z), u2, v2);

        if (!access.getColorDisabled()) self.color(colour2);
        if(spreadUV) self.texture(u3, v3);
        self.vertex((Float.intBitsToFloat(fastVertexData[16]) + x), (Float.intBitsToFloat(fastVertexData[17]) + y), (Float.intBitsToFloat(fastVertexData[18]) + z), u3, v3);

        if (!access.getColorDisabled()) self.color(colour3);
        if(spreadUV) self.texture(u4, v4);
        self.vertex((Float.intBitsToFloat(fastVertexData[24]) + x), (Float.intBitsToFloat(fastVertexData[25]) + y), (Float.intBitsToFloat(fastVertexData[26]) + z), u4, v4);
    }
}
