package com.lothrazar.cyclic.util;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class HarvestUtil {
    public static IntegerProperty getAgeProp(BlockState blockState) {
        if (blockState.getBlock() instanceof CropBlock crops) {
            //better mod compatibility if they dont use 'age'
            return crops.getAgeProperty();
        }
        String age = CropBlock.AGE.getName();
        for (Property<?> p : blockState.getProperties()) {
            if (p != null && p.getName() != null
                    && p instanceof IntegerProperty &&
                    p.getName().equalsIgnoreCase(age)) {
                return (IntegerProperty) p;
            }
        }
        //IGrowable is useless here, i tried. no way to tell if its fully grown, or what age/stage its in
        return null;
    }
}
