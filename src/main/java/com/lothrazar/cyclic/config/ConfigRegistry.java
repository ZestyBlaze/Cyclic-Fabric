package com.lothrazar.cyclic.config;

import com.lothrazar.cyclic.CyclicLogger;
import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.block.LavaSpongeBlock;
import com.lothrazar.cyclic.flib.ConfigTemplate;
import com.lothrazar.cyclic.item.food.EnderApple;
import com.lothrazar.cyclic.registry.PotionRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig;

import java.util.*;

public class ConfigRegistry extends ConfigTemplate {
    private static ForgeConfigSpec COMMON_CONFIG;
    private static ForgeConfigSpec CLIENT_CONFIG;

    public void setupMain() {
        COMMON_CONFIG.setConfig(setup(ModCyclic.MODID));
    }

    public void setupClient() {
        CLIENT_CONFIG.setConfig(setup(ModCyclic.MODID + "-client"));
        ForgeConfigRegistry.INSTANCE.register(ModCyclic.MODID, ModConfig.Type.CLIENT, ConfigRegistry.CLIENT_CONFIG);
    }

    // Defaults
    private static final List<String> BEHEADING = new ArrayList<>();
    private static final List<String> IGNORE_LIST_UNCRAFTER = new ArrayList<>();
    private static final List<String> MBALL_IGNORE = new ArrayList<>();
    private static final List<String> DISARM_IGNORE = new ArrayList<>();
    private static final List<String> IGNORE_RECIPES_UNCRAFTER = new ArrayList<>();
    private static final List<String> TRANSPORTBAG = new ArrayList<>();
    private static final List<String> ENDERAPPLE = new ArrayList<>();
    private static ConfigValue<List<? extends String>> BEHEADING_SKINS;
    private static ConfigValue<List<? extends String>> MBALL_IGNORE_LIST;
    private static ConfigValue<List<? extends String>> DISARM_IGNORE_LIST;
    private static final String WALL = "####################################################################################";
    public static BooleanValue OVERRIDE_TRANSPORTER_SINGLETON;
    public static BooleanValue GENERATE_FLOWERS;
    public static BooleanValue CYAN_PODZOL_LEGACY;
    static {
        buildDefaults();
        initConfig();
    }

    private static void buildDefaults() {
        //http://minecraft.gamepedia.com/Player.dat_format#Player_Heads
        //mhf https://twitter.com/Marc_IRL/status/542330244473311232  https://pastebin.com/5mug6EBu
        //other https://www.planetminecraft.com/blog/minecraft-playerheads-2579899/
        //NBT image data from  http://www.minecraft-heads.com/custom/heads/animals/6746-llama
        BEHEADING.add("minecraft:blaze:MHF_Blaze");
        BEHEADING.add("minecraft:cat:MHF_Ocelot");
        BEHEADING.add("minecraft:cave_spider:MHF_CaveSpider");
        BEHEADING.add("minecraft:chicken:MHF_Chicken");
        BEHEADING.add("minecraft:cow:MHF_Cow");
        BEHEADING.add("minecraft:enderman:MHF_Enderman");
        BEHEADING.add("minecraft:ghast:MHF_Ghast");
        BEHEADING.add("minecraft:iron_golem:MHF_Golem");
        BEHEADING.add("minecraft:magma_cube:MHF_LavaSlime");
        BEHEADING.add("minecraft:mooshroom:MHF_MushroomCow");
        BEHEADING.add("minecraft:ocelot:MHF_Ocelot");
        BEHEADING.add("minecraft:pig:MHF_Pig");
        BEHEADING.add("minecraft:zombie_pigman:MHF_PigZombie");
        BEHEADING.add("minecraft:sheep:MHF_Sheep");
        BEHEADING.add("minecraft:slime:MHF_Slime");
        BEHEADING.add("minecraft:spider:MHF_Spider");
        BEHEADING.add("minecraft:squid:MHF_Squid");
        BEHEADING.add("minecraft:villager:MHF_Villager");
        BEHEADING.add("minecraft:witch:MHF_Witch");
        BEHEADING.add("minecraft:wolf:MHF_Wolf");
        BEHEADING.add("minecraft:guardian:MHF_Guardian");
        BEHEADING.add("minecraft:elder_guardian:MHF_Guardian");
        BEHEADING.add("minecraft:snow_golem:MHF_SnowGolem");
        BEHEADING.add("minecraft:silverfish:MHF_Silverfish");
        BEHEADING.add("minecraft:endermite:MHF_Endermite");
        //
        //most of these are ported direct from 1.12 defaults, idk if these mods or items exist anymore
        IGNORE_LIST_UNCRAFTER.add("minecraft:elytra");
        IGNORE_LIST_UNCRAFTER.add("minecraft:tipped_arrow");
        IGNORE_LIST_UNCRAFTER.add("minecraft:magma_block");
        IGNORE_LIST_UNCRAFTER.add("minecraft:stick");
        IGNORE_LIST_UNCRAFTER.add("spectrite:spectrite_arrow");
        IGNORE_LIST_UNCRAFTER.add("spectrite:spectrite_arrow_special");
        IGNORE_LIST_UNCRAFTER.add("techreborn:uumatter");
        IGNORE_LIST_UNCRAFTER.add("projecte:*");
        //a
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:white_dye_from_lily_of_the_valley");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:orange_dye_from_orange_tulip");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:magenta_dye_from_allium");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:magenta_dye_from_lilac");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:light_blue_dye_from_blue_orchid");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:yellow_dye_from_sunflower");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:yellow_dye_from_dandelion");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:pink_dye_from_peony");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:pink_dye_from_pink_tulip");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:light_gray_dye_from_oxeye_daisy");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:light_gray_dye_from_azure_bluet");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:light_gray_dye_from_white_tulip");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:blue_dye_from_cornflower");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:red_dye_from_poppy");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:red_dye_from_rose_bush");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:red_dye_from_tulip");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:black_dye_from_wither_rose");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:blue_dye");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:black_dye");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:brown_dye");
        IGNORE_RECIPES_UNCRAFTER.add("botania:cobweb");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:magma_cream");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:beacon");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:stick_from_bamboo_item");
        IGNORE_RECIPES_UNCRAFTER.add("minecraft:netherite_ingot_from_netherite_block");
        IGNORE_RECIPES_UNCRAFTER.add("mysticalagriculture:essence*");
        IGNORE_RECIPES_UNCRAFTER.add("mysticalagriculture:farmland_till");
        IGNORE_RECIPES_UNCRAFTER.add("refinedstorage:coloring_recipes*");
        IGNORE_RECIPES_UNCRAFTER.add("forcecraft:transmutation*");
        IGNORE_RECIPES_UNCRAFTER.add("cyclic:flower_purple_tulip");
        IGNORE_RECIPES_UNCRAFTER.add("cyclic:flower_absalon_tulip");
        IGNORE_RECIPES_UNCRAFTER.add("cyclic:flower_cyan");
        IGNORE_RECIPES_UNCRAFTER.add("cyclic:flower_lime_carnation");
        IGNORE_RECIPES_UNCRAFTER.add("cyclic:fireball");
        IGNORE_RECIPES_UNCRAFTER.add("cyclic:shapeless/spark");
        //
        TRANSPORTBAG.addAll(Arrays.asList(
                //legacy
                "parabox:parabox", "extracells:fluidcrafter", "extracells:ecbaseblock", "extracells:fluidfiller",
                //entire mods
                "exnihilosequentia:*", "refinedstorage:*",
                //tconctruct fluid processing
                "tconstruct:seared_fuel_tank", "tconstruct:smeltery_controller", "tconstruct:seared_drain", "tconstruct:seared_fuel_gauge", "tconstruct:seared_ingot_tank", "tconstruct:seared_ingot_gauge", "tconstruct:seared_melter", "tconstruct:seared_heater",
                //drains and ducts
                "tconstruct:scorched_drain", "tconstruct:scorched_duct", "tconstruct:scorched_chute", "tconstruct:foundry_controller", "tconstruct:scorched_alloyer",
                //rftools batteries
                "rftoolspower:cell3", "rftoolspower:cell2", "rftoolspower:cell1", "rftoolspower:cell3", "rftoolspower:cell2", "rftoolspower:cell1"));
        //
        MBALL_IGNORE.add("minecraft:ender_dragon");
        MBALL_IGNORE.add("minecraft:wither");
        DISARM_IGNORE.add("alexsmobs:mimicube");
        ENDERAPPLE.addAll(Arrays.asList(
                "minecraft:eye_of_ender_located",
                "minecraft:on_woodland_explorer_maps",
                "minecraft:on_ocean_explorer_maps",
                "minecraft:village"));
    }

    private static void initConfig() {
        final ForgeConfigSpec.Builder CFG = builder();
        CFG.comment(WALL, "Features with configurable properties are split into categories", WALL).push(ModCyclic.MODID);
        CFG.comment(WALL, " Configs make sure players will not be able to craft any in survival "
                        + " (api only allows me to disable original base level potion, stuff like splash/tipped arrows are out of my control, for futher steps i suggest modpacks hide them from JEI as well if desired, or bug Mojang to implement JSON brewing stand recipes)", WALL)
                .push("potion");
        PotionRegistry.PotionRecipeConfig.ANTIGRAVITY = CFG.comment("Set false to disable the base recipe").define("antigravity.enabled", true);
        PotionRegistry.PotionRecipeConfig.ATTACK_RANGE = CFG.comment("Set false to disable the base recipe").define("attack_range.enabled", true);
        PotionRegistry.PotionRecipeConfig.BLIND = CFG.comment("Set false to disable the base recipe").define("blind.enabled", true);
        PotionRegistry.PotionRecipeConfig.BUTTERFINGERS = CFG.comment("Set false to disable the base recipe").define("butterfingers.enabled", true);
        PotionRegistry.PotionRecipeConfig.FLIGHT = CFG.comment("Set false to disable the base recipe").define("flight.enabled", true);
        PotionRegistry.PotionRecipeConfig.FROST_WALKER = CFG.comment("Set false to disable the base recipe").define("frost_walker.enabled", true);
        PotionRegistry.PotionRecipeConfig.GRAVITY = CFG.comment("Set false to disable the base recipe").define("gravity.enabled", true);
        PotionRegistry.PotionRecipeConfig.HASTE = CFG.comment("Set false to disable the base recipe").define("haste.enabled", true);
        PotionRegistry.PotionRecipeConfig.HUNGER = CFG.comment("Set false to disable the base recipe").define("hunger.enabled", true);
        PotionRegistry.PotionRecipeConfig.LEVITATION = CFG.comment("Set false to disable the base recipe").define("levitation.enabled", true);
        PotionRegistry.PotionRecipeConfig.MAGNETIC = CFG.comment("Set false to disable the base recipe").define("magnetic.enabled", true);
        PotionRegistry.PotionRecipeConfig.REACH_DISTANCE = CFG.comment("Set false to disable the base recipe").define("reach_distance.enabled", true);
        PotionRegistry.PotionRecipeConfig.RESISTANCE = CFG.comment("Set false to disable the base recipe").define("resistance.enabled", true);
        PotionRegistry.PotionRecipeConfig.STUN = CFG.comment("Set false to disable the base recipe").define("stun.enabled", true);
        PotionRegistry.PotionRecipeConfig.SWIMSPEED = CFG.comment("Set false to disable the base recipe").define("swimspeed.enabled", true);
        PotionRegistry.PotionRecipeConfig.SNOWWALK = CFG.comment("Set false to disable the base recipe").define("snowwalk.enabled", true);
        PotionRegistry.PotionRecipeConfig.WATERWALK = CFG.comment("Set false to disable the base recipe").define("waterwalk.enabled", true);
        PotionRegistry.PotionRecipeConfig.WITHER = CFG.comment("Set false to disable the base recipe").define("wither.enabled", true);
        CFG.pop();
        CFG.comment(WALL, " Logging related configs", WALL)
                .push("logging");
        CyclicLogger.LOGINFO = CFG.comment("Unblock info logs; very spammy; can be useful for testing certain issues").define("info", false);
        CFG.pop();
        CFG.comment(WALL, " Item specific configs", WALL).push("items");
        //
        CFG.comment("apple_ender settings").push("apple_ender");
        EnderApple.STRUCTURE_TAGS = CFG.comment("Which structure tags are looked for").defineList("structure_tags", ENDERAPPLE, it -> it instanceof String);
        EnderApple.PRINTED = CFG.comment("How many results the client will see").defineInRange("printed", 5, 1, 60);
        CFG.pop();
        //
        CFG.pop(); //items
        CFG.comment(WALL, " Block specific configs", WALL).push("blocks"); //////////////////////////////////////////////////////////////////////////////////// blocks
        LavaSpongeBlock.RADIUS = CFG.comment("Reach of the sponge").defineInRange("sponge_lava.radius", 8, 1, 64);
        CFG.pop(); //blocks
        CFG.pop(); //ROOT
        COMMON_CONFIG = CFG.build();
        initClientConfig();
    }

    private static void initClientConfig() {
        final ForgeConfigSpec.Builder CFGC = builder();
        CLIENT_CONFIG = CFGC.build();
    }

    @SuppressWarnings("unchecked")
    public static List<String> getMagicNetList() {
        return (List<String>) MBALL_IGNORE_LIST.get();
    }

    @SuppressWarnings("unchecked")
    public static List<String> getDisarmIgnoreList() {
        return (List<String>) DISARM_IGNORE_LIST.get();
    }

    public static Map<String, String> getMappedBeheading() {
        Map<String, String> mappedBeheading = new HashMap<String, String>();
        for (String s : BEHEADING_SKINS.get()) {
            try {
                String[] stuff = s.split(":");
                String entity = stuff[0] + ":" + stuff[1];
                String skin = stuff[2];
                mappedBeheading.put(entity, skin);
            }
            catch (Exception e) {
                ModCyclic.LOGGER.error("Beheading Enchantment: Invalid config entry " + s);
            }
        }
        return mappedBeheading;
    }
}
