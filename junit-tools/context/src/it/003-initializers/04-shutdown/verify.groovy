import java.nio.file.Files
import java.nio.file.Paths

def f = Paths.get(((File)basedir).absolutePath, "shutdown.txt");
println f.toAbsolutePath();
def s = Files.readAllLines(f).get(0);
assert s != null && s.length() == 2, "File is missing or content is wrong";
assert "OK" == s, "Invalid content"