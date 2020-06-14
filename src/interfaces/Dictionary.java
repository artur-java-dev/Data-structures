package interfaces;


import java.util.Optional;


public interface Dictionary<K, V>
{

  Optional<V> get(K key);

  void insert(K key, V value);

  void delete(K key);

  void clear();

  long size();

  default boolean isEmpty()
  {
	return size() == 0;
  }

}