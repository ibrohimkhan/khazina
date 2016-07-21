package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.AnalyticsEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 2/22/2015.
 */
public interface AnalyticsEventListener extends EventListener {
    void searchEventAction(AnalyticsEvent event);
}
