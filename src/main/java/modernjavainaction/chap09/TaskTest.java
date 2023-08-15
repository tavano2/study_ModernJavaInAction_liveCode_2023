package modernjavainaction.chap09;

public class TaskTest {

    interface Task {
        public void execute();
    }

    public static void doSomething(Runnable r) {
        r.run();
    }
    public static void doSomething(Task a) {
        a.execute();
    }

    public static void main(String[] args) {
        doSomething(new Task() {
            @Override
            public void execute() {
                System.out.println("Danger! danger!");
            }
        });
    };


}
