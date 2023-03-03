
package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testing.R;
import com.example.testing.adapters.ProjectRVAdapter;
import com.example.testing.adapters.ReportRVAdapter;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.Project;
import com.example.testing.classes.Report;

import java.util.ArrayList;

public class ReportListActivity extends AppCompatActivity {
    private RecyclerView reportRV;
    private ReportRVAdapter adapter;
    private ArrayList<Report> reports;
    private Button reportType;
    private boolean unmanaged;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        reports = new ArrayList<>();
        db=CCDatabase.getInstance(this);
        reportType=findViewById(R.id.btnReportType);
        unmanaged=true;
        reports = (ArrayList<Report>) db.reportDao().getUnmanagedReports();

        initReportsRV();
        reportType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(unmanaged) {
                    unmanaged = false;
                    reportType.setText("Unmanaged Reports");
                    reports = (ArrayList<Report>) db.reportDao().getManagedReports();
                    adapter.setReports(reports);
                    initReportsRV();


                }
                else{
                    unmanaged = true;
                    reportType.setText("Managed Reports");
                    reports = (ArrayList<Report>) db.reportDao().getUnmanagedReports();
                    adapter.setReports(reports);
                  initReportsRV();

                }
            }
        });


    }

    private void initReportsRV() {

        reportRV = findViewById(R.id.reportRVList);
        adapter = new ReportRVAdapter(this);
        adapter.setReports(reports);
        reportRV.setAdapter(adapter);
        reportRV.setLayoutManager(new LinearLayoutManager(this));
    }


}