package com.lothrazar.cyclic.capability;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface ICyclicPlayer extends AutoSyncedComponent {
    void setStepHeight(boolean value);
    boolean getStepHeight();
    void toggleStepHeight();
}
