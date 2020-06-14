package struct;


import interfaces.Stack;


public class LinkedStack<T>
		implements Stack<T>
{

  private LinkedList<T> list;


  public LinkedStack()
  {
	list = new LinkedList<>();
  }


  @Override
  public void push(T elem)
  {
	list.insertAtBegin(elem);
  }


  @Override
  public T pop()
  {
	if (isEmpty())
	  throw new EmptyStackException();

	T first = list.first();
	list.deleteFromBegin();
	return first;
  }


  @Override
  public T top()
  {
	if (isEmpty())
	  throw new EmptyStackException();

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