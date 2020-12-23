package rs.dusk.game.entity.character;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A character list is a specialized collection which stores all characters in the world. The additional functionality
 * it provides is management of indexes for the characters.
 * <p>
 * Indexes need to be set when characters are added to the world, and removed.
 *
 * @author [primary author unknown]
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18th, 2020
 */
public class CharacterList<T extends Character> extends AbstractCollection<T> {
	
	private static final int MIN_VALUE = 1;
	
	private final Object lock = new Object();
	
	public Object[] entities;
	
	public Set<Integer> indicies = new HashSet<>();
	
	public int curIndex = MIN_VALUE;
	
	public int capacity;
	
	public CharacterList(int capacity) {
		entities = new Object[capacity];
		this.capacity = capacity;
	}
	
	public Iterator<T> iterator() {
		synchronized (lock) {
			return new CharacterListIterator<T>(entities, indicies, this);
		}
	}
	
	public int size() {
		return indicies.size();
	}
	
	public boolean add(T entity) {
		synchronized (lock) {
			add(entity, curIndex);
			return true;
		}
	}
	
	public void remove(T entity) {
		synchronized (lock) {
			entities[entity.getIndex()] = null;
			indicies.remove(entity.getIndex());
			decreaseIndex();
		}
	}
	
	public void decreaseIndex() {
		curIndex--;
		if (curIndex <= capacity) {
			curIndex = MIN_VALUE;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T remove(int index) {
		synchronized (lock) {
			Object temp = entities[index];
			entities[index] = null;
			indicies.remove(index);
			decreaseIndex();
			return (T) temp;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T get(int index) {
		synchronized (lock) {
			if (index >= entities.length) {
				return null;
			}
			return (T) entities[index];
		}
	}
	
	public void add(T entity, int index) {
		if (entities[curIndex] != null) {
			increaseIndex();
			add(entity, curIndex);
		} else {
			entities[curIndex] = entity;
			entity.setIndex(index);
			indicies.add(curIndex);
			increaseIndex();
		}
	}
	
	public void increaseIndex() {
		curIndex++;
		if (curIndex >= capacity) {
			curIndex = MIN_VALUE;
		}
	}
	
	public boolean contains(T entity) {
		return indexOf(entity) > -1;
	}
	
	public int indexOf(T entity) {
		for (int index : indicies) {
			if (entities[index].equals(entity)) {
				return index;
			}
		}
		return -1;
	}
}
