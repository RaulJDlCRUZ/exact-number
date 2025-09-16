# RocketScience

In this file, a first approximation of the "cyl" algorithm is provided.
Actually, can be found at `src/main/java/com/kangoo/cyl/domain/service/Solver`

```java
public class solver {
    public void rocketscience(int[] numToUse, int targetNum, boolean[] used, int[] temp, int tempIndex,
            int currentTotal) {
        if (currentTotal == targetNum || approximation(currentTotal, targetNum)) {
            showSolution(targetNum, temp, tempIndex);
        } else {
            for (int i = 0; i < numToUse.length; i++) {
                // BLANK SPACE
                if (!used[i]) {
                    used[i] = true;
                    temp[tempIndex] = numToUse[i];
                    rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, operation(/* BLANK SPACE */));
                    used[i] = false;
                }
            }
        }
    }
}
```