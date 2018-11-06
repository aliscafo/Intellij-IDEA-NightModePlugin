import com.intellij.openapi.diagnostic.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class NightModeService {
    private static final Logger LOG = Logger.getInstance(NightModeService.class);

    NightModeService() {
        Timer time = new Timer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 0, 1000);
    }

    public class ScheduledTask extends TimerTask {
        @Override
        public void run() {
            LOG.info(String.valueOf(NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS));
        }
    }
}
