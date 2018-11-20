package com.intellij.nightModePlugin;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NightModeConfigurable implements Configurable {
    private static final Logger LOG = Logger.getInstance(NightModeConfigurable.class);

    private JPanel myMainPanel;
    private JComboBox startHoursBox;
    private JComboBox startMinutesBox;
    private JComboBox endHoursBox;
    private JComboBox endMinutesBox;
    private JComboBox basicScheme;
    private JComboBox onScheduleScheme;
    private JCheckBox isSchemeOnSchedule;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Night Mode";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        String[] listHours = new String[24];
        for (Integer i = 0; i < 24; i++) {
            listHours[i] = i.toString();
        }

        String[] listMinutes = new String[60];
        for (Integer i = 0; i < 60; i++) {
            listMinutes[i] = i.toString();
        }

        EditorColorsScheme[] schemes = EditorColorsManager.getInstance().getAllSchemes();
        String[] schemeNames = new String[schemes.length];

        for (int i = 0; i < schemeNames.length; i++) {
            schemeNames[i] = schemes[i].getName();
        }

        startHoursBox.setModel(new DefaultComboBoxModel(listHours));
        endHoursBox.setModel(new DefaultComboBoxModel(listHours));

        startMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));
        endMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));

        basicScheme.setModel(new DefaultComboBoxModel(schemes));
        onScheduleScheme.setModel(new DefaultComboBoxModel(schemes));

        return myMainPanel;
    }

    @Override
    public boolean isModified() {
        boolean value = (startHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS) ||
                (startMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES) ||
                (endHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS) ||
                (endMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES) ||
                (!NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME.equals(basicScheme.getSelectedItem().toString())) ||
                (!NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME.equals(onScheduleScheme.getSelectedItem().toString())) ||
                (isSchemeOnSchedule.isSelected() != NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE);

        return value;
    }

    @Override
    public void apply() throws ConfigurationException {
        NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS = startHoursBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES = startMinutesBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS = endHoursBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES = endMinutesBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME = basicScheme.getSelectedItem().toString();
        NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME = onScheduleScheme.getSelectedItem().toString();
        NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE = isSchemeOnSchedule.isSelected();
    }

    @Override
    public void reset() {
        startHoursBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS);
        startMinutesBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES);
        endHoursBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS);
        endMinutesBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES);

        basicScheme.setSelectedIndex(findIndexInComboBox(basicScheme,
                NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME));
        onScheduleScheme.setSelectedIndex(findIndexInComboBox(onScheduleScheme,
                NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME));
        isSchemeOnSchedule.setSelected(NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE);
    }

    // Returns 0, if didn't find given string.
    private int findIndexInComboBox(JComboBox comboBox, String item) {
        ComboBoxModel model = comboBox.getModel();
        int size = model.getSize();
        for (int i = 0; i < size; i++) {
            Object element = model.getElementAt(i);
            if (element.toString().equals(item)) {
                return i;
            }
        }

        return 0;
    }
}
