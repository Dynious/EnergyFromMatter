package redmennl.mods.efm.lib;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class EmcRGBValues
{
    public static final int omniR = 255;
    public static final int omniG = 255;
    public static final int omniB = 255;
    
    public static final int corporealR = 128;
    public static final int corporealG = 128;
    public static final int corporealB = 128;
    
    public static final int kineticR = 255;
    public static final int kineticG = 0;
    public static final int kineticB = 0;
    
    public static final int tempotalR = 255;
    public static final int tempotalG = 255;
    public static final int tempotalB = 0;
    
    public static final int essentiaR = 0;
    public static final int essentiaG = 255;
    public static final int essentiaB = 255;
    
    public static final int amorphousR = 255;
    public static final int amorphousG = 170;
    public static final int amorphousB = 0;
    
    public static final int voidR = 0;
    public static final int voidG = 0;
    public static final int voidB = 0;
    
    public static RGBValue getRGB(EmcValue emcValue)
    {
        RGBValue value = new RGBValue(0, 0, 0);
        float components = 0;
        for (int i = 0; i < emcValue.components.length; i++)
        {
            float component = emcValue.components[i];
            if (component != 0.0F)
            {
                if (component < 0.1F)
                {
                    component = 0.1F;
                }
                EmcType type = EmcType.values()[i];
                switch (type)
                {
                    case OMNI:
                        value.colorR += omniR * component;
                        value.colorG += omniG * component;
                        value.colorB += omniB * component;
                        break;
                    case CORPOREAL:
                        value.colorR += corporealR * component;
                        value.colorG += corporealG * component;
                        value.colorB += corporealB * component;
                        break;
                    case KINETIC:
                        value.colorR += kineticR * component;
                        value.colorG += kineticG * component;
                        value.colorB += kineticB * component;
                        break;
                    case TEMPORAL:
                        value.colorR += tempotalR * component;
                        value.colorG += tempotalG * component;
                        value.colorB += tempotalB * component;
                        break;
                    case ESSENTIA:
                        value.colorR += essentiaR * component;
                        value.colorG += essentiaG * component;
                        value.colorB += essentiaB * component;
                        break;
                    case AMORPHOUS:
                        value.colorR += amorphousR * component;
                        value.colorG += amorphousG * component;
                        value.colorB += amorphousB * component;
                        break;
                    case VOID:
                        value.colorR += voidR * component;
                        value.colorG += voidG * component;
                        value.colorB += voidB * component;
                        break;
                }
                components += component;
            }
        }
        value.colorR = (int)(value.colorR / components);
        value.colorG = (int)(value.colorG / components);
        value.colorB = (int)(value.colorB / components);
        return value;
    }
    
    public static RGBValue getRGB(EmcType emcType)
    {
        RGBValue value = new RGBValue(0, 0, 0);
        switch (emcType)
        {
            case OMNI:
                value.colorR = omniR;
                value.colorG = omniG;
                value.colorB = omniB;
                break;
            case CORPOREAL:
                value.colorR = corporealR;
                value.colorG = corporealG;
                value.colorB = corporealB;
                break;
            case KINETIC:
                value.colorR = kineticR;
                value.colorG = kineticG;
                value.colorB = kineticB;
                break;
            case TEMPORAL:
                value.colorR = tempotalR;
                value.colorG = tempotalG;
                value.colorB = tempotalB;
                break;
            case ESSENTIA:
                value.colorR = essentiaR;
                value.colorG = essentiaG;
                value.colorB = essentiaB;
                break;
            case AMORPHOUS:
                value.colorR = amorphousR;
                value.colorG = amorphousG;
                value.colorB = amorphousB;
                break;
            case VOID:
                value.colorR = voidR;
                value.colorG = voidG;
                value.colorB = voidB;
                break;
        }
        return value;
    }
}
