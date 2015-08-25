package andrewtorski.casette.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CassetteLayoutManager extends LinearLayoutManager {
    /**
     * Creates a vertical LinearLayoutManager
     *
     * @param context Current context, will be used to access resources.
     */
    public CassetteLayoutManager(Context context) {
        super(context);
    }

    /**
     * @param context       Current context, will be used to access resources.
     * @param orientation   Layout orientation. Should be {@link #HORIZONTAL} or {@link
     *                      #VERTICAL}.
     * @param reverseLayout When set to true, layouts from end to start.
     */
    public CassetteLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }
}
