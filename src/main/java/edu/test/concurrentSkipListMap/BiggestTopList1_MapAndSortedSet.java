package edu.test.concurrentSkipListMap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

/* - Map + Set
   - Objects
 */
public class BiggestTopList1_MapAndSortedSet {

    private int limit = 6;
    private long highestMs;

    private Map<String, Long> map = new HashMap<>();
    private NavigableSet<Pair<String, Long>> set = new TreeSet<>(
            Comparator.comparingLong(v -> -v.value));

    public void add(String key, long value) {
        long startMs = System.currentTimeMillis();

        addInner(key, value);

        long durationMs = System.currentTimeMillis() - startMs;
        if (durationMs > highestMs) {
            highestMs = durationMs;
        }
    }

    private void addInner(String key, long value) {
        Long existingKeyValue = map.get(key);

        if (map.size() < limit) {
            if (existingKeyValue == null) {
                map.put(key, value);    // O(1)
                Pair<String, Long> newPair = new Pair<>(key, value);
                set.add(newPair);       // O(log n)
            } else {
                if (value > existingKeyValue) {
                    map.replace(key, value); // O(1)

                    set.remove(new Pair<>(key, existingKeyValue));  // O(log n)
                    set.add(new Pair<>(key, value));                // O(log n)
                }
            }
        } else {
            if (existingKeyValue == null) {
                Pair<String, Long> lowestValue = set.last();   // O(log n)
                if (value > lowestValue.value) {
                    map.remove(lowestValue.key);    // O(1)
                    map.put(key, value);            // O(1)

                    set.remove(lowestValue);            // O(log n)
                    set.add(new Pair<>(key, value));    // O(log n)
                }
            } else {
                if (value > existingKeyValue) {
                    map.replace(key, value);    // O(1)

                    set.remove(new Pair<>(key, existingKeyValue));  // O(log n)
                    set.add(new Pair<>(key, value));                // O(log n)
                }
            }
        }
    }

    public NavigableSet<Pair<String, Long>> getTop() {
        return set;
    }

    public static class Pair<K, V> {
        public final K key;
        public final V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return key.equals(pair.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

}
