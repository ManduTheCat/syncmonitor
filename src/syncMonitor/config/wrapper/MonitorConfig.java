package syncMonitor.config.wrapper;

public class MonitorConfig {
    private final static Integer WIDE_WIDTH = 80;
    private final static Integer NARROW_WIDTH = 30;
    private String size;

    public String getSize() {

        return size;
    }

    public void setSize(String size) {
        if ("wide".equalsIgnoreCase(size)) {
            this.size = WIDE_WIDTH.toString();
        }
        else if ("narrow".equalsIgnoreCase(size)) {
            this.size = NARROW_WIDTH.toString();
        } else {
            this.size = size;
        }
    }
}
