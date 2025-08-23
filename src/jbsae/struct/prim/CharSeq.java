package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

// TODO: Consider turning into circular queue
public class CharSeq{
    public char[] items;
    public int size;

    public CharSeq(){
        this(4);
    }

    public CharSeq(int size){
        items = new char[size];
    }

    public CharSeq(char... values){
        this(values.length);
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public CharSeq(String str){
        this(str.length());
        add(str);
    }

    public char[] list(){
        char[] values = new char[size];
        copy(items, values, size);
        return values;
    }

    public CharSeq set(char value, int index){
        items[index] = value;
        return this;
    }

    public CharSeq set(char... values){
        clear();
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public CharSeq set(CharSeq values){
        items = values.items;
        size = values.size;
        return this;
    }

    public CharSeq add(char value){
        if(size >= items.length) resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public CharSeq add(char value, int index){
        if(size >= items.length) resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public CharSeq addAll(char... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public CharSeq add(String str){
        for(int i = 0;i < str.length();i++) add(str.charAt(i));
        return this;
    }

    public CharSeq add(String str, int index){
        if(size + str.length() >= items.length) resize(max(8, (size + str.length()) * 2));
        shift(items, index, (size += str.length()), str.length());
        for(int i = 0;i < str.length();i++) items[i + index] = str.charAt(i);
        return this;
    }


    public CharSeq remove(int index){
        shift(items, index + 1, size--, -1);
        items[size] = 0;
        return this;
    }

    public CharSeq removeValue(char value){
        for(int i = 0;i < size;i++){
            if(items[i] == value){
                remove(i);
                break;
            }
        }
        return this;
    }

    public CharSeq remove(char... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
        return this;
    }

    public CharSeq removeAll(char... values){
        for(int i = 0;i < values.length;i++){
            for(int j = 0;j < size;j++) if(items[j] == values[i]) remove(j--);
        }
        return this;
    }
    public CharSeq remove(int index, int range){
        shift(items, index + range, (size -= range) + range, -range);
        for(int i = 0;i < range;i++) items[size + i] = 0;
        return this;
    }

    public CharSeq remove(String str){
        int index = indexOf(str);
        if(index >= 0) remove(index, str.length());
        return this;
    }

    public CharSeq removeAll(String str){
        int index;
        while((index = indexOf(str)) >= 0) remove(index, str.length());
        return this;
    }

    public char get(int index){
        return items[index];
    }

    public boolean contains(char value){
        for(int i = 0;i < size;i++) if(items[i] == value) return true;
        return false;
    }

    public boolean contains(Boolfi condition){
        for(int i = 0;i < size;i++) if(condition.get(items[i])) return true;
        return false;
    }

    public CharSeq each(Intc cons){
        for(int i = 0;i < size;i++) cons.get(items[i]);
        return this;
    }

    public CharSeq sort(){
        resize(size);
        sortArr(items);
        return this;
    }

    public CharSeq clear(){
        fill(items, '\0');
        size = 0;
        return this;
    }

    public CharSeq resize(int newSize){
        char[] items = new char[newSize];
        copy(this.items, items, size);
        this.items = items;
        return this;
    }

    public CharSeq lower(){
        for(int i = 0;i < size;i++) items[i] = lowerCase((char)items[i]);
        return this;
    }

    public CharSeq upper(){
        for(int i = 0;i < size;i++) items[i] = upperCase((char)items[i]);
        return this;
    }

    public CharSeq replace(String from, String to){
        CharSeq newSeq = new CharSeq();
        for(int i = 0;i <= size - from.length();i++){
            if(matches(i, from)){
                newSeq.add(to);
                i += from.length() - 1;
            }else newSeq.add(items[i]);
        }
        items = newSeq.items;
        size = newSeq.size;
        return this;
    }

    public CharSeq substring(int start, int end){
        CharSeq substring = new CharSeq();
        for(int i = 0;i < end - start;i++) substring.add((char)items[i + start]);
        return substring;
    }

    public char charAt(int index){
        return (char)get(index);
    }

    public boolean equals(String str){
        return matches(0, str);
    }

    public boolean matches(int index, String str){
        for(int i = 0;i < str.length();i++) if(str.charAt(i) != items[i + index]) return false;
        return true;
    }

    public boolean contains(String str){
        return indexOf(str) >= 0;
    }

    public int indexOf(String str){
        for(int i = 0;i < size - str.length();i++) if(matches(i, str)) return i;
        return -1;
    }

    public CharSeq trim(){
        int start, end;
        for(start = 0;start < size;start++) if(items[start] > ' ') break;
        for(end = size - 1;end >= 0;end--) if(items[end] > ' ') break;
        if(end >= start){
            shift(items, start, start + (size = end - start + 1), -start);
            resize(size);
        }else clear();
        return this;
    }

    @Override
    public String toString(){
        char[] chars = new char[size];
        for(int i = 0;i < size;i++) chars[i] = (char)items[i];
        return combine(chars);
    }
}
