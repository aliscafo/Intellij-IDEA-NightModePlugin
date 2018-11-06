import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "NightModeApplicationLevelConfiguration", storages = @Storage(value = "other.xml", roamingType = RoamingType.DISABLED))
public class NightModeApplicationLevelConfiguration implements PersistentStateComponent<NightModeApplicationLevelConfiguration> {

    public int START_TIME_HOURS = 0;
    public int START_TIME_MINUTES = 0;
    public int END_TIME_HOURS = 0;
    public int END_TIME_MINUTES = 0;

    public static NightModeApplicationLevelConfiguration getInstance() {
        return ServiceManager.getService(NightModeApplicationLevelConfiguration.class);
    }

    @Nullable
    @Override
    public NightModeApplicationLevelConfiguration getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull NightModeApplicationLevelConfiguration object) {
        XmlSerializerUtil.copyBean(object, this);
    }
}