package jbsae.struct.prim;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

//TODO: finish
public class CharSeq extends IntSeq{
    public CharSeq(){
        super();
    }

    public CharSeq(String str){
        this();
        add(str);
    }

    public CharSeq add(String str){
        for(int i = 0;i < str.length();i++) add(str.charAt(i));
        return this;
    }

    public CharSeq add(String str, int index){
        int[] items = this.items;
        //TODO: This doesn't work
        if(size + str.length() >= items.length) items = resize(max(8, size * 2, size + str.length() + 1));
        shift(items, index, (size += str.length()), str.length());
        for(int i = 0;i < str.length();i++) items[i + index] = str.charAt(i);
        return this;
    }

    public CharSeq remove(int index, int range){
        int[] items = this.items;
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

    public CharSeq lower(){
        for(int i = 0;i < size;i++) items[i] = lowerCase((char)items[i]);
        return this;
    }

    public CharSeq upper(){
        for(int i = 0;i < size;i++) items[i] = upperCase((char)items[i]);
        return this;
    }

    public CharSeq replace(String from, String to){
        for(int i = 0;i < size - from.length();i++){
            if(matches(i, from)){
                remove(i, from.length());
                add(to, i);
            }
        }
        return this;
    }

    public char charAt(int index){
        return (char)get(index);
    }

    public String substring(int start, int end){
        char[] chars = new char[end - start];
        for(int i = 0;i < chars.length;i ++) chars[i] = (char)items[i + start];
        return arrToString(chars);
    }

    public boolean matches(int index, String str){
        for(int i = 0;i < str.length();i++){
            if(str.charAt(i) != items[i + index]) return false;
        }
        return true;
    }

    public boolean contains(String str){
        return indexOf(str) >= 0;
    }

    public int indexOf(String str){
        for(int i = 0;i < size - str.length();i++) if(matches(i, str)) return i;
        return -1;
    }

    @Override
    public String toString(){
        char[] chars = new char[size];
        for(int i = 0;i < size;i++) chars[i] = (char)items[i];
        return arrToString(chars);
    }
}
