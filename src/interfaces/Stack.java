package interfaces;


public interface Stack<T>
{

  void push(T elem);

  T pop();

  T top();

  void clear();

  long size();

  default boolean isEmpty()
  {
	return size() == 0;
  }

  class EmptyStackException
		  extends RuntimeException
  {

  }


}