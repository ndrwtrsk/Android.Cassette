package andrewtorski.casette.app.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import andrewtorski.casette.app.di.HasComponent;

/**
 * Created by andrew on 20/08/15.
 */
public class BaseFragment extends Fragment {

    private final String TAG = "BA_FRAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        Log.d(TAG, "Returning component.");
        C component = ((HasComponent<C>) getActivity()).getComponent();

        if (component == null) {
            Log.e(TAG, "Gotten component is null.");
        }

        return componentType.cast(component);
    }
}
