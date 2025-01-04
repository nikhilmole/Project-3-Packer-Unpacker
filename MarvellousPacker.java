import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Stream;

public class MarvellousPacker {
    FileOutputStream outstream = null;
    String ValidExt[] = {".txt", ".java", ".cpp"};

    public MarvellousPacker(String src, String Dest) throws Exception {
        String magic = "Marvellous11";
        byte arr[] = magic.getBytes();
        File output = new File(Dest);

        outstream = new FileOutputStream(Dest);
        outstream.write(arr, 0, arr.length);

        listAllFiles(src);
    }

    public void listAllFiles(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String name = filePath.getFileName().toString();
                        String ext = name.substring(name.lastIndexOf("."));
                        List<String> list = Arrays.asList(ValidExt);
                        if (list.contains(ext)) {
                            pack(filePath.toString());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void pack(String filePath) {
        FileInputStream instream = null;
        try {
            byte[] buffer = new byte[1024];
            int length;
            byte temp[] = new byte[100];
            File fobj = new File(filePath);

            String Header = filePath + " " + fobj.length();

            for (int i = Header.length(); i < 100; i++) {
                Header += " ";
            }

            temp = Header.getBytes();
            instream = new FileInputStream(filePath);
            outstream.write(temp, 0, temp.length);

            while ((length = instream.read(buffer)) > 0) {
                outstream.write(buffer, 0, length);
            }

            instream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
