package planet.it.limited.pepsigosmart.activities.bondhu_club;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.activities.ShowAllIputDataAct;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.task.FailledDataSendToServer;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;
import planet.it.limited.pepsigosmart.utils.TableHelper;

public class ShowAllInputedDataBondhuAct extends AppCompatActivity implements View.OnClickListener{

    private TableLayout tableLayout,cloumnHeader;
    LocalStoragePepsiDB localStorageDB;
    TableHelper tableHelper;
    String []headers ;
    String [][]allRows;
    TextView txvNoOfRows,todaysAllRows ;
    public static int todaysRowCount ;
    Toolbar toolbar;

    //FailledDataSendToServer failledDataSendToServer;

    ClearAllSaveData clearAllSaveData;

    @BindView(R.id.btn_back)
    Button btnPageBack;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_mobile)
    TextView txvMobile;


    String outletCode = " ";
    String coolCategory =" ";
    public String indusCSD="";
    public String indusWater="";
    public String indusLRB="";
    public String pepsiCSD="";
    public String pepsiWater="";
    public String pepsiLRB="";


    public String remarks ="";
    public String entryDate ="";
    public String outPicOneLPath="";
    public String outPicOneSPath="";

    public String startTime="";

    public String longitude="";
    public String userName="";
    public String status="";

    String userMobile = " ";
    ArrayList dataList ;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_inputed_data_bondhu);
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initViews(){
        ButterKnife.bind(this);
        clearAllSaveData = new ClearAllSaveData(ShowAllInputedDataBondhuAct.this);

        tableLayout = (TableLayout) findViewById(R.id.gridview_b);
        txvNoOfRows = (TextView)findViewById(R.id.txv_no_total) ;
        todaysAllRows = (TextView)findViewById(R.id.txv_today_no_rows) ;

        String entryDate = clearAllSaveData.getCurrentEntryDate();

        //cloumnHeader =(TableLayout) findViewById(R.id.headergridview);

        localStorageDB = new LocalStoragePepsiDB(ShowAllInputedDataBondhuAct.this,tableLayout,cloumnHeader);
        localStorageDB.open();
        tableHelper = new TableHelper(ShowAllInputedDataBondhuAct.this);
        allRows = tableHelper.getAllInputBData();
        todaysRowCount = localStorageDB.getTodaysNoOfRows(entryDate,userMobile);

        if(todaysRowCount>0){
            todaysAllRows.setText(String.valueOf(todaysRowCount));
        }

        addHeaders();

        addData();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addHeaders() {

        TableLayout tl = findViewById(R.id.gridview_b);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());

        //  tr.addView(getTextView(0, "Auditor id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Outlet Code", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Category", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Indus CSD", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Indus Water", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Indus LRB", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        //tr.addView(getTextView(0, "Enrolled Order", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Pepsico CSD", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Pepsico Water", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Pepsico LRB", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));

        tr.addView(getTextView(0, "Remarks", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Entry Date", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Status", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllInputedDataBondhuAct.this,R.drawable.table_header_border)));


        tl.addView(tr, getTblLayoutParams());
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addData() {
        dataList = new ArrayList();
      //  headers = tableHelper.tableHeaders;
        int allInputData = 0 ;
        if(allRows!=null){
            allInputData = allRows.length;
            txvNoOfRows.setText(String.valueOf(allInputData));
        }



        //   int pos = 0;

        //   int numCompanies = companies.length;
        TableLayout tl = findViewById(R.id.gridview_b);
        for (int i = 0; i < allInputData; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
//            tr.addView(getRowsTextView(i + 1, allRows[i][13], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  0, allRows[i][0], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  1, allRows[i][1], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  2, allRows[i][2], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));

            tr.addView(getRowsTextView(  3, allRows[i][3], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  4, allRows[i][4], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            // tr.addView(getRowsTextView( 5, allRows[i][5], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView( 5, allRows[i][5], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  6, allRows[i][6], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  7, allRows[i][7], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  8, allRows[i][8], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  9, allRows[i][9], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));



            String syncStatus = allRows[i][16];
            if(syncStatus.equals("failled")){
                String uderLineText = allRows[i][16] ;

                tr.addView(getRowsTextView(  16,uderLineText , Color.RED, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            }else {
                tr.addView(getRowsTextView(  16, allRows[i][16], Color.GREEN, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            }




//
            tl.addView(tr, getTblLayoutParams());





        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private TextView getRowsTextView(int id, String title, int color, int typeface, Drawable bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        // tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackground(bgColor);
        //tv.setLayoutParams(getLayoutParams());
        tv.setHeight(100);
        tv.setGravity(Gravity.CENTER );
        tv.setOnClickListener(this);
        return tv;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private TextView getTextView(int id, String title, int color, int typeface, Drawable bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        // tv.setBackgroundColor(bgColor);
        tv.setBackground(bgColor);
        //tv.setLayoutParams(getLayoutParams());
        tv.setHeight(80);
        // tv.setWidth(330);
        tv.setGravity(Gravity.CENTER );



        return tv;
    }
    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public void onClick(View v) {

    }
}
