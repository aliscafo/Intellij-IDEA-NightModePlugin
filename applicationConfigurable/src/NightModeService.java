import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.debugger.actions.ArrayFilterAction;
import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vcs.FileStatusManager;


import java.util.*;

public class NightModeService {
    private static final Logger LOG = Logger.getInstance(NightModeService.class);

    NightModeService() {
        Timer time = new Timer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 0, 5000);
    }

    public class ScheduledTask extends TimerTask {
        @Override
        public void run() {
            if (!NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE) {
                return;
            }

            LOG.info("RUN");

            if (ifTimeForOnScheduleScheme() && !ifCurSchemeIsOnSchedule()) {
                EditorColorsScheme scheme = EditorColorsManager.getInstance()
                        .getScheme(NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME);
                EditorColorsManager.getInstance().setGlobalScheme(scheme);

                EditorFactory.getInstance().refreshAllEditors();
                Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
                for (Project openProject : openProjects) {
                    FileStatusManager.getInstance(openProject).fileStatusesChanged();
                    DaemonCodeAnalyzer.getInstance(openProject).restart();
                }

                LOG.info("WAS CHANGED TO ON_SCHEDULE");

            } else if (!ifTimeForOnScheduleScheme() && !ifCurSchemeIsBasic()) {
                LOG.info(EditorColorsManager.getInstance().getGlobalScheme().getName());
                LOG.info(NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME);
                LOG.info(String.valueOf(ifCurSchemeIsBasic()));

                EditorColorsScheme scheme = EditorColorsManager.getInstance()
                        .getScheme(NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME);
                EditorColorsManager.getInstance().setGlobalScheme(scheme);

                EditorFactory.getInstance().refreshAllEditors();
                Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
                for (Project openProject : openProjects) {
                    FileStatusManager.getInstance(openProject).fileStatusesChanged();
                    DaemonCodeAnalyzer.getInstance(openProject).restart();
                }

                LOG.info("WAS CHANGED TO BASIC");
            }
        }
    }

    private Calendar getCalendar() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();

        return calendar;
    }

    private int getHourOfDay() {
        return getCalendar().get(Calendar.HOUR_OF_DAY);
    }

    private int getMinuteOfDay() {
        return getCalendar().get(Calendar.MINUTE);
    }

    private boolean ifTimeForOnScheduleScheme() {
        int startTime = NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS * 60 +
                NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES;
        int endTime = NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS * 60 +
                NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES;

        if (endTime < startTime) {
            endTime += 24 * 60;
        }

        int curTime = getHourOfDay() * 60 + getMinuteOfDay();

        if (startTime <= curTime && curTime <= endTime) {
            return true;
        }

        curTime += 24 * 60;

        if (startTime <= curTime && curTime <= endTime) {
            return true;
        }

        return false;
    }

    private boolean ifCurSchemeIsOnSchedule() {
        return (EditorColorsManager.getInstance().getGlobalScheme().getName()
                .equals(NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME));
    }

    private boolean ifCurSchemeIsBasic() {
        return (EditorColorsManager.getInstance().getGlobalScheme().getName()
                .equals(NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME));
    }
}
