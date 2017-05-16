package launcher;

import org.apache.camel.spring.Main;

public class Launcher {
	public static void main(String... args) throws Exception {
        Main main = new Main();
        main.run(args);
    }
}
