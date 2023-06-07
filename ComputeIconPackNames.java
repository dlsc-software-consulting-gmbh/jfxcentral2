import java.nio.file.*;

// See conveyor.conf for what this is about.
public class ComputeIconPackNames {
    public static void main(String[] args) throws Exception {
        Files.lines(Path.of("components/pom.xml"))
             .filter(l -> l.contains("<artifactId>ikonli-") && l.contains("-pack</artifactId>"))
             .map(s -> s.replace("<artifactId>ikonli-", "").replace("-pack</artifactId>", "").trim())
             .map(s -> "org.kordamp.ikonli." + s)
             .forEach(System.out::println);
    }
}
