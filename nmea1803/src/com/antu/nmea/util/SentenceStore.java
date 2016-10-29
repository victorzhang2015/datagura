package com.antu.nmea.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.antu.nmea.sentence.IGroupedSentence;

public class SentenceStore<K, V extends IGroupedSentence> {

	private HashMap<K, V> storedSentences = new HashMap<K, V>();
	
	/**
	 * @return the storedSentences
	 */
	public HashMap<K, V> getStoredSentences() {
		return storedSentences;
	}

	/**
	 * @param storedSentences the storedSentences to set
	 */
	public void setStoredSentences(HashMap<K, V> storedSentences) {
		this.storedSentences = storedSentences;
	}

	public SentenceStore() {
	}
	
	public List<V> getExpiredItems(Date checkTime, int milliSeconds) {
		
		List<V> result = new ArrayList<V>();
		List<K> keys = new ArrayList<K>();
		
		if (checkTime == null || milliSeconds < 0)
			return result;
		
		long time = checkTime.getTime() - milliSeconds;
		System.out.println("time"+time);
		synchronized (this) {
			
			for (K key : this.storedSentences.keySet()) {
				if (this.storedSentences.get(key).getReceiveDate().getTime() > time) {
					keys.add(key);
					result.add(this.storedSentences.get(key));
				}
			}
			
			for (K key : keys) {
				this.storedSentences.remove(key);
			}
		}
		keys.clear();
		
		return result;
	}
	
	public V addItem(K key, V value) {
		
		if (value.getTotalNumberOfSentences() == 1) {
			return value;			
		}
		
		synchronized (this) {
			
			if (this.storedSentences.containsKey(key)) {
				
				V oldValue = this.storedSentences.get(key);
				
				if (oldValue.getSentenceNumber() + 1 == value.getSentenceNumber()) {
					
					if (value.getSentenceNumber() == value.getTotalNumberOfSentences()) {
						
						oldValue.concatenate(value);
						System.out.println("here555555555");
						this.storedSentences.remove(key);
						return oldValue;
					} else {
						oldValue.concatenate(value);
						System.out.println("here444444444");
						return null;
					}
					
				} else {
					this.storedSentences.remove(key);
					
					if (value.getSentenceNumber() == 1)
						this.storedSentences.put(key, value);
					return oldValue;
				}
				
			} else if (value.getSentenceNumber() == 1) {
				this.storedSentences.put(key, value);
				System.out.println("here3333");
				return null;
			} else {
				return null;
			}
		}
	}
}
