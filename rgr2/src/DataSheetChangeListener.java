import java.util.EventListener;

public interface DataSheetChangeListener extends EventListener {
    public void dataChange(DataSheetChangeEvent e);
}
