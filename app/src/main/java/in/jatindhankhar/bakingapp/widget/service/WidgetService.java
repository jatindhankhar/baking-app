package in.jatindhankhar.bakingapp.widget.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import in.jatindhankhar.bakingapp.widget.ui.adapter.ListProvider;

/**
 * Created by jatin on 1/21/18.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("Hachi","Creating new Widget");
        return new ListProvider(this.getApplicationContext(),intent);
    }
}
