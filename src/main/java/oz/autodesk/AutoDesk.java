package oz.autodesk;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoDesk {


    static class OurFile {
        String name;

        public String getName() {
            return name;
        }
    }

    static class Directory extends OurFile {
        Directory parent;
        Set<? extends OurFile> files;

        public Set<? extends OurFile> getFiles() {
            return files;
        }

        List<OurFile> list(String search) {
            return getFiles().stream().filter(of -> of.name.contains(search)).flatMap(of -> {
                        if (of instanceof Directory) {
                            return ((Directory) of).list(search).stream();
                        } else {
                            return Stream.of(of);
                        }
                    }
            ).collect(Collectors.toList());
        }
    }


    public static void main(String[] args) {

    }

    public List<String> grep(String search, Directory dir) {
        return dir.list(search).stream().map(OurFile::getName).collect(Collectors.toList());
    }

}
