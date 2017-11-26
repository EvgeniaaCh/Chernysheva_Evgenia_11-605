package FirstTask;

/**
 * Created by Dmitry on 10.06.2017.
 */
public class Site {

    private String name;

    public String getName() {
        return name;
    }

    public long getUsersCount() {
        return usersCount;
    }

    public Site(String name, long usersCount) {

        this.name = name;
        this.usersCount = usersCount;
    }

    private long usersCount;
}
