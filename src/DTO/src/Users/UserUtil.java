package Users;
import java.util.*;

public class UserUtil {

    public static boolean areUsersListsIdentical(List<UserImpl> list1, List<UserImpl> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        // Create a copy of each list to avoid modifying the original lists
        List<UserImpl> copy1 = new ArrayList<>(list1);
        List<UserImpl> copy2 = new ArrayList<>(list2);

        // Sort the lists based on the username to ensure consistent order for comparison
        Collections.sort(copy1, Comparator.comparing(UserImpl::getUsername));
        Collections.sort(copy2, Comparator.comparing(UserImpl::getUsername));

        // Compare the elements in both lists
        for (int i = 0; i < copy1.size(); i++) {
            UserImpl user1 = copy1.get(i);
            UserImpl user2 = copy2.get(i);
            if (!user1.equals(user2)) {
                return false;
            }
        }

        return true;
    }
}

