package org.ysnam.householdAccounts.core.filter;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class HouseholdReader extends BufferedReader {
    public HouseholdReader(Reader in) {
        super(in);
    }

    @Getter
    private String year = "2018";

    @Override
    public String readLine() throws IOException {
        String line = super.readLine();
        if(line == null){
            return null;
        }
        year = line.substring(0, 4);
        return line.substring(4);
    }
}
