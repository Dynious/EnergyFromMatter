package dynious.mods.efm.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPortableHouse extends ModelBase
{
    // fields
    ModelRenderer Middle;
    ModelRenderer a;
    ModelRenderer b;
    ModelRenderer c;
    ModelRenderer d;
    ModelRenderer e;
    ModelRenderer f;
    ModelRenderer g;
    ModelRenderer h;
    ModelRenderer i;
    ModelRenderer j;
    ModelRenderer k;
    ModelRenderer l;
    
    float f5 = 0.0625F;
    
    public ModelPortableHouse()
    {
        textureWidth = 64;
        textureHeight = 64;
        
        Middle = new ModelRenderer(this, 0, 22);
        Middle.addBox(0F, 0F, 0F, 14, 14, 14);
        Middle.setRotationPoint(-7F, 9F, -7F);
        Middle.setTextureSize(64, 64);
        Middle.mirror = true;
        setRotation(Middle, 0F, 0F, 0F);
        a = new ModelRenderer(this, 28, 0);
        a.addBox(0F, 0F, 0F, 2, 2, 16);
        a.setRotationPoint(-6F, 24F, -8F);
        a.setTextureSize(64, 64);
        a.mirror = true;
        setRotation(a, 0F, 0F, 3.141593F);
        b = new ModelRenderer(this, 28, 0);
        b.addBox(0F, 0F, 0F, 2, 2, 16);
        b.setRotationPoint(8F, 22F, -8F);
        b.setTextureSize(64, 64);
        b.mirror = true;
        setRotation(b, 0F, 0F, 1.570796F);
        c = new ModelRenderer(this, 28, 0);
        c.addBox(0F, 0F, 0F, 2, 2, 16);
        c.setRotationPoint(-8F, 10F, -8F);
        c.setTextureSize(64, 64);
        c.mirror = true;
        setRotation(c, 0F, 0F, -1.570796F);
        d = new ModelRenderer(this, 28, 0);
        d.addBox(0F, 0F, 0F, 2, 2, 16);
        d.setRotationPoint(6F, 8F, -8F);
        d.setTextureSize(64, 64);
        d.mirror = true;
        setRotation(d, 0F, 0F, 0F);
        f = new ModelRenderer(this, 0, 0);
        f.addBox(0F, 0F, 0F, 12, 2, 2);
        f.setRotationPoint(-6F, 24F, -8F);
        f.setTextureSize(64, 64);
        f.mirror = true;
        setRotation(f, 1.570796F, 0F, 0F);
        g = new ModelRenderer(this, 0, 0);
        g.addBox(0F, 0F, 0F, 12, 2, 2);
        g.setRotationPoint(-6F, 24F, 8F);
        g.setTextureSize(64, 64);
        g.mirror = true;
        setRotation(g, 3.141593F, 0F, 0F);
        h = new ModelRenderer(this, 0, 0);
        h.addBox(0F, 0F, 0F, 12, 2, 2);
        h.setRotationPoint(-6F, 8F, -8F);
        h.setTextureSize(64, 64);
        h.mirror = true;
        setRotation(h, 0F, 0F, 0F);
        i = new ModelRenderer(this, 0, 0);
        i.addBox(0F, 0F, 0F, 12, 2, 2);
        i.setRotationPoint(-6F, 8F, 8F);
        i.setTextureSize(64, 64);
        i.mirror = true;
        setRotation(i, -1.570796F, 0F, 0F);
        j = new ModelRenderer(this, 0, 6);
        j.addBox(0F, 0F, 0F, 2, 12, 2);
        j.setRotationPoint(6F, 10F, -8F);
        j.setTextureSize(64, 64);
        j.mirror = true;
        setRotation(j, 0F, 0F, 0F);
        k = new ModelRenderer(this, 0, 6);
        k.addBox(0F, 0F, 0F, 2, 12, 2);
        k.setRotationPoint(8F, 10F, 6F);
        k.setTextureSize(64, 64);
        k.mirror = true;
        setRotation(k, 0F, -1.570796F, 0F);
        l = new ModelRenderer(this, 0, 6);
        l.addBox(0F, 0F, 0F, 2, 12, 2);
        l.setRotationPoint(-8F, 10F, -6F);
        l.setTextureSize(64, 64);
        l.mirror = true;
        setRotation(l, 0F, 1.570796F, 0F);
        e = new ModelRenderer(this, 0, 6);
        e.addBox(0F, 0F, 0F, 2, 12, 2);
        e.setRotationPoint(-6F, 10F, 8F);
        e.setTextureSize(64, 64);
        e.mirror = true;
        setRotation(e, 0F, 3.141593F, 0F);
    }
    
    /*
     * public void render(Entity entity, float f, float f1, float f2, float f3,
     * float f4, float f5) { super.render(entity, f, f1, f2, f3, f4, f5);
     * setRotationAngles(f, f1, f2, f3, f4, f5, entity); Middle.render(f5);
     * Side1.render(f5); Side2.render(f5); Side3.render(f5); Side4.render(f5);
     * Side5.render(f5); Side6.render(f5); Side7.render(f5); Side8.render(f5);
     * Side9.render(f5); Side10.render(f5); Side11.render(f5);
     * Side12.render(f5); }
     */
    public void renderFrame()
    {
        a.render(f5);
        b.render(f5);
        c.render(f5);
        d.render(f5);
        e.render(f5);
        f.render(f5);
        g.render(f5);
        h.render(f5);
        i.render(f5);
        j.render(f5);
        k.render(f5);
        l.render(f5);
    }
    
    public void renderMiddle()
    {
        Middle.render(f5);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3,
            float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
    
}
