package struct;


import static java.lang.Math.floor;


public class MaxHeap<T, K extends Comparable<K>>
{

  private DynamicArray<Elem> array;
  private int size;


  public MaxHeap()
  {
	array = new DynamicArray<>();
	size = 0;
  }


  public void insert(T elem, K key)
  {
	array.set(size, new Elem(elem));
	increaseKey(size, key);
	size = size + 1;
  }


  public T max()
  throws UnderflowException
  {
	if (size < 1)
	  throw new UnderflowException();

	return array.get(0).value;
  }


  public T extractMax()
  throws UnderflowException
  {
	if (size < 1)
	  throw new UnderflowException();

	T max = array.get(0).value;
	array.set(0, array.get(size - 1));
	heapify(0);
	size = size - 1;

	return max;
  }


  public void clear()
  {
	size = 0;
  }


  public int getSize()
  {
	return size;
  }


  private void heapify(int i)
  {
	int l = left(i);
	int r = right(i);

	int largest;
	if (l <= size && array.get(l).greater(array.get(i)))
	  largest = l;
	else
	  largest = i;

	if (r <= size && array.get(r).greater(array.get(largest)))
	  largest = r;

	if (largest != i)
	{
	  swap(i, largest);
	  heapify(largest);
	}
  }


  private void increaseKey(int i, K key)
  {
	if (array.get(i).greater(key))
	  throw new InvalidKey();

	array.get(i).key = key;

	int cur = i;
	while (cur > 0)
	{
	  int p = parent(cur);
	  if (array.get(cur).greaterEq(array.get(p))) break;
	  swap(cur, p);
	  cur = p;
	}
  }


  private int left(int i)
  {
	return 2 * i;
  }


  private int parent(int i)
  {
	double floor = floor(i / 2f);

	return (int) floor;
  }


  private int right(int i)
  {
	return 2 * i + 1;
  }


  private void swap(int i, int j)
  {
	Elem t = array.get(i);
	array.set(i, array.get(j));
	array.set(j, t);
  }


  private static class InvalidKey
		  extends RuntimeException
  {

  }


  public static class UnderflowException
		  extends Exception
  {

  }


  private class Elem
  {

	K key;
	final T value;


	Elem(T obj)
	{
	  value = obj;
	  key = null;
	}


	boolean less(Elem e)
	{
	  if (key == null)
		return true;

	  return key.compareTo(e.key) < 0;
	}


	boolean greater(K k)
	{
	  if (key == null)
		return false;

	  return key.compareTo(k) > 0;
	}


	boolean greaterEq(Elem e)
	{
	  return !less(e);
	}


	boolean greater(Elem e)
	{
	  if (key == null)
		return false;

	  return key.compareTo(e.key) > 0;
	}

  }

}