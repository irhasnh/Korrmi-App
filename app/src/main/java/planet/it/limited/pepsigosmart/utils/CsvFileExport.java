package planet.it.limited.pepsigosmart.utils;

/**
 * Created by Planet IT
 */

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;

public class CsvFileExport {
    Context context;
    LocalStoragePepsiDB localStorageDB;
    ClearAllSaveData clearAllSaveData;

    public CsvFileExport(Context ct) {
        this.context = ct;
        localStorageDB = new LocalStoragePepsiDB(context);
        clearAllSaveData = new ClearAllSaveData(context);
    }




    public void csvFileExport() {
        localStorageDB.open();
        Cursor c = null;

        try {

            c = localStorageDB.selectALlRecords();


            int rowcount = 0;

            int colcount = 0;


            File sdCardDir = Environment.getExternalStorageDirectory();

            String filename = "pepsi_go_smart.csv";

            // the name of the file to export with

            File saveFile = new File(sdCardDir, filename);


            FileWriter fw = new FileWriter(saveFile);

            BufferedWriter bw = new BufferedWriter(fw);

            rowcount = c.getCount();

            colcount = c.getColumnCount();

            if (rowcount > 0) {

                c.moveToFirst();

                for (int i = 0; i < colcount; i++) {

                    if (i != colcount - 1) {

                        bw.write(c.getColumnName(i) + ",");

                    } else {

                        bw.write(c.getColumnName(i));

                    }

                }

                bw.newLine();

                for (int i = 0; i < rowcount; i++) {

                    c.moveToPosition(i);

                    for (int j = 0; j < colcount; j++) {

                        if (j != colcount - 1)

                            bw.write(c.getString(j) + ",");

                        else

                            bw.write(c.getString(j));

                    }

                    bw.newLine();

                }

                bw.flush();




             //   Toast.makeText(context,"Database Exported Successfully in CSV file format ", Toast.LENGTH_LONG).show();

                clearAllSaveData.openDialog("Database Exported Successfully in CSV file format ");

            }

        } catch (Exception ex) {

            localStorageDB.close();
           // Toast.makeText(context,"Failed to export in csv file format \n"+ex, Toast.LENGTH_LONG).show();
            clearAllSaveData.openDialog("Failed to export in csv file format \n");
        } finally {

            localStorageDB.close();
        }
    }
}
