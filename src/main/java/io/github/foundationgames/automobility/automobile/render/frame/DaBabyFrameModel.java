package io.github.foundationgames.automobility.automobile.render.frame;

import dev.monarkhes.myron.api.Myron;
import io.github.foundationgames.automobility.Automobility;
import io.github.foundationgames.automobility.util.AUtils;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class DaBabyFrameModel extends Model {
    // Credit to https://github.com/Sk3leCreeper for model
    private final BakedModel model = Myron.getModel(Automobility.id("models/misc/automobile/frame/dababy"));

    public DaBabyFrameModel(EntityRendererFactory.Context ctx) {
        super(id -> TexturedRenderLayers.getEntitySolid());
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (model == null) return;
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
        matrices.translate(0.1, 0.04, 0);
        AUtils.renderMyronObj(model, vertices, matrices, light, overlay);
        matrices.pop();
    }
}
