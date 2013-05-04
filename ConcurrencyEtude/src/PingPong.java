import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class PingPong {
	
	 public static void main(String args[]) throws IOException {
		 System.out.println("Let's the battle begin: ");
		 PingPong parent = new PingPong();
		 Proxy proxy = parent.new Proxy();
		 proxy.startBattle();
	 }
	 

	 /*package*/ class Proxy {
		 
		 public void startBattle() {
			 new StraigthforwardSolution().start();
		 }
		 
	 }
	 
	 /*package*/ class StraigthforwardSolution {
		 
		 private static final int MAX_ACTIONS = 8;
		 
		 private static final int MAX_THREADS = 2;
		 
		 private ConcurrentLinkedQueue<String> actions = new ConcurrentLinkedQueue<String>();
		 
		 private ExecutorService pool;
		 
		 private AtomicInteger turn; 
		 
		 public StraigthforwardSolution() {
			 turn.set(0);
			 for (int i = 0; i < MAX_ACTIONS; i++) {
				 // even number
				 if ((i % 2) == 0) {
					 actions.add("Ping \n");
				 } else {
					 actions.add("Pong \n");
				 }
			 }
		 }
		 
		 public void start() {
			 pool = Executors.newScheduledThreadPool(MAX_THREADS);
			 pool.execute(new Command());
			 pool.execute(new Command());
		 }
		 
		 // if the order is important we should use mutex to be sure in right order
		 public synchronized void getActionsInOrder() {
			 int currentTurn = turn.getAndIncrement();
			 currentTurn % 
		 }
		 
		 private synchronized void dispose() {
			 if (pool != null && !pool.isShutdown()) {
				 pool.shutdown();
				 System.out.println("Done!");
			 }
		 }
		 
		 private class Command implements Runnable {

			@Override
			public void run() {
				do {
					String action = actions.poll();
					if (action != null) {
						System.out.println(action);
					} else {
						dispose();
					}
				} while (!actions.isEmpty());

			}
			 
		 }
		 
	 }

	 
}
