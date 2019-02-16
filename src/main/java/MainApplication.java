import com.mytomcat.core.MyTomcat;

public class MainApplication {
    public static void main(String[] args) {
        try {
            new MyTomcat(8000).start();
        } catch (Exception e) {
            handleError(e);
        }
    }

    private static void handleError(Exception e) {
        e.printStackTrace();
    }
}
