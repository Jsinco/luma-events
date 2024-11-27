package dev.jsinco.luma.config;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class MaterialTransformer extends BidirectionalTransformer<String, Material> {
    @Override
    public GenericsPair<String, Material> getPair() {
        return this.genericsPair(String.class, Material.class);
    }

    @Override
    public Material leftToRight(@NotNull String data, @NotNull SerdesContext serdesContext) {
        return Material.getMaterial(data);
    }

    @Override
    public String rightToLeft(Material data, @NotNull SerdesContext serdesContext) {
        return data.toString();
    }
}
