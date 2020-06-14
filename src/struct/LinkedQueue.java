package struct;


import interfaces.Queue;


public class LinkedQueue<T>
		implements Queue<T>
{

  private LinkedList<T> list;


  public LinkedQueue()
  {
	list = new LinkedList<>();
  }


  @Override
  public void enqueue(T elem)
  {
	list.insertAtEnd(elem);
  }


  @Override
  public T dequeue()
  {
	if (isEmpty())
	  throw new EmptyQueueException();

	T first = list.first();
	list.deleteFromBegin();
	return first;
  }


  @Override
  public T front()
  {
	if (isEmpty())
	  throw new EmptyQueueException();

	return list.first();
  }


  @Override
  public void clear()
  {
	list.clear();
  }


  @Override
  public long size()
  {
	return list.size();
  }

}