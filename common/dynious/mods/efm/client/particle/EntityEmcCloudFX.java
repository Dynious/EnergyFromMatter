package dynious.mods.efm.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import dynious.mods.efm.lib.Resources;

public class EntityEmcCloudFX extends EntityFX
{
    
    public EntityEmcCloudFX(World world, float posX, float posY,
            float posZ, float scale, int colorR, int colorG,
            int colorB)
    {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        this.particleRed = colorR;
        this.particleGreen = colorG;
        this.particleBlue = colorB;
        
        setSize(10F, 10F);
        this.particleScale = scale/819.2F;
        this.noClip = true;
        
        this.particleMaxAge = Integer.MAX_VALUE;
    }
    
    @Override
    public void onUpdate()
    {
        
    }
    
    public void setSize(float scale)
    {
        this.particleScale = scale/819.2F;
    }
    
    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1,
            float f2, float f3, float f4, float f5)
    {
        tessellator.draw();
        GL11.glPushMatrix();
        
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        Minecraft.getMinecraft().renderEngine
                .bindTexture(Resources.PARTICLE_BEAM);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        
        float scale = 0.1F
                * this.particleScale;
        float x = (float) (this.prevPosX + (this.posX - this.prevPosX) * f - EntityFX.interpPosX);
        float y = (float) (this.prevPosY + (this.posY - this.prevPosY) * f - EntityFX.interpPosY);
        float z = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * f - EntityFX.interpPosZ);
        float color = 1.0F;
        
        tessellator.startDrawingQuads();
        tessellator.setBrightness(240);
        
        tessellator.setColorRGBA((int) (this.particleRed * color),
                (int) (this.particleGreen * color),
                (int) (this.particleBlue * color), 255);
        tessellator.addVertexWithUV(x - f1 * scale - f4 * scale,
                y - f2 * scale, z - f3 * scale - f5 * scale, 0.0D, 1.0D);
        tessellator.addVertexWithUV(x - f1 * scale + f4 * scale,
                y + f2 * scale, z - f3 * scale + f5 * scale, 1.0D, 1.0D);
        tessellator.addVertexWithUV(x + f1 * scale + f4 * scale,
                y + f2 * scale, z + f3 * scale + f5 * scale, 1.0D, 0.0D);
        tessellator.addVertexWithUV(x + f1 * scale - f4 * scale,
                y - f2 * scale, z + f3 * scale - f5 * scale, 0.0D, 0.0D);
        
        tessellator.draw();
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        
        GL11.glPopMatrix();
        Minecraft.getMinecraft().renderEngine
                .bindTexture(Resources.VANILLA_PARTICLES);
        tessellator.startDrawingQuads();
    }
}
