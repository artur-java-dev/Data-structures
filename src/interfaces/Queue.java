package interfaces;


public interface Queue<T>
{

  void enqueue(T elem);

  T dequeue();

  T front();

  void clear();

  long size();

  default boolean isEmpty()
  {
	return size() == 0;
  }

  class EmptyQueueException
		  extends RuntimeException
  {

  }


}