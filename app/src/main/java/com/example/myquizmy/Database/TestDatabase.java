package com.example.myquizmy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myquizmy.Profile.UserProfile;
import com.example.myquizmy.Transaction;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Sign.db2";
    private static final String TABLE_NAME = "UnifiedData";
    private static final String TABLE_NAME2 = "table2";
    private static final String TABLE_NAME3 = "GameDetailss";

    private static final String COL_PHONE = "phone";
    private static final String COL_NAME = "name";
    private static final String COL_PASSWORD = "password";
    private static final String COL_EMAIL = "email";
    private static final String COL_AGE = "age";
    private static final String COL_ADDRESS = "address";
    private static final String COL_IMAGE = "image";
    private static final String COL_FACEBOOK = "facebook";
    private static final String COL_WHATSAPP = "whatsapp";
    private static final String COL_REFERRAL_CODE = "referralCode";
    private static final String COL_UPI_ID = "upiId";
    private static final String COL_UPI_NO = "upiNo";
    private static final String COL_BANK_NAME = "bankName";
    private static final String COL_BANK_ACC = "bankAcc";
    private static final String COL_BANK_IFC = "bankIfc";
    private static final String COL_BANK_BALANCE = "bankBalance";

    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IDS = "IDS";
    public static final String COLUMN_PAY_TYPE = "PAY_TYPE";

    // New columns for GameDetails
    private static final String COL_GAME_NAME = "gameName";
    private static final String COL_MONEY_SPENT = "moneySpent";
    private static final String COL_MONEY_WON = "moneywon";

    public TestDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4); // Incremented schema version
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_PHONE + " TEXT PRIMARY KEY, " +
                COL_NAME + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_AGE + " TEXT, " +
                COL_ADDRESS + " TEXT, " +
                COL_IMAGE + " BLOB, " +
                COL_FACEBOOK + " TEXT, " +
                COL_WHATSAPP + " TEXT, " +
                COL_REFERRAL_CODE + " TEXT, " +
                COL_UPI_ID + " TEXT, " +
                COL_UPI_NO + " TEXT, " +
                COL_BANK_NAME + " TEXT, " +
                COL_BANK_ACC + " TEXT, " +
                COL_BANK_IFC + " TEXT, " +
                COL_BANK_BALANCE + " REAL, " +
                COLUMN_AMOUNT + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_PAY_TYPE + " TEXT, " +
                COLUMN_IDS + " TEXT)";

        String createTableSQL2 = "CREATE TABLE " + TABLE_NAME2 + " (" +
                COL_PHONE + " TEXT, "+
                COLUMN_AMOUNT + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_PAY_TYPE + " TEXT, " +
                COLUMN_IDS + " TEXT)";

        // New table for game details without joiningFee
        String createTableSQL3 = "CREATE TABLE " + TABLE_NAME3 + " (" +
                COL_PHONE + " TEXT, " +
                COL_GAME_NAME + " TEXT, " +
                COL_MONEY_SPENT + " TEXT, " +
                COL_MONEY_WON  +"TEXT,"+
                COLUMN_DATE + " TEXT, " +
                COLUMN_IDS + " TEXT)";

        db.execSQL(createTableSQL);
        db.execSQL(createTableSQL2);
        db.execSQL(createTableSQL3);
    }


    public boolean insertGameDetails(String phone, String gameName, double moneySpent,double moneyWon, String date, String ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PHONE, phone);
        contentValues.put(COL_GAME_NAME, gameName);
        contentValues.put(COL_MONEY_SPENT, moneySpent);
       // contentValues.put(COL_MONEY_WON, moneyWon);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_IDS, ids);
        Log.d("nbajjjj", "insertGameDetails: "+moneySpent);

        long result = db.insert(TABLE_NAME3, null, contentValues);
        return result != -1;
    }

    public Cursor getGameDetails(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE " + COL_PHONE + "=?", new String[]{phone});
    }
    public int getTotalMoneyWon(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_MONEY_WON + ") FROM " + TABLE_NAME3 + " WHERE " + COL_PHONE + "=?", new String[]{phone});
        int totalMoneyWon = 0;
        if (cursor.moveToFirst()) {
            totalMoneyWon = cursor.getInt(0);
        }
        cursor.close();
        return totalMoneyWon;
    }

    public int getTotalMoneySpent(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_MONEY_SPENT + ") FROM " + TABLE_NAME3 + " WHERE " + COL_PHONE + "=?", new String[]{phone});
        int totalMoneySpent = 0;
        if (cursor.moveToFirst()) {
            totalMoneySpent = cursor.getInt(0);
        }
        cursor.close();
        return totalMoneySpent;
    }

    public int getTotalGamesPlayed(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME3 + " WHERE " + COL_PHONE + "=?", new String[]{phone});
        int totalGamesPlayed = 0;
        if (cursor.moveToFirst()) {
            totalGamesPlayed = cursor.getInt(0);
        }
        cursor.close();
        return totalGamesPlayed;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);

        onCreate(db);
    }

    public Double getuSERTotalMoneySpent(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery("SELECT SUM(" + COL_MONEY_SPENT + ") FROM " + TABLE_NAME3 + " WHERE " + COL_PHONE + "=?", new String[]{phone});
       Cursor cursor = db.query(TABLE_NAME3,
                new String[]{COL_MONEY_SPENT},
                COL_PHONE + " = ?", new String[]{phone}, null, null, null);
        Double totalMoneySpent = 0.00;
        if (cursor != null && cursor.moveToFirst()) {
       // if (cursor.moveToFirst()) {
            totalMoneySpent=Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(COL_MONEY_SPENT)));
            Log.d("khguytgyuk", "getuSERTotalMoneySpent: "+String.valueOf(totalMoneySpent));

        //}
        cursor.close();

    }
        else {
            Log.d("khguytgyukelse", "getuSERTotalMoneySpent: "+String.valueOf(totalMoneySpent));

        }return totalMoneySpent;}

    public UserProfile getUserDetails(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        UserProfile userProfile = null;

        try {
            cursor = db.query(TABLE_NAME,
                    new String[]{COL_PHONE, COL_NAME, COL_EMAIL, COL_AGE, COL_ADDRESS, COL_IMAGE,COL_BANK_ACC,
                            COL_BANK_NAME,COL_BANK_IFC,COL_FACEBOOK,COL_WHATSAPP,COL_UPI_ID,COL_UPI_NO,COL_REFERRAL_CODE,COL_BANK_BALANCE},
                    COL_PHONE + " = ?", new String[]{phone}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                Log.d("myyesdata", cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                userProfile = new UserProfile(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_AGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(COL_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BANK_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BANK_ACC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BANK_IFC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FACEBOOK)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_WHATSAPP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_UPI_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_UPI_NO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_REFERRAL_CODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BANK_BALANCE)));
            } else {
                Log.d("getUserDetails", "No user found with phone: " + phone);
            }
        } catch (Exception e) {
            Log.e("getUserDetails", "Error while getting user details", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userProfile;
    }

    public boolean updateImage(String phone,Bitmap bitmapImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        values.put(COL_IMAGE, imageBytes);
        int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        //   db.close();
        //return rowsAffected > 0;
        long result1= db.insert(TABLE_NAME, null, values);

        db.close();


        return result1 != -1;
    }

    public boolean updateUserProfile(String phone, String name, String email, String age, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_AGE, age);
        values.put(COL_ADDRESS, address);

        int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        //   db.close();
        //return rowsAffected > 0;
        long result1= db.insert(TABLE_NAME, null, values);

        db.close();


        return result1 != -1;
    }
    public boolean updateBankPaymentData(String phone, String bankName, String bankAcc, String bankIfc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BANK_NAME, bankName);
        values.put(COL_BANK_ACC, bankAcc);
        values.put(COL_BANK_IFC, bankIfc);
        int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        db.close();
        return rowsAffected > 0;
    }

    public boolean updateReferralData(String phone, String referralCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_REFERRAL_CODE, referralCode);
        int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        db.close();
        return rowsAffected > 0;
    }


    public boolean updateUpiData(String phone,String upiId, String upiNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_UPI_ID, upiId);
        values.put(COL_UPI_NO, upiNo);
        int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        db.close();
        return rowsAffected > 0;
    }




    public boolean updateSocailData(String fac, String Whats,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FACEBOOK, fac);
        values.put(COL_WHATSAPP, Whats);
        int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        // db.close();
        return rowsAffected > 0;
    }



    public boolean insertLoginData(String name, String phone, String password, String age, String email, String address,
                                   String bank, String acc, String ifsc, String fac,String whastpp,String upino,String upiid,
                                   String referral,String type, String amount, String date,String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PHONE, phone);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_AGE, age);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_ADDRESS, address);
        contentValues.put(COL_BANK_NAME, bank);
        contentValues.put(COL_BANK_IFC, ifsc);
        contentValues.put(COL_BANK_ACC, acc);
        contentValues.put(COL_FACEBOOK, fac);
        contentValues.put(COL_WHATSAPP, whastpp);
        contentValues.put(COL_UPI_ID, upiid);
        contentValues.put(COL_UPI_NO, upino);
        contentValues.put(COL_REFERRAL_CODE, referral);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
      //  bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes =null;
        contentValues.put(COL_IMAGE, imageBytes);
        contentValues.put(COL_BANK_BALANCE, 0.0);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }


    public boolean checkPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PHONE + "=?", new String[]{phone});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public boolean checkUserCredentials(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PHONE + "=? AND " + COL_PASSWORD + "=?", new String[]{phone, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
    public boolean updateBankBalance(String phone, double newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BANK_BALANCE, newBalance);
        int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        db.close();
        return rowsAffected > 0;
    }

    public boolean withdrawFromBankBalance(String phone, double withdrawalAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_BANK_BALANCE + " FROM " + TABLE_NAME + " WHERE " + COL_PHONE + " = ?";
        Cursor cursor = null;
        boolean result = false;

        try {
            cursor = db.rawQuery(query, new String[]{phone});
            if (cursor != null && cursor.moveToFirst()) {
                double currentBalance = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_BANK_BALANCE));
                if (currentBalance >= withdrawalAmount && withdrawalAmount >= 50) {
                    double newBalance = currentBalance - withdrawalAmount;
                    ContentValues values = new ContentValues();
                    values.put(COL_BANK_BALANCE, newBalance);

                    int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
                    result = rowsAffected > 0;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return result;
    }



    public boolean addMoneyToBankBalance(String phone, double depositAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_BANK_BALANCE + " FROM " + TABLE_NAME + " WHERE " + COL_PHONE + " = ?";
        Cursor cursor = null;
        boolean result = false;

        try {
            cursor = db.rawQuery(query, new String[]{phone});
            if (cursor != null && cursor.moveToFirst()) {
                double currentBalance = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_BANK_BALANCE));
                double newBalance = currentBalance + depositAmount;
                ContentValues values = new ContentValues();
                values.put(COL_BANK_BALANCE, newBalance);

                int rowsAffected = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
                result = rowsAffected > 0;
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return result;
    }

    public List<Transaction> getTransactions(String phone, String section) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE phone = ?";

//            if (!section.equals("All")) {
//                query += " AND type = ?";
//            }

            cursor = db.rawQuery(query, section.equals("All") ? new String[]{phone} : new String[]{phone, section});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                    String amount = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                    String PAYTYPE = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAY_TYPE));
                    String IDS = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IDS));
                    transactions.add(new Transaction(type, amount, date,PAYTYPE,IDS));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return transactions;
    }


    public boolean insertTransaction(String phone,String type, String payment_type, String date, String amount,String ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_PHONE,phone);
        contentValues.put(COLUMN_TYPE,type);
        contentValues.put(COLUMN_PAY_TYPE,payment_type);
        contentValues.put(COLUMN_DATE,date);
        contentValues.put(COLUMN_AMOUNT,amount);
        contentValues.put(COLUMN_IDS,ids);

        long result = db.insert(TABLE_NAME2, null, contentValues);
        db.close();
        return result != -1;
    }



}
