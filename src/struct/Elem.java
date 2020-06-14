package struct;


class Elem<E>
{

  E object;
  Elem<E> next;
  Elem<E> prev;


  Elem(E obj)
  {
	object = obj;
  }


  public Elem()
  {
	object = null;
  }

}