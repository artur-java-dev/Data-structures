package struct;


import interfaces.Queue;

import java.util.function.ToIntFunction;


public class PriorityQueue<T>
		implements Queue<T>
{

  private MaxHeap<T, Integer> heap;
  private ToIntFunction<T> priority;


  public PriorityQueue(ToIntFunction<T> priorityFunc)
  {
	priority = priorityFunc;
	heap = new MaxHeap<>();
  }


  @Override
  public void enqueue(T elem)
  {
	int p = priority.applyAsInt(elem);
	heap.insert(elem, p);
  }


  @Override
  public T dequeue()
  {
	try
	{
	  return heap.extractMax();
	}
	catch (MaxHeap.UnderflowException e)
	{
	  throw new EmptyQueueException();
	}
  }


  @Override
  public T front()
  throws EmptyQueueException
  {
	try
	{
	  return heap.max();
	}
	catch (MaxHeap.UnderflowException e)
	{
	  throw new EmptyQueueException();
	}
  }


  @Override
  public void clear()
  {
	heap.clear();
  }


  @Override
  public long size()
  {
	return heap.getSize();
  }

}