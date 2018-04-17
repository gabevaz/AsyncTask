package br.com.fiap.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText edtUrl;
    ImageView imgBaixada;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUrl = findViewById(R.id.edtUrl);
        imgBaixada = findViewById(R.id.imgBaixada);
    }

    public void downloadImagem(View view) {
        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
        downloadAsyncTask.execute(edtUrl.getText().toString());
    }

    private class DownloadAsyncTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                InputStream inputStream;
                Bitmap imagem;

                URL endereco = new URL(strings[0]);
                inputStream = endereco.openStream();
                imagem = BitmapFactory.decodeStream(inputStream);

                inputStream.close();

                return imagem;

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(MainActivity.this,getString(R.string.download), getString(R.string.aguarde));

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null){
                imgBaixada.setImageBitmap(bitmap);
            }else {
                Toast.makeText(MainActivity.this, R.string.imagem_nao_encontrada, Toast.LENGTH_SHORT).show();
            }

            mProgressDialog.dismiss();
        }
    }
}
