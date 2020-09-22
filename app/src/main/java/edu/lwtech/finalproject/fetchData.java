package edu.lwtech.finalproject;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class fetchData extends AsyncTask<Void,Void,Void> {

    String data ="";
    String text ="adfdfdffdfdfdfdfdf";


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            //URL url = new URL("https://open.api.ebay.com/shopping?callname=FindProducts&responseencoding=JSON&appid=sajanpok-Personal-PRD-569e8f59f-c8ed2a58&siteid=0&version=967&QueryKeywords=ipadair&AvailableItemsOnly=true&MaxEntries=6");
            URL url = new URL("https://api.myjson.com/bins/j5f6b");

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
           // httpURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
            String line = "";
            while(line != null)
            {
                line = bufferedReader.readLine();
                data = data+line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        SecondActivity.data.setText(this.data);
    }

}
