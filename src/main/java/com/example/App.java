package com.example;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.example.sql.SqlQuery;
import com.example.sql.db.DbStorage;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

public class App 
{
    private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    private static String readCommand() throws IOException {
        System.out.print("> ");
        return stdin.readLine();
    }

    public static void run(DbStorage db){
        while (true) {
            try {
                SqlQuery query = new SqlQuery(readCommand(), db);
                Double runTime = query.run() / 1000.0;
                System.out.println("Command succesful with runtime: "+runTime+"s");
                System.out.println(query.getResult());
                // new SqlQuery(readCommand(), db);
            } catch (IOException e) {
                System.err.println("Error occured while reading input");
            } catch (InvalidSyntaxError e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            } catch (TableNotFoundError e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            } catch (InvalidValueError e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            } catch (ColumnNotFoundError e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args )
    {
        if (args.length < 1){
            System.err.println("java <executable> <db_filename.json>");
            return;
        }

        try {
            run(new DbStorage(args[0]));
        } catch (IOException e){
            System.out.println("Problem reading db file");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}
