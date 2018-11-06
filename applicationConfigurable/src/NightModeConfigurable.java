import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NightModeConfigurable implements Configurable {
    private JPanel myMainPanel;
    JComboBox startHoursBox = new ComboBox();
    JComboBox startMinutesBox = new ComboBox();
    JComboBox endHoursBox = new ComboBox();
    JComboBox endMinutesBox = new ComboBox();

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

        myMainPanel = new JPanel();
        SpringLayout layout = new SpringLayout();

        JLabel startLabel = new JLabel("Start: ");
        JLabel endLabel = new JLabel("End: ");
        JLabel hoursLabel1 = new JLabel("hours");
        JLabel minutesLabel1 = new JLabel("minutes");
        JLabel hoursLabel2 = new JLabel("hours");
        JLabel minutesLabel2 = new JLabel("minutes");

        startHoursBox.setModel(new DefaultComboBoxModel(listHours));
        startMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));

        endHoursBox.setModel(new DefaultComboBoxModel(listHours));
        endMinutesBox.setModel(new DefaultComboBoxModel(listMinutes));

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

        layout.putConstraint(SpringLayout.WEST, startLabel, 5, SpringLayout.WEST, myMainPanel);
        layout.putConstraint(SpringLayout.NORTH, startLabel, 10, SpringLayout.NORTH, myMainPanel);
        layout.putConstraint(SpringLayout.WEST, startHoursBox, 5, SpringLayout.EAST, startLabel);
        layout.putConstraint(SpringLayout.NORTH, startHoursBox, 2, SpringLayout.NORTH, myMainPanel);
        layout.putConstraint(SpringLayout.WEST, hoursLabel1, 3, SpringLayout.EAST, startHoursBox);
        layout.putConstraint(SpringLayout.NORTH, hoursLabel1, 5, SpringLayout.NORTH, myMainPanel);
        layout.putConstraint(SpringLayout.WEST, startMinutesBox, 8, SpringLayout.EAST, hoursLabel1);
        layout.putConstraint(SpringLayout.NORTH, startMinutesBox, 2, SpringLayout.NORTH, myMainPanel);
        layout.putConstraint(SpringLayout.WEST, minutesLabel1, 3, SpringLayout.EAST, startMinutesBox);
        layout.putConstraint(SpringLayout.NORTH, minutesLabel1, 5, SpringLayout.NORTH, myMainPanel);

        layout.putConstraint(SpringLayout.WEST, endLabel, 5, SpringLayout.WEST, myMainPanel);
        layout.putConstraint(SpringLayout.NORTH, endLabel, 20, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, endHoursBox, 11, SpringLayout.EAST, endLabel);
        layout.putConstraint(SpringLayout.NORTH, endHoursBox, 20, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, hoursLabel2, 3, SpringLayout.EAST, endHoursBox);
        layout.putConstraint(SpringLayout.NORTH, hoursLabel2, 20, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, endMinutesBox, 8, SpringLayout.EAST, hoursLabel2);
        layout.putConstraint(SpringLayout.NORTH, endMinutesBox, 20, SpringLayout.SOUTH, startLabel);
        layout.putConstraint(SpringLayout.WEST, minutesLabel2, 5, SpringLayout.EAST, endMinutesBox);
        layout.putConstraint(SpringLayout.NORTH, minutesLabel2, 20, SpringLayout.SOUTH, startLabel);

        return myMainPanel;
    }

    @Override
    public boolean isModified() {
        return (startHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS) ||
                (startMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES) ||
                (endHoursBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS) ||
                (endMinutesBox.getSelectedIndex() != NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES);
    }

    @Override
    public void apply() throws ConfigurationException {
        NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS = startHoursBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES = startMinutesBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS = endHoursBox.getSelectedIndex();
        NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES = endMinutesBox.getSelectedIndex();
    }

    @Override
    public void reset() {
        startHoursBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().START_TIME_HOURS);
        startMinutesBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().START_TIME_MINUTES);
        endHoursBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().END_TIME_HOURS);
        endMinutesBox.setSelectedIndex(NightModeApplicationLevelConfiguration.getInstance().END_TIME_MINUTES);
    }
}
