package starships.colideables;

import org.jetbrains.annotations.Nullable;

public interface PowerUpApplier {
    Starship mount(Starship starship);

    Starship dismount(Starship starship);

    boolean isOver(Double secondsSinceLastTime);

    @Nullable
    PowerUpApplier addTime(Double secondsSinceLastTime);
}
