package com.victor.atomic;
import java.util.AbstractList;
import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class FreeLockStack<E> extends AbstractList<E>{
    private int[] array;//������ʵ��
    private int top; //ջ��ָ��
    private static final int N_BUCKET =30;//�����ܵĸ���
    private static final boolean DEBUG = false;
    private static final int FIRST_BUCKET_SIZE =8;//ÿ����������װ�ص�����
    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;//�����ܵ���������
    private AtomicReference<Descriptor<E>> descripor;
    private static final int zeroNumFirst =Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE);
    
    static class ReadDescriptor<E>{
    	public AtomicReferenceArray<E> addr;
    	public int addr_ind;
    	private ReadDescriptor(AtomicReferenceArray<E> addr, int addr_ind) {
			super();
			this.addr = addr;
			this.addr_ind = addr_ind;
		}
		public E getIt(){
			if(addr.get(addr_ind)!=null){
				return addr.get(addr_ind);
			}
    		return null;
    	}
    }
    //��װд�����ݵĲ���
    static class WriteDescription<E>{//д�����������
    	public E oldv;
    	public E newV;
    	public AtomicReferenceArray<E> addr;
    	public int addr_ind;
		public WriteDescription(AtomicReferenceArray<E> addr,
				int addr_ind,E oldv, E newV) {
			super();
			this.oldv = oldv;
			this.newV = newV;
			this.addr = addr;
			this.addr_ind = addr_ind;
		}
    	
		public void doIt(){
			addr.compareAndSet(addr_ind, oldv, newV);
		}
    	
    	
    }
    
    static class Descriptor<E>{//ִ��д�����Ķ���
    	public int size;
    	volatile WriteDescription<E> writeop;
    	volatile ReadDescriptor<E> readop;
		
    	
    	
    	
    	private Descriptor(int size, WriteDescription<E> writeop,
				ReadDescriptor<E> readop) {
			super();
			this.size = size;
			this.writeop = writeop;
			this.readop = readop;
		}


		public void completeWrite(){
    		WriteDescription<E> tmpop =writeop;
    		if(tmpop!=null){
    			tmpop.doIt();
    			writeop=null;
    		}
    	}
		
		public E completeRead(){
			ReadDescriptor<E> tmpop1 =readop;
    		if(tmpop1!=null){
    			E e=tmpop1.getIt();
    			tmpop1=null;
    			return e;
    		}
    		return null;
    	}
    }
    public FreeLockStack(){
    	buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
    	buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
    	descripor =new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,null,null)); 
    }
    //ѹջ
    public void push(E e){
    	Descriptor<E> desc;
    	Descriptor<E> newd;
    	do{
    		desc=descripor.get();
    		desc.completeWrite();
    		int pos = desc.size+FIRST_BUCKET_SIZE;
    		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
    		int bucketInd=zeroNumFirst-zeroNumPos;//��ά������±�
    		if(buckets.get(bucketInd)==null){//����µ�һά����Ϊ����ȥ����
    			int newLen =2*buckets.get(bucketInd-1).length();//�µ�һά����ĳ���
    			if(DEBUG){
    				System.out.println("Now new length is"+newLen);
    			}
    			buckets.compareAndSet(bucketInd, null, new AtomicReferenceArray<E>(newLen));//�����µ�һά����
    		}
	    	int idx=(0x80000000>>>zeroNumPos)^pos;//��ά�����е�һά������±�
	    	newd = new Descriptor<E>(desc.size+1,new WriteDescription<E>(buckets.get(bucketInd),idx,null,e),null);
    	}while(!descripor.compareAndSet(desc, newd));
    	descripor.get().completeWrite();	
    }
    //��ջ
    public E pop(){
    	Descriptor<E> desc;
    		desc=descripor.get();
    		E e=desc.completeRead();
    		if(e!=null){
    			return e;
    		}
    		int pos = desc.size+FIRST_BUCKET_SIZE;
    		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
    		int bucketInd=zeroNumFirst-zeroNumPos;//��ά������±�
	    	int idx=(0x80000000>>>zeroNumPos)^pos;//��ά�����е�һά������±�
	    	desc = new Descriptor<E>(desc.size,null,new ReadDescriptor<E>(buckets.get(bucketInd),idx-1));
	    	return desc.completeRead();	
    }
	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}