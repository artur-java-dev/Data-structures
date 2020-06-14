package struct;


import interfaces.Set;

import java.util.Iterator;
import java.util.function.Predicate;


public class SortedListSet<T extends Comparable<T>>
		implements Set<T>
{

  private Elem<T> sentinel;
  private long size;


  public SortedListSet()
  {
	sentinel = new Elem<>();
	size = 0;
  }


  public SortedListSet(Set<T> set)
  {

  }


  @Override
  public void insert(T elem)
  {
	Elem<T> cur = sentinel;
	while (cur.next != null)
	{
	  T obj = cur.next.object;

	  if (obj.equals(elem)) return;
	  if (obj.compareTo(elem) > 0) break;

	  cur = cur.next;
	}

	Elem<T> e = new Elem<>(elem);
	e.next = cur.next;
	e.prev = cur;
	cur.next = e;
	size++;
  }


  @Override
  public void delete(T elem)
  {
	Elem<T> x = sentinel.next;
	while (x != null)
	{
	  if (x.object.equals(elem))
	  {
		x.prev.next = x.next;
		if (x.next != null)
		  x.next.prev = x.prev;
		size--;
	  }

	  x = x.next;
	}
  }


  @Override
  public void clear()
  {
	sentinel = null;
	size = 0;
  }


  @Override
  public long size()
  {
	return size;
  }


  @Override
  public boolean isMember(T elem)
  {
	Elem<T> cur = sentinel.next;
	while (cur != null)
	{
	  if (cur.object.equals(elem))
		return true;
	  cur = cur.next;
	}

	return false;
  }


  @Override
  public boolean equals(Set<T> set)
  {
	if (set == null || getClass() != set.getClass())
	  return false;

	if (set == this)
	  return true;

	if (set.size() != size)
	  return false;

	SortedListSet<T> other = (SortedListSet<T>) set;

	return equalsElements(other);
  }


  @Override
  public T select()
  {
	if (isEmpty())
	  return null;
	return sentinel.next.object;
  }


  @Override
  public Set<T> union(Set<T> set)
  {
	if (set.isEmpty())
	  return this;

	return union(this, new SortedListSet<>(set));
  }


  @Override
  public Set<T> intersection(Set<T> set)
  {
	if (set.isEmpty())
	  return new SortedListSet<>();

	return intersection(this, new SortedListSet<>(set));
  }


  @Override
  public Set<T> difference(Set<T> set)
  {
	if (set.isEmpty())
	  return this;

	if (isEmpty())
	  return set;

	if (set == this)
	  return new SortedListSet<>();

	return difference(this, new SortedListSet<>(set));
  }


  @Override
  public Set<T> selectByCondition(Predicate<T> cond)
  {
	SortedListSet<T> set = new SortedListSet<>();

	Elem<T> x = sentinel.next;
	Elem<T> cur = set.sentinel;
	while (x != null)
	{
	  if (cond.test(x.object))
	  {
		Elem<T> forAdd = new Elem<>(x.object);
		forAdd.next = cur.next;
		forAdd.prev = cur;
		cur.next = forAdd;
		set.size++;
		cur = forAdd;
	  }
	  x = x.next;
	}

	return set;
  }


//  @Override
//  public Iterator<T> iterator()
//  {
//	return new SortedListSetIterator();
//  }


  private SortedListSet<T> difference(SortedListSet<T> a, SortedListSet<T> b)
  {
	SortedListSet<T> diff = new SortedListSet<>();

	Elem<T> acur = a.sentinel.next;
	Elem<T> bcur = b.sentinel.next;
	Elem<T> cur = diff.sentinel;
	while (acur != null)
	{
	  if (acur.object.equals(bcur.object))
	  {
		acur = acur.next;
		bcur = bcur.next;
	  }
	  else if (acur.object.compareTo(bcur.object) < 0)
	  {
		cur.next = new Elem<>();
		cur = cur.next;
		cur.object = acur.object;
		acur = acur.next;
	  }
	  else
		bcur = bcur.next;
	}

	cur.next = null;

	return diff;
  }


  private boolean equalsElements(SortedListSet<T> set)
  {
	Elem<T> cur1 = sentinel.next;
	Elem<T> cur2 = set.sentinel.next;
	while (cur1 != null)
	{
	  if (!cur1.object.equals(cur2.object))
		return false;
	  cur1 = cur1.next;
	}

	return true;
  }


  private SortedListSet<T> intersection(SortedListSet<T> a, SortedListSet<T> b)
  {
	SortedListSet<T> result = new SortedListSet<>();

	Elem<T> acur = a.sentinel.next;
	Elem<T> bcur = b.sentinel.next;
	Elem<T> cur = result.sentinel;
	while (acur != null && bcur != null)
	{
	  if (acur.object.equals(bcur.object))
	  {
		cur.next = new Elem<>();
		cur = cur.next;
		cur.object = acur.object;
		acur = acur.next;
		bcur = bcur.next;
	  }
	  else if (acur.object.compareTo(bcur.object) < 0)
	  {
		acur = acur.next;
	  }
	  else
		bcur = bcur.next;
	}

	cur.next = null;

	return result;
  }


  private SortedListSet<T> union(SortedListSet<T> a, SortedListSet<T> b)
  {
	SortedListSet<T> result = new SortedListSet<>();

	Elem<T> acur = a.sentinel.next;
	Elem<T> bcur = b.sentinel.next;
	Elem<T> cur = result.sentinel;
	while (acur != null && bcur != null)
	{
	  T obj;
	  if (acur.object.equals(bcur.object))
	  {
		obj = acur.object;
		acur = acur.next;
		bcur = bcur.next;
	  }
	  else if (acur.object.compareTo(bcur.object) < 0)
	  {
		obj = acur.object;
		acur = acur.next;
	  }
	  else
	  {
		obj = bcur.object;
		bcur = bcur.next;
	  }

	  cur.next = new Elem<>();
	  cur = cur.next;
	  cur.object = obj;
	}

	cur.next = null;

	return result;
  }


  private class SortedListSetIterator
		  implements Iterator<T>
  {

	Elem<T> pos = sentinel;


	@Override
	public boolean hasNext()
	{
	  return pos.next != null;
	}


	@Override
	public T next()
	{
	  T obj = pos.next.object;
	  pos = pos.next;

	  return obj;
	}

  }

}