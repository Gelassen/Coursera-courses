import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Philosophers {

	private static final int MAX_PHILOSOPHERS = 5;

	private static final int MAX_CHOPSTICKS = 5;
	
	private static final int MAX_FEEDS= 5;
	
	public static void main(String[] args) {
		Philosophers wrapper = new Philosophers();
		Chopstick[] chopsticks = new Chopstick[MAX_CHOPSTICKS];
		for (int i = 0; i < MAX_CHOPSTICKS; i++) {
			chopsticks[i] = wrapper.new Chopstick();
		}
		
		// they seating counterclockwise
		ExecutorService pool = Executors.newFixedThreadPool(MAX_PHILOSOPHERS);
		pool.execute(wrapper.new Philosoph("Adam", chopsticks[0], chopsticks[1]));
		pool.execute(wrapper.new Philosoph("Nick", chopsticks[1], chopsticks[2]));
		pool.execute(wrapper.new Philosoph("Di", chopsticks[2], chopsticks[3]));
		pool.execute(wrapper.new Philosoph("Grey", chopsticks[3], chopsticks[4]));
		pool.execute(wrapper.new Philosoph("Watson", chopsticks[4], chopsticks[0]));
	}
	
	public class Philosoph implements Runnable {
		
		private int feedCount = 0;
		
		private int starvingCount = 0;
		
		private String name;
		
		private Chopstick leftChopstick;
		
		private Chopstick rightChopstick;
		
		public Philosoph(String name, Chopstick left, Chopstick right) {
			this.name = name;
			this.leftChopstick = left;
			this.rightChopstick = right;
		}
		
		public void think() {
			System.out.println(String.format("%s is think", name));
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		
		public void eat() {
			System.out.println(String.format("%s is eat", name));
			feedCount++;
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				//  release locks
				leftChopstick.lock.unlock();
				rightChopstick.lock.unlock();
				System.out.println(String.format("%s stops eat", name));
			}
		}

		@Override
		public void run() {
			while (feedCount != MAX_FEEDS) {
				synchronized (this) {
					boolean ready = leftChopstick.getBothChopsticks(name, rightChopstick);
					if (ready) {
//						System.out
						eat();
					} else {
						starvingCount++;
					}
				}
				think();
			}
			System.out.println(String.format("%s complete dinner, startving %s times", name, starvingCount));
		}		
	}
	
	public class Chopstick {
		
		private ReentrantLock lock = new ReentrantLock();
		
		public boolean getBothChopsticks(String philosophers, Chopstick secondChopstick) {
			boolean takeFirst = false;
			boolean takeSecond = false;
			try { 
				takeFirst = lock.tryLock();
				takeSecond = secondChopstick.lock.tryLock();
			} finally {
				if (!(takeFirst && takeSecond)) {
					if (takeFirst) {
						lock.unlock();
					}
					if (takeSecond) {
						secondChopstick.lock.unlock();
					}
				}
			}
			return takeFirst && takeSecond;
		}
		
	}

}
