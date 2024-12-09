package syncMonitor.mode;

import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.session.SyncMonitorSession;
import syncMonitor.session.SyncMonitorSessionTibero;
import syncMonitor.mode.view.ViewTibero;

// 티베로 통해 오라클 가져오는 모드 티베로만 연결하면됨
public class OnlyTibero {
    private ViewTibero viewTibero;
    public OnlyTibero(TiberoConfig tiberoConfig) {
        SyncMonitorSession sessionTibero = SyncMonitorSessionTibero.getSyncMonitorSession(tiberoConfig);
        this.viewTibero = new ViewTibero(sessionTibero, tiberoConfig);
    }

    public ViewTibero getViewTibero() {
        return viewTibero;
    }
}
