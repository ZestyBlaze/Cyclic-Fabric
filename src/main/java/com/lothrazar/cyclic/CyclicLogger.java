package com.lothrazar.cyclic;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.apache.logging.log4j.Logger;

public class CyclicLogger {
    private final Logger logger;
    public static BooleanValue LOGINFO;

    public CyclicLogger(Logger logger) {
        this.logger = logger;
    }

    public void error(String string) {
        logger.error(string);
    }

    public void error(String string, Object e) {
        logger.error(string, e);
    }

    public void info(String string) {
        if (LOGINFO.get()) {
            logger.info(string);
        }
    }
}
