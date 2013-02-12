package be.kdg.groeph.mockMother;

import be.kdg.groeph.model.*;
import be.kdg.groeph.model.TripTypeEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 8/02/13
 * Time: 11:01
 */
public class TripMother {


    public static Trip validTripTimeboundOnlyStart()
    {
        return null;
    }
    /*
    public static Trip validOpenTrip1() {

        String description = "Lekker int antwaarps zonneke gon ligge op't mooiste strand vant land";
        TripTypeEnum tripType = TripTypeEnum.PUBLIEK;
        String title = "Chille op Sint-Anna plage";
        List<Label> labels = new ArrayList<Label>();
        labels.add(new Label("Strand"));
        labels.add(new Label("Antwerpen"));
        labels.add(new Label("Slecht weer"));

        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
                                                            //new TripType("Publiek")
        return new Trip(title, description, new Date(), new Date(), "Publiek" ,labels );
    }
    */
}
