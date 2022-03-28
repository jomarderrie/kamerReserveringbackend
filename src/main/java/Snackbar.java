import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Snackbar {
    public static void main(String[] args) {
        String[] arr = new String[5];
        arr[0] = "ops";
        arr[1] = "bogs";
        arr[2] = "dogs";
        arr[3] = "cops";
        List<String> sorted = Arrays.stream(arr).sorted().collect(Collectors.toList());
        String[] arr2 = new String[5];
        arr2[0] = "ops";
        arr2[1] = "bocs";
        arr2[2] = "dogs";
        arr2[3] = "cops";
        List<String> sorted1 = Arrays.stream(arr2).sorted().collect(Collectors.toList());
        System.out.println(sorted1);
        System.out.println(sorted);

    }
}
