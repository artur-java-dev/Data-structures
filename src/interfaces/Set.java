package interfaces;


import java.util.function.Predicate;


public interface Set<T>
{

  void insert(T elem);

  void delete(T elem);

  void clear();

  long size();

  boolean isMember(T elem);

  boolean equals(Set<T> set);

  T select();

  Set<T> union(Set<T> set);

  Set<T> intersection(Set<T> set);

  Set<T> difference(Set<T> set);

  Set<T> selectByCondition(Predicate<T> cond);

  default boolean isEmpty()
  {
	return size() == 0;
  }

}