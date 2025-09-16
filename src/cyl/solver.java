public class solver
{
    public void rocketscience(int [] numToUse, int targetNum, boolean [] used, int [] temp, int tempIndex, int currentTotal){
        if(currentTotal == targetNum || approximation(currentTotal, targetNum)){
            showSolution(targetNum, temp, tempIndex);
        }else{
            for(int i = 0; i < numToUse.length; i++){
                //BLANK SPACE
                if(!used[i]){
                    used[i] = true;
                    temp[tempIndex] = numToUse[i];
                    rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, operation(/*BLANK SPACE*/));
                    used[i] = false;
                }
            }
        }
    }
}