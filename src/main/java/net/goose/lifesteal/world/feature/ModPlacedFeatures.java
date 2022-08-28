package net.goose.lifesteal.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final Holder<PlacedFeature> HEART_ORE_PLACED = PlacementUtils.register("heart_ore_placed",
            ModConfiguredFeatures.HEART_ORE,
                    commonOrePlacement(12, //VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-50), VerticalAnchor.aboveBottom(70))));

    public static final Holder<PlacedFeature> NETHER_HEART_ORE_PLACED = PlacementUtils.register("nether_heart_ore_placed",
            ModConfiguredFeatures.NETHER_HEART_ORE,
                    commonOrePlacement(14, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(20), VerticalAnchor.aboveBottom(100))));

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

}