package interfaces;


public interface Deque<T>
{

  void enqueueFront(T elem);

  T dequeueFront();

  T front();

  void enqueueBack(T elem);

  T dequeueBack();

  T back();

  void clear();

  long size();

  default boolean isEmpty()
  {
	return size() == 0;
  }

  class EmptyDequeException
		  extends RuntimeException
  {

  }


}