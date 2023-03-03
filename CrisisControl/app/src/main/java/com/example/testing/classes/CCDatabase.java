package com.example.testing.classes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.testing.interfaces.database_interfaces.Account_Dao;
import com.example.testing.interfaces.database_interfaces.Admin_Dao;
import com.example.testing.interfaces.database_interfaces.Camp_Dao;
import com.example.testing.interfaces.database_interfaces.DonationAllocation_Dao;
import com.example.testing.interfaces.database_interfaces.Donation_Dao;
import com.example.testing.interfaces.database_interfaces.MedicalRequest_Dao;
import com.example.testing.interfaces.database_interfaces.NGO_Dao;
import com.example.testing.interfaces.database_interfaces.Project_Dao;
import com.example.testing.interfaces.database_interfaces.Ratings_Dao;
import com.example.testing.interfaces.database_interfaces.Report_Dao;
import com.example.testing.interfaces.database_interfaces.User_Dao;
import com.example.testing.interfaces.database_interfaces.Volunteers_Dao;

import java.util.Calendar;


@androidx.room.Database(entities = {Account.class,User.class,NGO.class,Project.class,Donation.class,DonationAllocation.class,Camp.class,Admin.class, AppliedVolunteer.class,Report.class,Ratings.class, MedicalRequest.class},version = 1)
    public abstract class CCDatabase extends RoomDatabase {
    public abstract Account_Dao accountDao();
    public abstract User_Dao userDao();
    public abstract NGO_Dao NGODao();
    public abstract Project_Dao projectDao();
    public abstract Donation_Dao donationDao();
    public abstract DonationAllocation_Dao donationAllocationDao();
    public abstract Camp_Dao campDao();
    public abstract Admin_Dao adminDao();
    public abstract Volunteers_Dao volunteersDao();
    public abstract Report_Dao reportDao();
    public abstract Ratings_Dao ratingsDao();
    public abstract MedicalRequest_Dao medicalRequestDao();

    private static CCDatabase instance;

    public static synchronized CCDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context,CCDatabase.class,"Crisis_Control_Db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallBack)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback initialCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateInitialData(instance).execute();
        }
    };

    private static class populateInitialData extends AsyncTask<Void,Void,Void>{
        private Account_Dao accountDao;
        private User_Dao userDao;
        private NGO_Dao NGODao;
        private Project_Dao projectDao;
        private Donation_Dao donationDao;
        private Camp_Dao campDao;
        private Admin_Dao adminDao;
        private Volunteers_Dao volunteersDao;
        private Report_Dao reportDao;
        private Ratings_Dao ratingsDao;
        private MedicalRequest_Dao medicalRequestDao;

        public populateInitialData(CCDatabase db){
            this.accountDao=db.accountDao();
            this.userDao=db.userDao();
            this.NGODao=db.NGODao();
            this.projectDao=db.projectDao();
            this.donationDao=db.donationDao();
            this.campDao=db.campDao();
            this.adminDao= db.adminDao();
            this.volunteersDao = db.volunteersDao();
            this.reportDao = db.reportDao();
            this.ratingsDao = db.ratingsDao();
            this.medicalRequestDao=db.medicalRequestDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2001);
            cal.set(Calendar.MONTH, Calendar.AUGUST);
            cal.set(Calendar.DAY_OF_MONTH, 21);
            User u1=new User(accountDao.getNewAccountID(),"hakim.shahzad@gmail.com","hakimhakim","Hakim Tiwana",cal.getTime(),"23 Ext-Judicial Colony Phase 1",User.USER_TYPE_DONOR);
            userDao.insertUserAccount(u1,u1);
            cal.set(Calendar.YEAR, 2006);
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 16);
            User u2 = new User(accountDao.getNewAccountID(),"usjid.nisar@gmail.com","usjidusjid","Usjid Nisar",cal.getTime(),"12-D Model Town",User.USER_TYPE_AFFECTEE);
            userDao.insertUserAccount(u2,u2);
            cal.set(Calendar.YEAR, 2003);
            cal.set(Calendar.MONTH, Calendar.AUGUST);
            cal.set(Calendar.DAY_OF_MONTH, 18);
            User u3 = new User(accountDao.getNewAccountID(),"ammar.munir@gmail.com","ammarammar","Ammar Munir",cal.getTime(),"236-B Faisal Town Lahore",User.USER_TYPE_VOLUNTEER);
            userDao.insertUserAccount(u3,u3);
            NGO n1 = new NGO(accountDao.getNewAccountID(),"SafePak","SafePak123","SafePak","Aim to make Pakistan Safe", "55-A Model Town",	true,	true,	3.4,2,"03234489666","JazzCash");
            NGODao.insertNGOAccount(n1,n1);
            NGO n2 = new NGO(accountDao.getNewAccountID(),"GreenPak","GreenPak123","GreenPak"	,"Aim to make Pakistan Greener",	"25-B Faisal Town",	true,	true,	4.7	,5,"03234489656","JazzCash");
            NGODao.insertNGOAccount(n2,n2);
            NGO n3 = new NGO(accountDao.getNewAccountID(),"EduPak","EduPak123","EduPak",	"Aim to Educate Pakistani Youth",	"73-F Johar Town",	true,	true,	4.1,	51,"03234489366","JazzCash");
            NGODao.insertNGOAccount(n3,n3);
            cal.set(Calendar.YEAR, 2021);
            cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 11);
            Project p1 = new Project("Educate the children",	"Asking for donations to buy poor children stationary and uniform for school and asking volunteers to teach those students", 	5,	40000,	100,	cal.getTime());
            projectDao.insertProject(p1);
            cal.set(Calendar.YEAR,2022 );
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 18);
            Project p2 = new Project("Clean Lahore",	"Asking Volunteers to join our team to clean the city of lahore",	4,	0,	200,	cal.getTime());
            projectDao.insertProject(p2);
            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.NOVEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 23);
            Project p3= new Project("Books For Children",	"Using Donations to buy book for the poor", 	5,	50000,	0	,cal.getTime());
            projectDao.insertProject(p3);
            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.NOVEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 16);
            Donation d = new Donation( 	1,	3,	cal.getTime(),5000,0);
            donationDao.insertDonation(d);
            Donation d1= new Donation(1,	1,	cal.getTime(),	1000,0);
            donationDao.insertDonation(d1);
            Donation d2 = new Donation(0,	1,	cal.getTime(),	10000,1);
            donationDao.insertDonation(d2);

            Coordinates co1 = new Coordinates(31.4845, 74.3263,"Model Town Park");
            Coordinates co2 = new Coordinates(31.4776, 74.3123,"Motia Park Model Town");

            Camp c1 = new Camp(3,co1);
            campDao.insertCamp(c1);

            Camp c2 = new Camp(3,co2);
            campDao.insertCamp(c2);

            Coordinates co3 = new Coordinates(31.4739, 74.3066,"Faisal Town Park");
            Camp c3 = new Camp(4,co3);
            campDao.insertCamp(c3);

            Coordinates co4 = new Coordinates(31.4616, 74.2872,"Johar Town Park");
            Camp c4 = new Camp(5,co4);
            campDao.insertCamp(c4);

            Coordinates co5 = new Coordinates(31.4688132, 74.249968,"J-3 Park Johar Town");
            Camp c5 = new Camp(5,co5);
            campDao.insertCamp(c5);

            Coordinates co6 = new Coordinates(31.4720, 74.2622,"H-3 Park Johar Town");
            Camp c6 = new Camp(5,co6);
            campDao.insertCamp(c6);

            Admin ad1 = new Admin(accountDao.getNewAccountID(),"0123456789112","Admin1","0123456789112","Hakim Shahzad Tiwana");
            adminDao.insertAdmin(ad1,ad1);

            Report r = new Report(n1.accID, u2.accID,"They do not respond to email and their allocations are not updated.",false);
            Report r1 = new Report(n3.accID, u3.accID,"Their allocations are not updated.",true);
            reportDao.insertReport(r);
            reportDao.insertReport(r1);

            return null;
        }
    }


}
