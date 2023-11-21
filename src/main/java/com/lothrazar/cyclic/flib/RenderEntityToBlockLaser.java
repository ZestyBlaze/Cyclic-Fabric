package com.lothrazar.cyclic.flib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Citation/source/author
 * <a href="https://github.com/Direwolf20-MC/MiningGadgets">...</a>
 */
public class RenderEntityToBlockLaser {
    private static float calculateLaserFlickerModifier(long gameTime) {
        return 0.9f + 0.1f * Mth.sin(gameTime * 0.99f) * Mth.sin(gameTime * 0.3f) * Mth.sin(gameTime * 0.1f);
    }

    public static void renderLaser(PoseStack pose, Player player, float ticks, InteractionHand hand) {
        renderLaser(pose, player, ticks, hand, 18, -0.02F); // default range, default speed
    }

    public static void renderLaser(PoseStack pose, Player player, float ticks, InteractionHand hand, final int range, float speedModifier) {
        Vec3 playerPos = player.getEyePosition(ticks);
        HitResult trace = player.pick(range, 0.0F, false);
        drawLasers(hand, pose, playerPos, trace, 0, 0, 0, 100F / 255f, 0F / 255f, 2F / 255f, 0.02f, player, ticks, speedModifier);
    }

    /**
     * Citation/source/author
     * <a href="https://github.com/Direwolf20-MC/MiningGadgets">...</a>
     */
    public static void drawLasers(InteractionHand activeHand, PoseStack matrix, Vec3 from, HitResult trace, double xOffset, double yOffset, double zOffset, float r, float g, float b, float thickness, Player player, float ticks, float speedModifier) {
        VertexConsumer builder;
        double distance = Math.max(1, from.subtract(trace.getLocation()).length());
        long gameTime = player.level().getGameTime();
        double v = gameTime * speedModifier;
        float additiveThickness = (thickness * 3.5f) * calculateLaserFlickerModifier(gameTime);
        float beam2r = 100f / 255f;
        float beam2g = 0f / 255f;
        float beam2b = 10f / 255f;
        Vec3 view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        matrix.pushPose();
        matrix.translate(-view.x(), -view.y(), -view.z());
        matrix.translate(from.x, from.y, from.z);
        matrix.mulPose(Axis.YP.rotationDegrees(Mth.lerp(ticks, -player.getYRot(), -player.yRotO)));
        matrix.mulPose(Axis.XP.rotationDegrees(Mth.lerp(ticks, player.getXRot(), player.xRotO)));
        //
        PoseStack.Pose matrixstack$entry = matrix.last();
        Matrix3f matrixNormal = matrixstack$entry.normal();
        Matrix4f positionMatrix = matrixstack$entry.pose();
        //additive laser beam
        builder = buffer.getBuffer(LaserRenderType.LASER_MAIN_ADDITIVE);
        drawBeam(xOffset, yOffset, zOffset, builder, positionMatrix, matrixNormal, additiveThickness, activeHand, distance, 0.5, 1, ticks, r, g, b, 0.7f);
        //main laser, colored part
        builder = buffer.getBuffer(LaserRenderType.LASER_MAIN_BEAM);
        drawBeam(xOffset, yOffset, zOffset, builder, positionMatrix, matrixNormal, thickness, activeHand, distance, v, v + distance * 1.5, ticks, r, g, b, 1f);
        //core
        builder = buffer.getBuffer(LaserRenderType.LASER_MAIN_CORE);
        drawBeam(xOffset, yOffset, zOffset, builder, positionMatrix, matrixNormal, thickness / 2, activeHand, distance, v, v + distance * 1.5, ticks, beam2r, beam2g, beam2b, 1f);
        matrix.popPose();
        //      RenderSystem.disableDepthTest();
        buffer.endBatch();
    }

    /**
     * Citation/source/author
     * <a href="https://github.com/Direwolf20-MC/MiningGadgets">...</a>
     */
    private static void drawBeam(double xOffset, double yOffset, double zOffset, VertexConsumer builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, InteractionHand hand, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {
        Vector3f vector3f = new Vector3f(0.0f, 1.0f, 0.0f);
        vector3f.mul(matrixNormalIn); //  vector3f = matrixNormalIn.transform(vector3f);
        LocalPlayer player = Minecraft.getInstance().player;
        float startXOffset = -0.20f;
        float startYOffset = -.106f;
        float startZOffset = 0.60f;
        // Adjust for fov changing
        startZOffset += (1 - player.getFieldOfViewModifier());
        if (hand == InteractionHand.OFF_HAND) {
            startYOffset = -.120f;
            startXOffset = 0.25f;
        }
        float f = (Mth.lerp(ticks, player.xRotO, player.getXRot()) - Mth.lerp(ticks, player.xBobO, player.xBob));
        float f1 = (Mth.lerp(ticks, player.yRotO, player.getYRot()) - Mth.lerp(ticks, player.yBobO, player.yBob));
        startXOffset = startXOffset + (f1 / 750);
        startYOffset = startYOffset + (f / 750);
        Vector4f vec1 = new Vector4f(startXOffset, -thickness + startYOffset, startZOffset, 1.0F);
        vec1.mul(positionMatrix);
        Vector4f vec2 = new Vector4f((float) xOffset, -thickness + (float) yOffset, (float) distance + (float) zOffset, 1.0F);
        vec2.mul(positionMatrix);
        Vector4f vec3 = new Vector4f((float) xOffset, thickness + (float) yOffset, (float) distance + (float) zOffset, 1.0F);
        vec3.mul(positionMatrix);
        Vector4f vec4 = new Vector4f(startXOffset, thickness + startYOffset, startZOffset, 1.0F);
        vec4.mul(positionMatrix);
        if (hand == InteractionHand.MAIN_HAND) {
            builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            //Rendering a 2nd time to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are....
            builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        }
        else {
            builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            //Rendering a 2nd time to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are....
            builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
            builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        }
    }
}
