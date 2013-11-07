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
        int components = 0;
        for (int i = 0; i < emcValue.components.length; i++)
        {
            float component = emcValue.components[i];
            if (component != 0.0F)
            {
                EmcType type = EmcType.values()[i];
                switch (type)
                {
                    case OMNI:
                        value.colorR += omniR;
                        value.colorG += omniG;
                        value.colorB += omniB;
                        break;
                    case CORPOREAL:
                        value.colorR += corporealR;
                        value.colorG += corporealG;
                        value.colorB += corporealB;
                        break;
                    case KINETIC:
                        value.colorR += kineticR;
                        value.colorG += kineticG;
                        value.colorB += kineticB;
                        break;
                    case TEMPORAL:
                        value.colorR += tempotalR;
                        value.colorG += tempotalG;
                        value.colorB += tempotalB;
                        break;
                    case ESSENTIA:
                        value.colorR += essentiaR;
                        value.colorG += essentiaG;
                        value.colorB += essentiaB;
                        break;
                    case AMORPHOUS:
                        value.colorR += amorphousR;
                        value.colorG += amorphousG;
                        value.colorB += amorphousB;
                        break;
                    case VOID:
                        value.colorR += voidR;
                        value.colorG += voidG;
                        value.colorB += voidB;
                        break;
                }
                components++;
            }
        }
        value.colorR = value.colorR / components;
        value.colorG = value.colorG / components;
        value.colorB = value.colorB / components;
        return value;
    }
    
    public static RGBValue getRGB(EmcType emcType)
    {
        RGBValue value = new RGBValue(0, 0, 0);
        switch (emcType)
        {
            case OMNI:
                value.colorR += omniR;
                value.colorG += omniG;
                value.colorB += omniB;
                break;
            case CORPOREAL:
                value.colorR += corporealR;
                value.colorG += corporealG;
                value.colorB += corporealB;
                break;
            case KINETIC:
                value.colorR += kineticR;
                value.colorG += kineticG;
                value.colorB += kineticB;
                break;
            case TEMPORAL:
                value.colorR += tempotalR;
                value.colorG += tempotalG;
                value.colorB += tempotalB;
                break;
            case ESSENTIA:
                value.colorR += essentiaR;
                value.colorG += essentiaG;
                value.colorB += essentiaB;
                break;
            case AMORPHOUS:
                value.colorR += amorphousR;
                value.colorG += amorphousG;
                value.colorB += amorphousB;
                break;
            case VOID:
                value.colorR += voidR;
                value.colorG += voidG;
                value.colorB += voidB;
                break;
        }
        return value;
    }
}
