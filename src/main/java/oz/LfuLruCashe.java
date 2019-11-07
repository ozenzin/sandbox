package oz;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * Implement simple cache with max capacity and eviction policy as follows:
 *  LFU (least frequently used) with ties resolved by LRU (least recently accessed)
 */
public class LfuLruCashe<K, V> {

    public static void main(String[] args) {
        List<List<Integer>> l = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3));
        List<Integer> is = l.stream().flatMap(ll -> ll.stream()).collect(toList());
    }

    private final int MAX_CAP;

    private Map<K, Integer> fqs = new HashMap<>();

    private Map<K, V> cache;

    public LfuLruCashe(int capacity) {
        this.MAX_CAP = capacity;
        this.cache = new LinkedHashMap<>(MAX_CAP);
    }

    public V get(K key) {
        V val = cache.get(key);
        if (val != null)
            fqs.put(key, fqs.get(key) +1);
        return val;
    }

    public void put(K key, V val) {
        V existing = cache.get(key);
        if (existing != null)
            return;
        if (cache.size() == MAX_CAP) {
            removeLfuLru();
        }
        cache.put(key, val);
    }

    private void removeLfuLru() {
        Map<Integer, List<Map.Entry<K, Integer>>> grouped = fqs.entrySet().stream().collect(groupingBy(Map.Entry<K, Integer>::getValue));
        List<Map.Entry<K, Integer>> leastFrequent = grouped.
                        entrySet().stream().min(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).get();
        K key = leastFrequent.get(0).getKey();
        if (leastFrequent.size() > 1) {
            List<K> lfuKeys = leastFrequent.stream().map(Map.Entry::getKey).collect(toList());
            for (K lruKey : cache.keySet()) {
                if (lfuKeys.contains(lruKey)) {
                    key = lruKey;
                }
            }
        }
        fqs.remove(key);
        cache.remove(key);
    }

}
