package com.gasco.Models;

import com.gasco.R;
import com.gasco.UI.Activities.CNGDispenseRecordActivity;
import com.gasco.UI.Activities.ProfileActivity;
import com.gasco.UI.Activities.WorkRosterActivity;

public class Menu<T> {

    private String name;
    private int image;
    private Class<T> destination;

    public Menu(String name, int image, Class<T> destination) {
        this.name = name;
        this.image = image;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public Class<T> getDestination() {
        return destination;
    }

    private static Menu[] menu = {
            new Menu<>("Profile",
                    R.drawable.ic_profile_person,
                    ProfileActivity.class),
            new Menu<>("Work Roster",
                    R.drawable.ic_calender,
                    WorkRosterActivity.class),
            new Menu<>("Handover Report",
                    R.drawable.ic_report_card,
                    ProfileActivity.class),
            new Menu<>("Maintenance Check",
                    R.drawable.ic_tools,
                    ProfileActivity.class),
            new Menu<>("CNG Record",
                    R.drawable.ic_graph_card,
                    CNGDispenseRecordActivity.class),
            new Menu<>("Incident Report",
                    R.drawable.ic_warning,
                    ProfileActivity.class),
            new Menu<>("Jobs",
                    R.drawable.ic_briefcase,
                    ProfileActivity.class)
    };

    public static Menu[] getDashboardMenu(){
        return menu;
    }
}
