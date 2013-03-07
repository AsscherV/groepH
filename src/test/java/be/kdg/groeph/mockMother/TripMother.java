package be.kdg.groeph.mockMother;

import be.kdg.groeph.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TripMother {
    public static Trip validPublicTrip1() {
        String title = "Chille op Sint-Anna plage";
        String description = "Lekker int antwaarps zonneke gon ligge op't mooiste strand vant land";
        TripType tripType = new TripType("Timebound");
        ArrayList<Label> labels = new ArrayList<Label>();
        labels.add(new Label("Strand"));
        labels.add(new Label("Antwerpen"));
        labels.add(new Label("Slecht weer"));

        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        Date startTime = cal.getTime();
        cal.set(2013, Calendar.MARCH, 30, 17, 00);
        Date endTime = cal.getTime();

        boolean isPublic = true;

        return new Trip(title, description, startTime, endTime,labels,tripType,isPublic);
    }

}
