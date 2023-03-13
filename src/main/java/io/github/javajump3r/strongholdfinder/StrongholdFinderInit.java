package io.github.javajump3r.strongholdfinder;

import io.github.javajump3r.strongholdfinder.strongholdfinder.StrongholdFinder;
import net.fabricmc.api.ClientModInitializer;

public class StrongholdFinderInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new StrongholdFinder();
    }
}
