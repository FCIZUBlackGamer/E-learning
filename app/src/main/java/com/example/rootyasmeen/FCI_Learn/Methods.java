package com.example.rootyasmeen.FCI_Learn;

/**
 * Created by fci on 24/03/18.
 */

import android.content.Context;
import android.database.Cursor;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.TableWrapper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hackro on 24/11/15.
 */
public class Methods {

    ResultDatabase resultDatabase;
    Cursor cursor1, cursor2;
    Context context;
    int counter = 0;
    Methods(Context context){
        this.context = context;
    }
    public Boolean write(String fname, String fcontent) {
        resultDatabase = new ResultDatabase( context );
        cursor1 = resultDatabase.ShowQuestionANDAnswer();
        cursor2 = resultDatabase.ShowData();
        try {
            String fpath = "/sdcard/" + fname + ".pdf";
            File file = new File(fpath);

            if (!file.exists()) {
                file.createNewFile();
            }

//            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
//            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            document.add(new Paragraph("Exam Result Powered by @FCI-Learn" ));
            while (cursor2.moveToNext()) {
                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
            }
            document.addCreationDate();
            document.setMargins( 10,10,10,10 );
            //document.add(new Paragraph("Powered by @FCI-Learn" ));
            document.add( new Paragraph( "------------------------------------------------------" ) );
            document.add( new Paragraph( "|  ID\t| Question\t| Answer\t| Degree  |" ) );
            while (cursor1.moveToNext()) {
                document.add( new Paragraph( "  "+cursor1.getString( 0 )+"\t| "+cursor1.getString( 1 )+"\t| "+cursor1.getString( 2 )+"\t| "+cursor1.getString( 3 )+"  |" ) );
                counter+= Integer.parseInt( cursor1.getString( 3 ) );
            }
            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
            document.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
