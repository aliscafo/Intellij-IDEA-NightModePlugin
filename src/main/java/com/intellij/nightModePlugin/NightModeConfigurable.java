package com.intellij.nightModePlugin;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SchemeManager;
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
    private JCheckBox ifUsingScript;
    private JLabel scheduleSchemeChangeLabel;
    private JLabel changeSchemeUsingScriptLabel;
    private JTextField commandField;
    private JComboBox scheme0;
    private JComboBox scheme1;

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
            schemeNames[i] = SchemeManager.getDisplayName(schemes[i]);
        }

        startHoursBox.setModel(new DefaultComboBoxModel(listHours));
        endHoursBox.setModel(new DefaultComboBoxModel(listHours));

        startMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));
        endMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));

        basicScheme.setModel(new DefaultComboBoxModel(schemeNames));
        onScheduleScheme.setModel(new DefaultComboBoxModel(schemeNames));
        scheme0.setModel(new DefaultComboBoxModel(schemeNames));
        scheme1.setModel(new DefaultComboBoxModel(schemeNames));

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
                (!NightModeApplicationLevelConfiguration.getInstance().SCHEME0.equals(scheme0.getSelectedItem().toString())) ||
                (!NightModeApplicationLevelConfiguration.getInstance().SCHEME1.equals(scheme1.getSelectedItem().toString())) ||
                (isSchemeOnSchedule.isSelected() != NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE) ||
                (ifUsingScript.isSelected() != NightModeApplicationLevelConfiguration.getInstance().IF_USING_SCRIPT) ||
                (!commandField.getText().equals(NightModeApplicationLevelConfiguration.getInstance().COMMAND_FIELD));

        setDisabled(isSchemeOnSchedule, changeSchemeUsingScriptLabel, ifUsingScript);

        setDisabled(ifUsingScript, scheduleSchemeChangeLabel, isSchemeOnSchedule);

        return value;
    }

    private void setDisabled(JCheckBox ifUsingScript, JLabel scheduleSchemeChangeLabel, JCheckBox isSchemeOnSchedule) {
        if (ifUsingScript.isSelected()) {
            scheduleSchemeChangeLabel.setEnabled(false);
            isSchemeOnSchedule.setEnabled(false);
            isSchemeOnSchedule.setSelected(false);
        } else {
            scheduleSchemeChangeLabel.setEnabled(true);
            isSchemeOnSchedule.setEnabled(true);
        }
    }

    @Override
    public void apply() {
        NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS = startHoursBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES = startMinutesBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS = endHoursBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES = endMinutesBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME = basicScheme.getSelectedItem().toString();
        NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME = onScheduleScheme.getSelectedItem().toString();
        NightModeApplicationLevelConfiguration.getInstance().SCHEME0 = scheme0.getSelectedItem().toString();
        NightModeApplicationLevelConfiguration.getInstance().SCHEME1 = scheme1.getSelectedItem().toString();
        NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE = isSchemeOnSchedule.isSelected();
        NightModeApplicationLevelConfiguration.getInstance().IF_USING_SCRIPT = ifUsingScript.isSelected();
        NightModeApplicationLevelConfiguration.getInstance().COMMAND_FIELD = commandField.getText();
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
        scheme0.setSelectedIndex(findIndexInComboBox(scheme0,
                NightModeApplicationLevelConfiguration.getInstance().SCHEME0));
        scheme1.setSelectedIndex(findIndexInComboBox(scheme1,
                NightModeApplicationLevelConfiguration.getInstance().SCHEME1));
        isSchemeOnSchedule.setSelected(NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE);
        ifUsingScript.setSelected(NightModeApplicationLevelConfiguration.getInstance().IF_USING_SCRIPT);
        commandField.setText(NightModeApplicationLevelConfiguration.getInstance().COMMAND_FIELD);
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
