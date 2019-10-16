package com.ade.retrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView TextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextResult = findViewById(R.id.TextResult);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi JsonPlaceHolderApi = retrofit.create(com.ade.retrofit2.JsonPlaceHolderApi.class);

        Call<List<Post>> call = JsonPlaceHolderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()){
                    TextResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post: posts){
                    String content ="";
                    content += "ID: " + post.getId()+ "\n";
                    content += "User ID: "+ post.getUserId() +"\n";
                    content += "Title: "+ post.getTitle()+ "\n";
                    content += "Text:" + post.getText()+ "\n\n";

                    TextResult.append(content);

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                TextResult.setText(t.getMessage());

            }
        });
    }
}
