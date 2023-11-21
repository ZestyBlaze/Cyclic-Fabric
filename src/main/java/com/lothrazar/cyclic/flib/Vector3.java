package com.lothrazar.cyclic.flib;

import net.minecraft.world.entity.Entity;

/**
 * This class was created by <ChickenBones>. It's distributed as part of the Botania Mod. Get the Source Code in github: <a href="https://github.com/Vazkii/Botania">...</a>
 * <p>
 * Botania is Open Source and distributed under the Botania License: <a href="http://botaniamod.net/license.php">...</a>
 * <p>
 * <p>
 * Thank you to ChickenBones and Vaskii for this file Imported into Scepter Powers by Lothrazar, December 11, 2015 with minimal modification (I ported it from minecraft forge 1.7.10 to 1.8.8). This
 * mod is open source (MIT), please respect this and the licenses and authors above. References: <a href="https://github.com/Chicken-Bones/CodeChickenLib/blob/master/src/codechicken/">...</a> lib/vec/Vector3.java
 * <a href="https://github.com/Vazkii/Botania/blob/">...</a> 9cf015ee972bb8568f65128fa7b84c12c4a7cfff/src/main/java/vazkii/botania/common/ core/helper/Vector3.java
 *
 *
 * @see com/lothrazar/cyclic/data/
 */
public class Vector3 {

    public double x;
    public double y;
    public double z;

    public Vector3(double d, double d1, double d2) {
        x = d;
        y = d1;
        z = d2;
    }

    public Vector3(Entity e) {
        this(e.xOld, e.yOld, e.zOld);
    }

    public double mag() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void normalize() {
        double d = mag();
        if (d != 0) {
            multiply(1 / d);
        }
    }

    public Vector3(Vector3 vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    public void multiply(double d) {
        x *= d;
        y *= d;
        z *= d;
    }

    public Vector3 copy() {
        return new Vector3(this);
    }

    public Vector3 subtract(Vector3 vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }
}
