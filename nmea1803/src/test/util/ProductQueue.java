package test.util;

    import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
      
    public class ProductQueue<T> {  
      
        private T[] items;  
        private static int temp = 1;
        private final Lock lock = new ReentrantLock();  
      
        private Condition notFull = lock.newCondition();  
      
        /**
		 * @return the temp
		 */
		public static int getTemp() {
			return temp;
		}

		/**
		 * @param temp the temp to set
		 */
		public static void setTemp(int temp) {
			ProductQueue.temp = temp;
		}
		private Condition notEmpty = lock.newCondition();  
      
        //  
        private int head, tail, count;  
      
        public ProductQueue(int maxSize) {  
            items = (T[]) new Object[maxSize];  
        }  
      
        public ProductQueue() {  
            this(10);  
        }  
      
        public void put(T t) throws InterruptedException {  
            lock.lock();  
            try {  
                while (count == getCapacity()) {  
                    notFull.await();  
                }  
                items[tail] = t;  
                if (++tail == getCapacity()) {  
                    tail = 0;  
                }  
                ++count;  
                notEmpty.signalAll();  
            } finally {  
                lock.unlock();  
            }  
        }  
      
        public T take() throws InterruptedException {  
            lock.lock();  
            try {  
                while (count == 0) {  
                    notEmpty.await();  
                }  
                T ret = items[head];  
                items[head] = null;//GC  
                //  
                if (++head == getCapacity()) {  
                    head = 0;  
                }  
                --count;  
                notFull.signalAll();  
                return ret;  
            } finally {  
                lock.unlock();  
            }  
        }  
      
        public int getCapacity() {  
            return items.length;  
        }  
      
        public int size() {  
            lock.lock();  
            try {  
                return count;  
            } finally {  
                lock.unlock();  
            }  
        }  
        public static void main(String[] args) {
        	ProductQueue<Integer> queue = new ProductQueue<Integer>();
        	for(int i=0;i<5;i++){
        		Thread thread = new Thread(new MyThread1(queue));
        		thread.start();
        	}
        	for(int i=0;i<5;i++){
        		Thread thread = new Thread(new MyThread2(queue));
        		thread.start();
        	}
		}
      
    } 
    
    class MyThread1 implements Runnable{
    	private ProductQueue<Integer> queue;
    	public MyThread1(ProductQueue<Integer> queue1){
    		this.queue=queue1;
    	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int i =queue.getTemp();
			queue.setTemp(i+1);
			try {
				queue.put(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
    
    class MyThread2 implements Runnable{
    	private ProductQueue<Integer> queue ;
    	public MyThread2(ProductQueue<Integer> queue1){
    		this.queue=queue1;
    	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int k = -1;
			try {
				k=queue.take();
				System.out.println("从队列中取出的值为"+k);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }