import error.DuplicateSubjectException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * @author 1
 * @since 08.09.2016.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws DuplicateSubjectException, IOException, ClassNotFoundException, CloneNotSupportedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Server server = new Server();
        server.startServer();
    }
}
