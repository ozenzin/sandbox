package oz.graph;

import java.util.*;

public class Graphs {

    public static class Vertex<T> {
        T data;
        Collection<T> connected = new LinkedList<>();
    }


    public static final Set<Vertex<Integer>> SOME_GRAPH = new LinkedHashSet<>(
            Arrays.asList()
    );
}
