package com.dataserve.migration.spga;

import java.text.DecimalFormat;

/************************
 *
 * Created By Mohammad Awwad 06-June-2020
 *
 ************************/
public class Test {

    public static void main(String[] args) {

        String number = "1000";
        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###");

        System.out.println(formatter.format(amount));
    }
}
