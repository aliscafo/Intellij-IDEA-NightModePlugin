package com.intellij.nightModePlugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;

import java.io.*;
import java.util.*;

public class NightModeService {
    private static final Logger LOG = Logger.getInstance(NightModeService.class);
    private static final int HOURS_IN_DAY = 24;
    private static final int MINUTES_IN_HOUR = 60;

    NightModeService() {
        Timer time = new Timer();

        if (NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE) {
            ScheduledTaskSchemeOnSchedule st = new ScheduledTaskSchemeOnSchedule();
            time.schedule(st, 0, 5000);
        }

        if (NightModeApplicationLevelConfiguration.getInstance().IF_USING_SCRIPT) {
            ScheduledTaskSchemeUsingScript st = new ScheduledTaskSchemeUsingScript();
            time.schedule(st, 0, 5000);
        }
    }

    public class ScheduledTaskSchemeOnSchedule extends TimerTask {
        @Override
        public void run() {
            changeSchemeOnSchedule();
        }
    }

    public class ScheduledTaskSchemeUsingScript extends TimerTask {
        @Override
        public void run() {
            if (NightModeApplicationLevelConfiguration.getInstance().IF_USING_SCRIPT) {
                try {
                    changeSchemeUsingScript();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void changeSchemeUsingScript() throws IOException, InterruptedException {
        String command = NightModeApplicationLevelConfiguration.getInstance().COMMAND_FIELD;
        ProcessBuilder pb = new ProcessBuilder(command).directory(new File(System.getProperty("user.dir")));

        Process process = pb.start();
        process.waitFor();

        String result = output(process.getInputStream());

        if (result.charAt(0) == '0') {
            EditorColorsScheme scheme = EditorColorsManager.getInstance()
                    .getScheme(NightModeApplicationLevelConfiguration.getInstance().SCHEME0);
            ApplicationManager.getApplication().invokeLater(() -> EditorColorsManager.getInstance().setGlobalScheme(scheme));
        } else if (result.charAt(0) == '1') {
            EditorColorsScheme scheme = EditorColorsManager.getInstance()
                    .getScheme(NightModeApplicationLevelConfiguration.getInstance().SCHEME1);
            ApplicationManager.getApplication().invokeLater(() -> EditorColorsManager.getInstance().setGlobalScheme(scheme));
        }
  }

    private static String output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
        }
        return sb.toString();
    }

    private void changeSchemeOnSchedule() {
        if (ifTimeForOnScheduleScheme() && !ifCurSchemeIsOnSchedule()) {
            EditorColorsScheme scheme = EditorColorsManager.getInstance()
                    .getScheme(NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME);
            ApplicationManager.getApplication().invokeLater(() -> EditorColorsManager.getInstance().setGlobalScheme(scheme));

            LOG.info("WAS CHANGED TO ON_SCHEDULE");

        } else if (!ifTimeForOnScheduleScheme() && !ifCurSchemeIsBasic()) {
            EditorColorsScheme scheme = EditorColorsManager.getInstance()
                    .getScheme(NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME);
            ApplicationManager.getApplication().invokeLater(() -> EditorColorsManager.getInstance().setGlobalScheme(scheme));

            LOG.info("WAS CHANGED TO BASIC");
        }
    }

    private Calendar getCalendar() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

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
            endTime += HOURS_IN_DAY * MINUTES_IN_HOUR;
        }

        int curTime = getHourOfDay() * MINUTES_IN_HOUR + getMinuteOfDay();

        if (startTime <= curTime && curTime <= endTime) {
            return true;
        }

        curTime += HOURS_IN_DAY * MINUTES_IN_HOUR;

        return startTime <= curTime && curTime <= endTime;

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
