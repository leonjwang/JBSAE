package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class Set<T> implements Iterable<T>{
  public T[] table;
	public int size = 0;

  public Set(){
    table = (T[])new Object[64];
  }

  public Set(Object... values){
        this();
        for(Object t : values) add((T)t);
    }
  
  public void add(T value){
    T[] table = this.table;

        int h = value.hashCode();
        int[] checks = new int[] {hash1(h), hash2(h), hash3(h)};
        for(int i = 0;i < 3;i ++) if(eql(table[checks[i]], value)) return;

        for(int i = 0;i < 3;i ++){
          if(table[checks[i]] == null){
            table[checks[i]] = value;
            size ++;
            return;
          }
        }

        resize(table.length << 1);
        add(value);
  }

  public int hash(int h, int prime){
        h *= prime;
        return (h ^ h >>>  (31 - trailZeros(table.length))) & (table.length - 1);
  }
  
    @Override
    public Iterator<T> iterator(){
        return null;
    }
}
