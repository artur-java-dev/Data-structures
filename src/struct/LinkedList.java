package struct;


import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class LinkedList<T>
{

  private Elem<T> head;
  private Elem<T> tail;
  private int length;


  public LinkedList()
  {
	length = 0;
  }


  public boolean isEmpty()
  {
	return length == 0;
  }


  public T first()
  {
	if (isEmpty())
	  throw new EmptyListException();

	return head.object;
  }


  public T last()
  {
	if (isEmpty())
	  throw new EmptyListException();

	return tail.object;
  }


  public boolean contains(T elem)
  {
	Elem<T> x = head;
	while (x != null)
	{
	  if (x.object.equals(elem))
		return true;
	  x = x.next;
	}

	return false;
  }


  public Stream<T> getByCondition(Predicate<T> cond)
  {
	return getElementsByCondition(cond).map(x -> x.object);
  }


  public void insertAtBegin(T elem)
  {
	Elem<T> x = new Elem<>(elem);

	x.next = head;
	if (head != null)
	  head.prev = x;
	head = x;
	x.prev = null;

	length = length + 1;

	if (length == 1)
	  tail = head;
  }


  public void insertAtEnd(T elem)
  {
	Elem<T> x = new Elem<>(elem);

	x.prev = tail;
	if (tail != null)
	  tail.next = x;
	tail = x;
	x.next = null;

	length = length + 1;

	if (length == 1)
	  head = tail;
  }


  public void insertBefore(T before, T elem)
  {
	insertBeforeElem(getElem(before), elem);
  }


  public void insertAfter(T after, T elem)
  {
	insertAfterElem(getElem(after), elem);
  }


  public void delete(T elem)
  {
	deleteElem(getElem(elem));
  }


  public void deleteFromBegin()
  {
	if (isEmpty())
	  throw new NoSuchElementException();

	deleteElem(head);
  }


  public void deleteFromEnd()
  {
	if (isEmpty())
	  throw new NoSuchElementException();

	deleteElem(tail);
  }


  public void deleteBefore(T before)
  {
	Elem<T> x = getElem(before);
	if (x.prev != null)
	  deleteElem(x.prev);
  }


  public void deleteAfter(T after)
  {
	Elem<T> x = getElem(after);
	if (x.next != null)
	  deleteElem(x.next);
  }


  public void deleteAllBefore(T before)
  {
	Elem<T> x = getElem(before);
	while (x.prev != null)
	  deleteElem(x.prev);
  }


  public void deleteAllAfter(T after)
  {
	Elem<T> x = getElem(after);
	while (x.next != null)
	  deleteElem(x.next);
  }


  public void deleteByCondition(Predicate<T> cond)
  {
	getElementsByCondition(cond).forEach(this::deleteElem);
  }


  public void clear()
  {
	head = null;
	tail = null;
	length = 0;
  }


  public int size()
  {
	return length;
  }


  public Iterator<T> iterator()
  {
	return new LinkedListIterator();
  }


  private void deleteElem(Elem<T> x)
  {
	if (x.prev != null)
	  x.prev.next = x.next;
	else
	  head = x.next;

	if (x.next != null)
	  x.next.prev = x.prev;
	else
	  tail = x.prev;

	length = length - 1;
  }


  private Elem<T> getElem(T elem)
  {
	Elem<T> x = head;
	while (x != null)
	{
	  if (x.object.equals(elem))
		return x;
	  x = x.next;
	}

	throw new NoSuchElementException();
  }


  private Stream<Elem<T>> getElementsByCondition(Predicate<T> cond)
  {
	Stream.Builder<Elem<T>> builder = Stream.builder();

	Elem<T> x = head;
	while (x != null)
	{
	  if (cond.test(x.object))
		builder.add(x);
	  x = x.next;
	}

	return builder.build();
  }


  private void insertAfterElem(Elem<T> after, T elem)
  {
	Elem<T> x = new Elem<>(elem);

	x.next = after.next;
	x.prev = after;
	after.next = x;

	length = length + 1;
  }


  private void insertBeforeElem(Elem<T> before, T elem)
  {
	Elem<T> x = new Elem<>(elem);

	x.next = before;
	x.prev = before.prev;
	before.prev = x;

	length = length + 1;
  }


  public static class EmptyListException
		  extends RuntimeException
  {

  }
  public static class NoSuchElementException
		  extends RuntimeException
  {

  }
  private class LinkedListIterator
		  implements Iterator<T>
  {

	Elem<T> pos = head;


	@Override
	public boolean hasNext()
	{
	  return pos != null;
	}


	@Override
	public T next()
	{
	  T obj = pos.object;
	  pos = pos.next;

	  return obj;
	}

  }

}