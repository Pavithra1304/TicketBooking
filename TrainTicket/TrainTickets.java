import java.util.Scanner;

public class TrainTickets {
    private int A_Seats;

    public TrainTickets(int seats) {
        this.A_Seats = seats;
    }

    public synchronized void booking(int n) throws Exception {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid number of seats.");
        }
        System.out.println(Thread.currentThread().getName() + " attempting to book " + n + " seats.");
        if (n > A_Seats) {
            throw new Exception("Seats not available.");
        }
        try {
            Thread.sleep(100);
        }

        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception("Interruption Occured.");
        }
        A_Seats -= n;
        System.out.println(Thread.currentThread().getName() + " succesfully booked " + n + " seats. ");
        System.out.println("remaining seats : " + A_Seats);

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TrainTickets trainTickets = new TrainTickets(150);

        System.out.print("Number of User : ");
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("User" + (i + 1) + " : ");
            a[i] = sc.nextInt();
        }
        Thread[] t = new Thread[n];
        for (int i = 0; i < n; i++) {
            int seat = a[i];
            Runnable task = () -> {
                try {
                    trainTickets.booking(seat);
                } catch (Exception e) {
                    System.err.println(Thread.currentThread().getName() + " failed " + e.getMessage());
                }
            };
            t[i] = new Thread(task, "User-" + (i + 1));
            t[i].start();
        }
        for (Thread th : t) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }
}