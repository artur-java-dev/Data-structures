package struct;


import interfaces.Stack;

import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;
import static struct.DynamicArray.IndexBase.ONE;


public class DynamicArray<T>
{

  private T[] array;
  private int length;
  private final IndexBase indexBase;
  private static final int INIT_CAPACITY = 100;
  private static final int EXTEND_COEF = 4;


  public DynamicArray()
  {
	this(IndexBase.ZERO);
  }


  @SuppressWarnings("unchecked")
  public DynamicArray(IndexBase idxBase)
  {
	indexBase = idxBase;
	array = (T[]) new Object[INIT_CAPACITY];
	length = 0;
  }


  @SuppressWarnings("unchecked")
  public DynamicArray(IndexBase idxBase, int capacity)
  {
	indexBase = idxBase;
	array = (T[]) new Object[capacity];
	length = 0;
  }


  public int indexOf(T elem)
  {
	for (int i = 0; i < length; i++)
	{
	  if (array[i].equals(elem))
		return indexOut(i);
	}

	return -1;
  }


  public T get(int index)
  {
	int i = indexCheck(index);
	return array[i];
  }


  public void set(int index, T elem)
  {
	int i = indexCheck(index);
	array[i] = elem;
  }


  public void insert(T elem, int index)
  {
	int i = indexCheck(index);

	extend();

	if (length - 1 - i >= 0)
	  arraycopy(array, i, array, i + 1, length - 1 - i);

	array[i] = elem;
	length = length + 1;
  }


  public void delete(int index)
  {
	int i = indexCheck(index);

	if (length - 2 - i >= 0)
	  arraycopy(array, i + 1, array, i, length - 2 - i);

	array[length - 1] = null;
	length = length - 1;
  }


  public void delete(T elem)
  {
	int i = indexOf(elem);
	delete(i);
  }


  public void clear()
  {
	array = copyOf(array, INIT_CAPACITY);
	length = 0;
  }


  public int size()
  {
	return length;
  }


  public void reverse()
  {
	Stack<T> stack = new LinkedStack<>();

	for (int i = 0; i < length; i++)
	  stack.push(array[i]);
	for (int i = 0; i < length; i++)
	  array[i] = stack.pop();
  }


  public boolean containsIndex(int index)
  {
	return indexExists(index);
  }


  private void extend()
  {
	if (array.length > length)
	  return;

	array = copyOf(array, length * EXTEND_COEF);
  }


  private int indexCheck(int index)
  {
	int low = indexBase == ONE ? 1 : 0;
	int high = indexBase == ONE ? length : length - 1;

	if (index < low || index > high)
	  throw new NoSuchIndexException();

	return indexIn(index);
  }


  private boolean indexExists(int index)
  {
	int low = indexBase == ONE ? 1 : 0;
	int high = indexBase == ONE ? length : length - 1;
	return (index < low || index > high);
  }


  private int indexIn(int i)
  {
	return indexBase == ONE ? i - 1 : i;
  }


  private int indexOut(int i)
  {
	return indexBase == ONE ? i + 1 : i;
  }


  public static class NoSuchIndexException
		  extends RuntimeException
  {

  }


  public enum IndexBase
  {
	ZERO,
	ONE
  }

}