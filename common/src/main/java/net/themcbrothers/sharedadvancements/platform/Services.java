package net.themcbrothers.sharedadvancements.platform;

import net.themcbrothers.sharedadvancements.Constants;
import net.themcbrothers.sharedadvancements.platform.services.IConfigHelper;
import net.themcbrothers.sharedadvancements.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {
    public static final IConfigHelper CONFIG = load(IConfigHelper.class);
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz, Services.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}