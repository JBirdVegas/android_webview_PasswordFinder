package com.jbirdvegas.webview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.jbirdvegas.webview.aokp.CMDProcessor;
import com.jbirdvegas.webview.aokp.Executable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PasswordFinder extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CMDProcessor cmdProcessor = new CMDProcessor();
        setContentView(R.layout.main);
        Executable findFiles = new Executable("find /data/data/. | grep webview.db");
        List<String> rowList = new LinkedList<String>();
        for (String db : cmdProcessor.su.runWaitFor(findFiles).getStdout().split("\n"))
            rowList.add(db + ": " + cmdProcessor.su.runWaitFor(new Executable(
                    String.format("sqlite3 -cmd \"SELECT * FROM password;\" %s '.quit'", db))).getStdout());
        ArrayList<String> passList = new ArrayList<String>(0);
        for (String s : rowList) {
            if (s != null && !s.isEmpty()) {
                for (String split : s.split("\n")) {
                    if (s.split("\n").length > 1) {
                        passList.add(split);
                    }
                }
            }
        }
        ((ListView) findViewById(R.id.listView)).setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, passList));
    }
}