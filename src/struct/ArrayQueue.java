package struct;


import interfaces.Queue;


public class ArrayQueue<T>
		implements Queue<T>
{

  private T[] array;
  private int head;
  private int tail;
  private int length;
  private static final int INIT_CAPACITY = 100;


  @SuppressWarnings("unchecked")
  public ArrayQueue()
  {
	array = (T[]) new Object[INIT_CAPACITY];
	head = 0;
	tail = 0;
	length = 0;
  }


  @Override
  public void enqueue(T elem)
  {
	if (head == tail + 1 || (head == 0 && tail == array.length - 1))
	  throw new OverflowException();

	array[tail] = elem;

	if (tail == array.length - 1)
	  tail = 0;
	else
	  tail = tail + 1;

	length++;
  }


  @Override
  public T dequeue()
  {
	if (head == tail)
	  throw new EmptyQueueException();

	T x = array[head];

	if (head == array.length - 1)
	  head = 0;
	else
	  head = head + 1;

	length--;

	return x;
  }


  @Override
  public T front()
  throws EmptyQueueException
  {
	if (head == tail)
	  throw new EmptyQueueException();

	return array[head];
  }


  @Override
  public void clear()
  {
	head = 0;
	tail = 0;
	length = 0;
  }


  @Override
  public long size()
  {
	return length;
  }


  public static class OverflowException
		  extends RuntimeException
  {

  }

}