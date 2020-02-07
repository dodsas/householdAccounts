package org.ysnam.householdAccounts.core.simple;

import org.junit.Test;

public class MyTest {

    @Test
    public void IntegerTest(){
        Integer i = 0;
        Integer i2 = 0;

        if(i == i2){
            System.out.println("단순비교 가능");
        }
        else{
            System.out.println("단순비교 불가");
        }
    }
}
