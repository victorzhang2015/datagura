package com.victor.atomic;
import java.util.AbstractList;
import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class FreeLockStack<E> extends AbstractList<E>{
    private int[] array;//用数组实现
    private int top; //栈顶指针
    private static final int N_BUCKET =30;//容器总的个数
    private static final boolean DEBUG = false;
    private static final int FIRST_BUCKET_SIZE =8;//每个容器所能装载的能力
    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;//容器总的容纳能力
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
    //封装写入数据的操作
    static class WriteDescription<E>{//写操作这个对象
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
    
    static class Descriptor<E>{//执行写操作的对象
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
    //压栈
    public void push(E e){
    	Descriptor<E> desc;
    	Descriptor<E> newd;
    	do{
    		desc=descripor.get();
    		desc.completeWrite();
    		int pos = desc.size+FIRST_BUCKET_SIZE;
    		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
    		int bucketInd=zeroNumFirst-zeroNumPos;//二维数组的下标
    		if(buckets.get(bucketInd)==null){//如果新的一维数组为空则去创建
    			int newLen =2*buckets.get(bucketInd-1).length();//新的一维数组的长度
    			if(DEBUG){
    				System.out.println("Now new length is"+newLen);
    			}
    			buckets.compareAndSet(bucketInd, null, new AtomicReferenceArray<E>(newLen));//创建新的一维数组
    		}
	    	int idx=(0x80000000>>>zeroNumPos)^pos;//二维数组中的一维数组的下标
	    	newd = new Descriptor<E>(desc.size+1,new WriteDescription<E>(buckets.get(bucketInd),idx,null,e),null);
    	}while(!descripor.compareAndSet(desc, newd));
    	descripor.get().completeWrite();	
    }
    //弹栈
    public E pop(){
    	Descriptor<E> desc;
    		desc=descripor.get();
    		E e=desc.completeRead();
    		if(e!=null){
    			return e;
    		}
    		int pos = desc.size+FIRST_BUCKET_SIZE;
    		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
    		int bucketInd=zeroNumFirst-zeroNumPos;//二维数组的下标
	    	int idx=(0x80000000>>>zeroNumPos)^pos;//二维数组中的一维数组的下标
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