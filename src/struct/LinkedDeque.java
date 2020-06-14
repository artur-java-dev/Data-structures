package struct;


import interfaces.Deque;


public class LinkedDeque<T>
		implements Deque<T>
{

  private LinkedList<T> list;


  public LinkedDeque()
  {
	list = new LinkedList<>();
  }


  @Override
  public void enqueueFront(T elem)
  {
	list.insertAtEnd(elem);
  }


  @Override
  public T dequeueFront()
  {
	if (isEmpty())
	  throw new EmptyDequeException();

	T last = list.last();
	list.deleteFromEnd();
	return last;
  }


  @Override
  public T front()
  {
	if (isEmpty())
	  throw new EmptyDequeException();

	return list.last();
  }


  @Override
  public void enqueueBack(T elem)
  {
	list.insertAtBegin(elem);
  }


  @Override
  public T dequeueBack()
  {
	if (isEmpty())
	  throw new EmptyDequeException();

	T first = list.first();
	list.deleteFromBegin();
	return first;
  }


  @Override
  public T back()
  {
	if (isEmpty())
	  throw new EmptyDequeException();

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