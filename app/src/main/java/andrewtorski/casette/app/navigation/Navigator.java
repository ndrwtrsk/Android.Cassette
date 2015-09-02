package andrewtorski.casette.app.navigation;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import andrewtorski.casette.app.view.activity.CassetteDetailActivity;
import andrewtorski.casette.app.view.activity.ListCassettesActivity;

@Singleton
public class Navigator {

    @Inject
    public void Navigator() {
        //empty
    }

    public void navigateToCassetteList(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = ListCassettesActivity.getCallingIntent(context);
        context.startActivity(intent);
    }

    public void navigateToCassetteDetails(Context context, long cassetteId) {
        if (context == null) {
            return;
        }

        Intent intent = CassetteDetailActivity.getCallingIntent(context, cassetteId);
        context.startActivity(intent);
    }
}
