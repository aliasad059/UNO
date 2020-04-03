import java.util.Comparator;

public class PointComparator implements Comparator {
    @Override
    public  int compare(Object o1, Object o2) {
        Player player1 = (Player) o1;
        Player player2 = (Player) o2;
        if (player1.point == player2.point)
            return 0;
        else if(player1.point > player2.point)
            return 1;
        else return -1;
    }
}
