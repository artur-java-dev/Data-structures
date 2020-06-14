package struct;


import interfaces.Dictionary;

import java.util.Optional;


public class HashTable<K, V>
		implements Dictionary<K, V>
{

  private DynamicArray<LinkedList<Entry>> blocks;
  private long size;


  public HashTable()
  {
	blocks = new DynamicArray<>();
	size = 0;
  }


  @Override
  public Optional<V> get(K key)
  {
	int h = hash(key);

	if (!blocks.containsIndex(h))
	  throw new InvalidHash(h, key);

	return blocks.get(h)
				 .getByCondition(x -> x.key.equals(key))
				 .findFirst()
				 .map(x -> x.value);
  }


  @Override
  public void insert(K key, V value)
  {
	int h = hash(key);

	if (!blocks.containsIndex(h))
	  throw new InvalidHash(h, key);

	LinkedList<Entry> block = blocks.get(h);

	Optional<Entry> entry =
			block.getByCondition(x -> x.key.equals(key))
				 .findFirst();

	if (entry.isPresent())
	{
	  entry.get().value = value;
	  return;
	}

	Entry e = new Entry(key, value);
	block.insertAtBegin(e);
	size++;

	rehashing();
  }


  @Override
  public void delete(K key)
  {
	int h = hash(key);

	if (!blocks.containsIndex(h))
	  throw new InvalidHash(h, key);

	blocks.get(h)
		  .deleteByCondition(x -> x.key.equals(key));
	size--;
  }


  @Override
  public void clear()
  {
	blocks.clear();
  }


  @Override
  public long size()
  {
	return size;
  }


  private int hash(K key)
  {
	return key.hashCode() % blocks.size();
  }


  private int hashForRehashing(K key, int blocksCount)
  {
	return key.hashCode() % blocksCount;
  }


  private void rehashing()
  {
	int n = blocks.size();
	int blocksCount = n * 2;

	if (size <= 4 * n / 3) return;

	DynamicArray<LinkedList<Entry>> table = new DynamicArray<>(ONE, blocksCount);
	for (int i = 1; i <= n; i++)
	{
	  LinkedList<Entry> block = blocks.get(i);
	  while (!block.isEmpty())
	  {
		Entry e = block.first();
		block.deleteFromBegin();
		int h = hashForRehashing(e.key, blocksCount);
		table.get(h).insertAtBegin(e);
	  }
	}

	blocks = table;
  }


  private static class InvalidHash
		  extends RuntimeException
  {

	public InvalidHash(int hash, Object obj)
	{
	  super("Invalid hash: " + hash + " for object: " + obj.toString());
	}

  }


  private class Entry
  {

	K key;
	V value;


	public Entry(K k, V v)
	{
	  key = k;
	  value = v;
	}

  }

}