package net.themcbrothers.sharedadvancements.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.themcbrothers.sharedadvancements.Constants;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class SharedAdvancementsClient {
    public SharedAdvancementsClient(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
