import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.ui.CheckBox;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;

public class NightModeConfigurable implements Configurable {
    private static final Logger LOG = Logger.getInstance(NightModeConfigurable.class);

    private JPanel myMainPanel;
    JComboBox startHoursBox = new ComboBox();
    JComboBox startMinutesBox = new ComboBox();
    JComboBox endHoursBox = new ComboBox();
    JComboBox endMinutesBox = new ComboBox();
    JComboBox basicScheme = new ComboBox();
    JComboBox onScheduleScheme = new ComboBox();
    JCheckBox isSchemeOnSchedule = new JCheckBox();

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

        myMainPanel = new JPanel();
        SpringLayout layout = new SpringLayout();

        JLabel startLabel = new JLabel("Start: ");
        JLabel endLabel = new JLabel("End: ");
        JLabel hoursLabel1 = new JLabel("hours");
        JLabel hoursLabel2 = new JLabel("hours");
        JLabel minutesLabel1 = new JLabel("minutes");
        JLabel minutesLabel2 = new JLabel("minutes");
        JLabel schemeLabel1 = new JLabel("Scheme:");
        JLabel schemeLabel2 = new JLabel("Scheme:");
        JLabel scheduleSchemeLabel = new JLabel("Schedule scheme change:");

        startHoursBox.setModel(new DefaultComboBoxModel(listHours));
        endHoursBox.setModel(new DefaultComboBoxModel(listHours));

        startMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));
        endMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));

        basicScheme.setModel(new DefaultComboBoxModel(schemes));
        onScheduleScheme.setModel(new DefaultComboBoxModel(schemes));

        myMainPanel.setSize(300, 300);
        myMainPanel.setLayout(layout);

        myMainPanel.add(startLabel);
        myMainPanel.add(endLabel);
        myMainPanel.add(startHoursBox);
        myMainPanel.add(endHoursBox);
        myMainPanel.add(endMinutesBox);
        myMainPanel.add(startMinutesBox);
        myMainPanel.add(hoursLabel1);
        myMainPanel.add(minutesLabel1);
        myMainPanel.add(hoursLabel2);
        myMainPanel.add(minutesLabel2);
        myMainPanel.add(schemeLabel1);
        myMainPanel.add(schemeLabel2);
        myMainPanel.add(basicScheme);
        myMainPanel.add(onScheduleScheme);
        myMainPanel.add(scheduleSchemeLabel);
        myMainPanel.add(isSchemeOnSchedule);

        layout.putConstraint(SpringLayout.WEST, scheduleSchemeLabel, 5, SpringLayout.WEST, myMainPanel);
        layout.putConstraint(SpringLayout.NORTH, scheduleSchemeLabel, 20, SpringLayout.NORTH, myMainPanel);
        layout.putConstraint(SpringLayout.WEST, isSchemeOnSchedule, 15, SpringLayout.EAST, scheduleSchemeLabel);
        layout.putConstraint(SpringLayout.NORTH, isSchemeOnSchedule, 18, SpringLayout.NORTH, myMainPanel);

        layout.putConstraint(SpringLayout.WEST, startLabel, 5, SpringLayout.WEST, myMainPanel);
        layout.putConstraint(SpringLayout.NORTH, startLabel, 30, SpringLayout.SOUTH, scheduleSchemeLabel);
        layout.putConstraint(SpringLayout.WEST, startHoursBox, 5, SpringLayout.EAST, startLabel);
        layout.putConstraint(SpringLayout.NORTH, startHoursBox, 23, SpringLayout.SOUTH, scheduleSchemeLabel);
        layout.putConstraint(SpringLayout.WEST, hoursLabel1, 3, SpringLayout.EAST, startHoursBox);
        layout.putConstraint(SpringLayout.NORTH, hoursLabel1, 30, SpringLayout.SOUTH, scheduleSchemeLabel);
        layout.putConstraint(SpringLayout.WEST, startMinutesBox, 15, SpringLayout.EAST, hoursLabel1);
        layout.putConstraint(SpringLayout.NORTH, startMinutesBox, 23, SpringLayout.SOUTH, scheduleSchemeLabel);
        layout.putConstraint(SpringLayout.WEST, minutesLabel1, 5, SpringLayout.EAST, startMinutesBox);
        layout.putConstraint(SpringLayout.NORTH, minutesLabel1, 30, SpringLayout.SOUTH, scheduleSchemeLabel);
        layout.putConstraint(SpringLayout.WEST, schemeLabel1, 35, SpringLayout.EAST, minutesLabel1);
        layout.putConstraint(SpringLayout.NORTH, schemeLabel1, 30, SpringLayout.SOUTH, scheduleSchemeLabel);
        layout.putConstraint(SpringLayout.WEST, onScheduleScheme, 10, SpringLayout.EAST, schemeLabel1);
        layout.putConstraint(SpringLayout.NORTH, onScheduleScheme, 23, SpringLayout.SOUTH, scheduleSchemeLabel);

        layout.putConstraint(SpringLayout.WEST, endLabel, 5, SpringLayout.WEST, myMainPanel);
        layout.putConstraint(SpringLayout.NORTH, endLabel, 35, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, endHoursBox, 11, SpringLayout.EAST, endLabel);
        layout.putConstraint(SpringLayout.NORTH, endHoursBox, 28, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, hoursLabel2, 3, SpringLayout.EAST, endHoursBox);
        layout.putConstraint(SpringLayout.NORTH, hoursLabel2, 35, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, endMinutesBox, 15, SpringLayout.EAST, hoursLabel2);
        layout.putConstraint(SpringLayout.NORTH, endMinutesBox, 28, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, minutesLabel2, 5, SpringLayout.EAST, endMinutesBox);
        layout.putConstraint(SpringLayout.NORTH, minutesLabel2, 35, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, schemeLabel2, 35, SpringLayout.EAST, minutesLabel2);
        layout.putConstraint(SpringLayout.NORTH, schemeLabel2, 35, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, basicScheme, 10, SpringLayout.EAST, schemeLabel2);
        layout.putConstraint(SpringLayout.NORTH, basicScheme, 28, SpringLayout.SOUTH, startLabel);

        return myMainPanel;
    }

    @Override
    public boolean isModified() {
        /*LOG.info("SELECTED: |" + basicScheme.getSelectedItem().toString() + "|");
        LOG.info("SELECTED!!!: |" + NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME + "|");*/

        boolean value = (startHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS) ||
                (startMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES) ||
                (endHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS) ||
                (endMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES) ||
                (!NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME.equals(basicScheme.getSelectedItem().toString())) ||
                (!NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME.equals(onScheduleScheme.getSelectedItem().toString())) ||
                (isSchemeOnSchedule.isSelected() != NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE);

        /*LOG.info(String.valueOf(startHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS));
        LOG.info(String.valueOf(startMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES));
        LOG.info(String.valueOf(endHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS));
        LOG.info(String.valueOf(endMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES));
        LOG.info(String.valueOf(NightModeApplicationLevelConfiguration.getInstance().BASIC_SCHEME.equals(basicScheme.getSelectedItem().toString())));
        LOG.info(String.valueOf(NightModeApplicationLevelConfiguration.getInstance().ON_SCHEDULE_SCHEME.equals(onScheduleScheme.getSelectedItem().toString())));
        LOG.info(String.valueOf(isSchemeOnSchedule.isSelected() != NightModeApplicationLevelConfiguration.getInstance().IS_SCHEME_ON_SCHEDULE));

        LOG.info("VALUE: " + value);*/

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
